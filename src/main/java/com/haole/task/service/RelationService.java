package com.haole.task.service;


import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.RelationPojos;

/**
 * 日常
 */
public interface RelationService {

    boolean canManage(Long userId, Long useeId);

    /**
     * 新增。
     */
    BaseResponse create(Long userId, RelationPojos.CreateRequest request);

    /**
     * 更新。
     */
    BaseResponse update(Long userId, RelationPojos.CreateRequest request);

    /**
     * 新增。
     */
    BaseResponse list(Long userId, RelationPojos.ListRequest request);

    /**
     * 统计数据。
     */
    BaseResponse stat(Long userId);
}
