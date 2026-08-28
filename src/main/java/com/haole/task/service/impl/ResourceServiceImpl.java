package com.haole.task.service.impl;

import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.ResourceDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.DataResponse;
import com.haole.task.model.dto.ResourcePojos;
import com.haole.task.model.entity.ResourceDTO;
import com.haole.task.service.OssService;
import com.haole.task.service.ResourceService;
import com.haole.task.utils.IdGenerator;
import com.haole.task.utils.LogUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源服务
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger log = LogUtils.getLogger(ResourceServiceImpl.class.getSimpleName());

    private static final String SALT = "786db29dc15b03e3be716c047cc18da2";

    private final ResourceDao resourceDao;
    private final OssService ossService;

    public ResourceServiceImpl(ResourceDao resourceDao, OssService ossService) {
        this.resourceDao = resourceDao;
        this.ossService = ossService;
    }

    @Override
    public BaseResponse create(Long userId, ResourceDTO request) {
        // 先排重。
        List<ResourceDTO> exists = resourceDao.selectByHashes(Collections.singletonList(request.getHash()));
        if (!CollectionUtils.isEmpty(exists)) {
            for (ResourceDTO item : exists) {
                if (item.getChecked() != null && item.getChecked() != 0) {
                    // TODO 有可能存在多个，且数据变吗？？
                    adapt(item);
                    return new DataResponse<>(item);
                }
            }
        }

        // 补全记录。
        request.setCreator(userId);
        request.setId(IdGenerator.nextId());
        // 目前只有一个，所以不进库了。
        // request.setBucket(ossService.getBucket());
        request.setCreateTime(new Date());

        // 生成直传凭证，客户端凭此把文件直接 POST 到 OSS。
        String key = generateUploadKey(request);
        request.setPath(key);
        request.setUpload(ossService.create(key));

        int effected = resourceDao.insertSelective(request);
        if (effected <= 0) {
            return new BaseResponse(ErrorCode.ERR_SERVER_FAILED);
        }
        adapt(request);
        return new DataResponse<>(request);
    }

    @Override
    public BaseResponse update(Long userId, ResourcePojos.UpdateRequest request) {
        List<Long> ids = request.getData().stream().map(ResourceDTO::getId).collect(Collectors.toList());
        List<ResourceDTO> exists = resourceDao.selectByIds(ids);
        if (exists.size() != request.getData().size()) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        for (ResourceDTO item : request.getData()) {
            // 让总有点东西可以更新。
            item.setUpdateTime(new Date());
            resourceDao.updateByPrimaryKeySelective(item);
        }

        return list(userId, new ResourcePojos.ListRequest(ids));
    }

    @Override
    public BaseResponse list(Long userId, ResourcePojos.ListRequest request) {
        List<ResourceDTO> items = new ArrayList<>(request.getIds().size());
        if (!CollectionUtils.isEmpty(request.getIds())) {
            items = resourceDao.selectByIds(request.getIds());
        }

        if (!CollectionUtils.isEmpty(items)) {
            for (ResourceDTO item : items) {
                adapt(item);
            }
        }
        return new ResourcePojos.ListResponse(items);
    }

    /**
     * 生成上传到 OSS 的 object key，例如 image/1234567890.jpg。
     */
    protected String generateUploadKey(ResourceDTO request) {
        String postfix = request.getPostfix() == null ? "" : request.getPostfix();
        StringBuilder sb = new StringBuilder(128);
        sb.append(request.getType()).append(SALT).append(request.getHash()).append(System.currentTimeMillis());
        // 生成一个随机数。
        String hash = DigestUtils.md5DigestAsHex(sb.toString().getBytes(StandardCharsets.UTF_8));
        Calendar now = Calendar.getInstance();
        return String.format("u/%d/%02d/%02d/%s%s%s", now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1,
                now.get(Calendar.DAY_OF_MONTH), request.getHash().substring(0, 16), hash,
                !postfix.isEmpty() ? "." + postfix : "");
    }

    private void adapt(ResourceDTO item) {
        if (item.getUpload() == null && !TextUtils.isEmpty(item.getPath())) {
            item.setPath(ossService.getFullPath(item.getPath()));
        }
        item.adapt();
    }

}
