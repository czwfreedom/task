package com.haole.task.service;

import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.RoutinePojos;

/**
 * 日常
 */
public interface RoutineService {
    /**
     * 新增。
     */
    BaseResponse create(Long userId, RoutinePojos.CreateRequest request);

    /**
     * 更新。
     */
    BaseResponse update(Long userId, RoutinePojos.CreateRequest request);

    /**
     * 新增。
     */
    BaseResponse list(Long userId, RoutinePojos.ListRequest request);
}
