package com.haole.task.controller;

import com.haole.task.aop.RequestLog;
import com.haole.task.constants.Constants;
import com.haole.task.constants.ErrorCode;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.UserInfoPojos;
import com.haole.task.model.entity.UserInfoDTO;
import com.haole.task.service.UserInfoService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器
 */
@RestController
public class UserInfoController {


    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @RequestLog
    @PostMapping("/v1/user/info/update")
    public BaseResponse update(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody UserInfoDTO request) {
        // 目前只能修改自己的。
        if (request.getId() == null || !ObjectUtils.nullSafeEquals(userId, request.getId())) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        return userInfoService.update(userId, request);
    }

    @RequestLog
    @PostMapping("/v1/user/info/list")
    public BaseResponse list(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                             @RequestBody UserInfoPojos.ListRequest request) {
        if (request.getId() == null) {
            request.setId(userId);
        } else if (!ObjectUtils.nullSafeEquals(userId, request.getId())) {
            return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
        }

        return userInfoService.list(userId, request);
    }
}
