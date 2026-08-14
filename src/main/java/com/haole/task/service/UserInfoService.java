package com.haole.task.service;

import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.UserInfoPojos;
import com.haole.task.model.entity.UserInfoDTO;

/**
 * 用户信息
 */
public interface UserInfoService {
    /**
     * 更新用户信息
     */
    BaseResponse update(Long userId, UserInfoDTO request);

    /**
     * 获取用户信息
     */
    BaseResponse list(Long userId, UserInfoPojos.ListRequest request);
}
