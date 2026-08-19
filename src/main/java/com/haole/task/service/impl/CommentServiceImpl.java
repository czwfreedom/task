package com.haole.task.service.impl;

import com.haole.task.constants.CommentType;
import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.CommentDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.CommentPojos;
import com.haole.task.model.entity.CommentDTO;
import com.haole.task.model.entity.RoutineDTO;
import com.haole.task.model.entity.UserDTO;
import com.haole.task.service.CommentService;
import com.haole.task.service.RelationService;
import com.haole.task.service.RoutineService;
import com.haole.task.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论服务。
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final RoutineService routineService;
    private final RelationService relationService;
    private final UserService userService;


    public CommentServiceImpl(CommentDao commentDao, RoutineService routineService,
                              RelationService relationService, UserService userService) {
        this.commentDao = commentDao;
        this.routineService = routineService;
        this.relationService = relationService;
        this.userService = userService;
    }

    @Override
    public List<CommentPojos.Stat> getStat(Long userId, List<Long> refs) {
        return commentDao.selectStat(userId, refs);
    }

    @Override
    public BaseResponse create(Long userId, CommentDTO request) {
        if (CommentType.ROUTINE.equals(request.getType())) {
            return createRoutine(userId, request);
        }
        return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
    }

    @Override
    public BaseResponse list(Long userId, CommentPojos.ListRequest request) {
        // 这里有个权限没有校验。
        List<CommentDTO> comments = null;
        if (request.getRef() != null) {
            comments = commentDao.selectByRef(request.getRef(), request.getPraise(), request.getComment());
        }

        if (!CollectionUtils.isEmpty(comments)) {
            comments.forEach(CommentDTO::adapt);
            List<UserDTO> users = userService.get(
                    comments.stream().map(CommentDTO::getUserId).distinct().collect(Collectors.toList()), false);
            users.forEach(UserDTO::adaptMore);
            CommentPojos.ListResponse response = new CommentPojos.ListResponse(comments);
            response.users = users;
            return response;
        }
        return new BaseResponse();
    }

    @Override
    public BaseResponse update(Long userId, CommentDTO request) {
        CommentDTO comment = commentDao.selectByPrimaryKey(request.getId());
        if (comment == null) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        if (!comment.getUserId().equals(userId)) {
            return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
        }

        initComment(request);

        request.setUpdateTime(new Date());
        commentDao.updateByPrimaryKeySelective(request);

        comment = commentDao.selectByPrimaryKey(request.getId());
        comment.adapt();
        return new CommentPojos.ListResponse(Collections.singletonList(comment));
    }


    protected BaseResponse createRoutine(Long userId, CommentDTO request) {
        RoutineDTO routine = routineService.get(request.getRef());
        if (routine == null) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        // 从逻辑上说，要先看见，再评论。
        if (!relationService.canManage(userId, routine.getUserId())) {
            return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
        }

        initComment(request);

        int effected = commentDao.insertSelective(request);
        if (effected <= 0) {
            return new BaseResponse(ErrorCode.ERR_SERVER_FAILED);
        }

        CommentDTO comment = commentDao.selectByPrimaryKey(request.getId());
        comment.adapt();
        return new CommentPojos.ListResponse(Collections.singletonList(comment));
    }

    protected void initComment(CommentDTO request) {
        if (request.getPraise() != null && request.getPraise() != 0) {
            request.setPraiseTime(new Date());
        }

        if (request.getDetail() != null) {
            request.setComment((byte) (request.getDetail().isEmpty() ? 0 : 1));
            if (request.getComment() != 0) {
                request.setCommentTime(new Date());
            }
        }
    }
}
