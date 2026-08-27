package com.haole.task.service;

import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.ResourcePojos;
import com.haole.task.model.entity.ResourceDTO;

/**
 * 资源
 */
public interface ResourceService {

    /**
     * 创建资源。
     */
    BaseResponse create(Long userId, ResourceDTO request);


    /**
     * 更新资源。
     */
    BaseResponse update(Long userId, ResourcePojos.UpdateRequest request);


    /**
     * 拉取资源。
     */
    BaseResponse list(Long userId, ResourcePojos.ListRequest request);
}
