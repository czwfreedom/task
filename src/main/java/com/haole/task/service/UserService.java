package com.haole.task.service;

import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.entity.UserDTO;

import java.util.List;

/**
 * 用户
 */
public interface UserService {

    /**
     * 把Dao封装起来。
     */
    UserDTO get(Long id);

    /**
     * 把Dao封装起来。
     */
    List<UserDTO> get(List<Long> ids, Boolean withDetail);


    BaseResponse update(Long userId, UserDTO request);
}
