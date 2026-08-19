package com.haole.task.controller;

import com.haole.task.aop.RequestLog;
import com.haole.task.constants.CommentAttr;
import com.haole.task.constants.CommentType;
import com.haole.task.constants.Constants;
import com.haole.task.constants.ErrorCode;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.CommentPojos;
import com.haole.task.model.entity.CommentDTO;
import com.haole.task.service.CommentService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


/**
 * 评论。
 */
@RestController
public class CommentController {


    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestLog
    @PostMapping("/v1/comment/create")
    public BaseResponse create(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody CommentDTO request) {
        if (request.getRef() == null) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        if (request.getType() == null) {
            request.setType(CommentType.ROUTINE);
        }
        if (request.getAttrs() == null) {
            if (!ObjectUtils.isEmpty(request.getDetail())) {
                request.setAttrs(CommentAttr.COMMENT);
            } else {
                request.setAttrs(CommentAttr.LIKE);
            }
        }

        request.setUserId(userId);
        return commentService.create(userId, request);
    }

    @RequestLog
    @PostMapping("/v1/comment/update")
    public BaseResponse update(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                               @RequestBody CommentDTO request) {
        if (request.getId() == null) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        return commentService.update(userId, request);
    }

    @RequestLog
    @PostMapping("/v1/comment/list")
    public BaseResponse list(@RequestHeader(Constants.HEADER_USER_ID) Long userId,
                             @RequestBody CommentPojos.ListRequest request) {
        //  目前只支持一个个拉（怕数据太多）
        if (request.getRef() == null) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        return commentService.list(userId, request);
    }

}
