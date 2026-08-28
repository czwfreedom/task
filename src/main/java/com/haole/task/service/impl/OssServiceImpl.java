package com.haole.task.service.impl;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.haole.task.config.AliyunConfig;
import com.haole.task.model.entity.ResourceDTO;
import com.haole.task.service.OssService;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

/**
 * OSS服务
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 直传允许的最大文件大小（100MB）。
     */
    private static final long MAX_UPLOAD_SIZE = 20 * 1024 * 1024L;

    /**
     * 直传 policy 有效期（分钟）。
     */
    private static final long POLICY_EXPIRE_MINUTES = 60;

    /**
     * OSS POST policy 的 expiration 时间格式（ISO8601 UTC）。
     */
    private static final DateTimeFormatter POLICY_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);

    private final OSSClient ossClient;
    private final AliyunConfig.Oss ossConfig;

    public OssServiceImpl(AliyunConfig aliyunConfig) {
        this.ossConfig = aliyunConfig.getOss();
        this.ossClient = createClient();
    }

    /**
     * 创建阿里云 OSS 客户端（V2 SDK）。
     */
    private OSSClient createClient() {
        return OSSClient.newBuilder()
                .region(resolveRegion(ossConfig.getEndpoint()))
                .endpoint(ossConfig.getEndpoint())
                .credentialsProvider(new StaticCredentialsProvider(
                        ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret()))
                .build();
    }

    @Override
    public ResourceDTO.OSSUpload create(String objectKey) {
        ResourceDTO.OSSUpload upload = new ResourceDTO.OSSUpload();
        upload.setKey(objectKey);
        upload.setUrl(buildUploadUrl(ossConfig.getBucket(), ossConfig.getEndpoint()));
        upload.setKeyId(ossConfig.getAccessKeyId());
        String policy = generatePolicy(ossConfig.getBucket(), dirOf(objectKey));
        upload.setPolicy(policy);
        upload.setSignature(sign(policy, ossConfig.getAccessKeySecret()));
        return upload;
    }

    @Override
    public String getBucket() {
        return ossConfig.getBucket();
    }

    @Override
    public String getFullPath(String path) {
        return ossConfig.getCname() + "/" + path;
    }

    /**
     * 生成直传 policy（base64 编码），限制 bucket、key 目录和文件大小。
     */
    private String generatePolicy(String bucket, String dir) {
        String expiration = POLICY_TIME.format(Instant.now().plus(POLICY_EXPIRE_MINUTES, ChronoUnit.MINUTES));
        String json = "{\"expiration\":\"" + expiration + "\",\"conditions\":[" +
                "[\"content-length-range\",0," + MAX_UPLOAD_SIZE + "]," +
                "{\"bucket\":\"" + bucket + "\"}," +
                "[\"starts-with\",\"$key\",\"" + dir + "\"]" +
                "]}";
        return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 从 objectKey 提取目录前缀（含斜杠），例如 image/123.jpg -> image/。
     */
    private String dirOf(String objectKey) {
        int idx = objectKey == null ? -1 : objectKey.indexOf('/');
        return idx > 0 ? objectKey.substring(0, idx + 1) : "";
    }

    /**
     * 用 AccessKeySecret 对 policy 做 HmacSHA1 签名，返回 base64。
     */
    private String sign(String policy, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
            return Base64.getEncoder().encodeToString(mac.doFinal(policy.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("oss sign error", e);
        }
    }

    /**
     * 直传地址：https://{bucket}.{endpoint}。
     */
    private String buildUploadUrl(String bucket, String endpoint) {
        String host = endpoint;
        if (host.startsWith("http://")) {
            host = host.substring("http://".length());
        } else if (host.startsWith("https://")) {
            host = host.substring("https://".length());
        }
        return "https://" + bucket + "." + host;
    }

    /**
     * 从 endpoint（如 oss-cn-hangzhou.aliyuncs.com）解析 region（如 cn-hangzhou）。
     */
    private String resolveRegion(String endpoint) {
        String host = endpoint;
        if (host.startsWith("http://")) {
            host = host.substring("http://".length());
        } else if (host.startsWith("https://")) {
            host = host.substring("https://".length());
        }
        if (host.startsWith("oss-")) {
            host = host.substring("oss-".length());
        }
        if (host.endsWith(".aliyuncs.com")) {
            host = host.substring(0, host.length() - ".aliyuncs.com".length());
        }
        return host;
    }
}
