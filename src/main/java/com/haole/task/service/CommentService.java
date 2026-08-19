package com.haole.task.service;

import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.CommentPojos;
import com.haole.task.model.entity.CommentDTO;

/**
 * 评论。
 */
public interface CommentService {
    /**
     * 创建评论。
     */
    BaseResponse create(Long userId, CommentDTO request);

    /**
     * 拉取评论。
     */
    BaseResponse list(Long userId, CommentPojos.ListRequest request);

    /**
     * 更新评论。
     */
    BaseResponse update(Long userId, CommentDTO request);
}
