package com.haole.task.service;

import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.entity.UserDTO;

/**
 * 用户
 */
public interface UserService {


    BaseResponse update(Long userId, UserDTO request);
}
