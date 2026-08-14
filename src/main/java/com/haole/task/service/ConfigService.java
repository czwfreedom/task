package com.haole.task.service;

import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.ConfigPojos;
import com.haole.task.model.entity.ConfigDTO;

/**
 * 配置。
 */
public interface ConfigService {

    /**
     * 创建配置。
     */
    BaseResponse create(Long userId, ConfigDTO request);

    /**
     * 更新配置。
     */
    BaseResponse update(Long userId, ConfigDTO request);

    /**
     * 创建配置。
     */
    BaseResponse list(Long userId, ConfigPojos.ListRequest request);
}
