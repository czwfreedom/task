package com.haole.task.service;

import com.haole.task.model.entity.ResourceDTO;

/**
 * OSS服务
 */
public interface OssService {

    /**
     * 创建OSS上传
     */
    ResourceDTO.OSSUpload create(String objectKey);

    /**
     * 直传用的 bucket 名称
     */
    String getBucket();

    /**
     * 加上前缀
     */
    String getFullPath(String path);
}
