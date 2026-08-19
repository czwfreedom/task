package com.haole.task.service;

import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.RoutinePojos;
import com.haole.task.model.entity.RoutineDTO;
import com.haole.task.model.entity.StatEntity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 日常
 */
public interface RoutineService {

    RoutineDTO get(Long id);

    /**
     * 获取统计数据
     */
    List<StatEntity> getStat(Collection<Long> userIds, Date date);

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

    /**
     * 统计。
     */
    BaseResponse stat(Long userId);
}
