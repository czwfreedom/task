package com.haole.task.controller;

import com.haole.task.aop.RequestLog;
import com.haole.task.constants.Constants;
import com.haole.task.constants.ErrorCode;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.RoutinePojos;
import com.haole.task.model.entity.RoutineDTO;
import com.haole.task.service.RoutineService;
import com.haole.task.utils.DateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 日常。
 */
@RestController
public class RoutineController {

    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @RequestLog
    @PostMapping("/v1/routine/create")
    public BaseResponse create(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody RoutinePojos.CreateRequest request) {
        if (CollectionUtils.isEmpty(request.data)) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }
        for (RoutineDTO item : request.data) {
            if (ObjectUtils.isEmpty(item.getDetail()) || item.getCategory() == null ||
                    ObjectUtils.isEmpty(item.getTransaction())) {
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }
            item.setDate(new Date(DateUtils.getStartOfDay(item.getDate() != null ?
                    item.getDate().getTime() : System.currentTimeMillis())));
            item.setUserId(userId);
        }
        return routineService.create(userId, request);
    }

    @RequestLog
    @PostMapping("/v1/routine/update")
    public BaseResponse update(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody RoutinePojos.CreateRequest request) {
        if (CollectionUtils.isEmpty(request.data)) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }
        for (RoutineDTO item : request.data) {
            if (item.getId() == null) {
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }
        }
        return routineService.update(userId, request);
    }

    @RequestLog(logResponse = false)
    @PostMapping("/v1/routine/list")
    public BaseResponse list(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                             @RequestBody RoutinePojos.ListRequest request) {
        // 目前只能拉自己的。
        if (request.getUserId() == null) {
            request.setUserId(userId);
        }
        request.setDate(new Date(DateUtils.getStartOfDay(
                request.getDate() != null ? request.getDate().getTime() : System.currentTimeMillis())));
        return routineService.list(userId, request);
    }
}
