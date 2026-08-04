package com.haole.task.controller;

import com.haole.task.aop.RequestLog;
import com.haole.task.constants.Constants;
import com.haole.task.constants.ErrorCode;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.entity.UserDTO;
import com.haole.task.service.UserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 */
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestLog
    @PostMapping("/v1/user/update")
    public BaseResponse update(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody UserDTO request) {
        // 目前只能修改自己的。
        if (request.getId() == null || !ObjectUtils.nullSafeEquals(userId, request.getId())) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        if (ObjectUtils.isEmpty(request.getName()) && ObjectUtils.isEmpty(request.getNickname()) &&
                ObjectUtils.isEmpty(request.getAvatar()) && ObjectUtils.isEmpty(request.getPhone())) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        return userService.update(userId, request);
    }
}
