package com.haole.task.controller;

import com.haole.task.aop.RequestLog;
import com.haole.task.constants.Constants;
import com.haole.task.constants.ErrorCode;
import com.haole.task.constants.ResourceType;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.ResourcePojos;
import com.haole.task.model.entity.ResourceDTO;
import com.haole.task.service.ResourceService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源
 */
@RestController
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }


    @RequestLog
    @PostMapping("/v1/resource/create")
    public BaseResponse create(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody ResourceDTO request) {
        if (ObjectUtils.isEmpty(request.getHash()) || request.getType() == null || request.getPostfix() == null) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        if (ResourceType.IMAGE.equals(request.getType())) {
            if (request.getWidth() == null || request.getHeight() == null || request.getTime() == null) {
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }
        } else if (ResourceType.AUDIO.equals(request.getType())) {
            if (request.getDuration() == null || request.getTime() == null) {
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }
        }
        return resourceService.create(userId, request);
    }

    @RequestLog
    @PostMapping("/v1/resource/update")
    public BaseResponse update(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody ResourcePojos.UpdateRequest request) {
        if (CollectionUtils.isEmpty(request.getData())) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }
        for (ResourceDTO item : request.getData()) {
            // 不能改路径。
            if (item.getId() == null || item.getPath() != null) {
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }
        }
        return resourceService.update(userId, request);
    }

    @RequestLog(logResponse = false)
    @PostMapping("/v1/resource/update")
    public BaseResponse list(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                             @RequestBody ResourcePojos.ListRequest request) {
        if (CollectionUtils.isEmpty(request.ids) || request.getIds().size() > 1000) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }
        return resourceService.list(userId, request);
    }
}
