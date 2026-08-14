package com.haole.task.controller;

import com.haole.task.aop.RequestLog;
import com.haole.task.constants.Constants;
import com.haole.task.constants.ErrorCode;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.ConfigPojos;
import com.haole.task.model.entity.ConfigDTO;
import com.haole.task.service.ConfigService;
import com.haole.task.utils.IdGenerator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 配置
 */
@RestController
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @RequestLog
    @PostMapping("/v1/config/create")
    public BaseResponse create(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody ConfigDTO request) {
        if (ObjectUtils.isEmpty(request.getName()) || ObjectUtils.isEmpty(request.getValue()) || request.getType() == null) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        // 只能创建自己名下的。
        request.setUserId(userId);
        return configService.create(userId, request);
    }

    @RequestLog
    @PostMapping("/v1/config/update")
    public BaseResponse update(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody ConfigDTO request) {
        if (request.getId() == null) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }
        return configService.update(userId, request);
    }

    @RequestLog(logResponse = false)
    @PostMapping("/v1/config/list")
    public BaseResponse list(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                             @RequestBody ConfigPojos.ListRequest request) {
        if ((CollectionUtils.isEmpty(request.ids) && CollectionUtils.isEmpty(request.userIds) &&
                CollectionUtils.isEmpty(request.tags)) || CollectionUtils.isEmpty(request.types)) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        if (!CollectionUtils.isEmpty(request.userIds)) {
            // 除了系统默认的，只允许拉自己的。
            for (Long id : request.userIds) {
                if (id > IdGenerator.MIN && !Objects.equals(userId, id)) {
                    return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
                }
            }
        }
        return configService.list(userId, request);
    }
}
