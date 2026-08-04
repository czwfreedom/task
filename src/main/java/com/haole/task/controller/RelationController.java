package com.haole.task.controller;

import com.haole.task.aop.RequestLog;
import com.haole.task.constants.Constants;
import com.haole.task.constants.ErrorCode;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.RelationPojos;
import com.haole.task.model.entity.RelationDTO;
import com.haole.task.service.RelationService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关系
 */
@RestController
public class RelationController {

    private final RelationService relationService;

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }

    @RequestLog
    @PostMapping("/v1/relation/create")
    public BaseResponse create(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody RelationPojos.CreateRequest request) {
        if (CollectionUtils.isEmpty(request.data)) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }
        for (RelationDTO item : request.data) {
            if (ObjectUtils.isEmpty(item.getUserId()) || ObjectUtils.isEmpty(item.getUseeId())) {
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }

            // 只能创建关于自己的
            if (!item.getUserId().equals(userId)) {
                return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
            }
        }
        return relationService.create(userId, request);
    }

    @RequestLog
    @PostMapping("/v1/relation/update")
    public BaseResponse update(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody RelationPojos.CreateRequest request) {
        if (CollectionUtils.isEmpty(request.data)) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }
        for (RelationDTO item : request.data) {
            if (item.getId() == null ||
                    (item.getDeleted() == null && ObjectUtils.isEmpty(item.getRemark()) && ObjectUtils.isEmpty(item.getExtra()))) {
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }
        }
        return relationService.update(userId, request);
    }

    @RequestLog(logResponse = false)
    @PostMapping("/v1/relation/list")
    public BaseResponse list(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                             @RequestBody RelationPojos.ListRequest request) {
        if (!ObjectUtils.isEmpty(request.getUserId()) && !ObjectUtils.isEmpty(request.getUseeId())) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        if (!ObjectUtils.isEmpty(request.getUserId()) && !request.getUserId().equals(userId)) {
            return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
        }

        if (!ObjectUtils.isEmpty(request.getUseeId()) && !request.getUseeId().equals(userId)) {
            return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
        }

        if (ObjectUtils.isEmpty(request.getUserId()) && ObjectUtils.isEmpty(request.getUseeId())) {
            request.setUserId(userId);
        }

        return relationService.list(userId, request);
    }
}
