package com.haole.task.service.impl;

import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.RelationDao;
import com.haole.task.dao.RoutineDao;
import com.haole.task.dao.UserDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.RelationPojos;
import com.haole.task.model.entity.Relation;
import com.haole.task.model.entity.RelationDTO;
import com.haole.task.model.entity.StatEntity;
import com.haole.task.model.entity.UserDTO;
import com.haole.task.service.RelationService;
import com.haole.task.utils.DateUtils;
import com.haole.task.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 关系服务
 */
@Service
public class RelationServiceImpl implements RelationService {

    private static final Logger log = LogUtils.getLogger(RelationService.class.getSimpleName());

    private final RelationDao relationDao;
    private final UserDao userDao;
    private final RoutineDao routineDao;

    public RelationServiceImpl(RelationDao relationDao, UserDao userDao, RoutineDao routineDao) {
        this.relationDao = relationDao;
        this.userDao = userDao;
        this.routineDao = routineDao;
    }


    @Override
    public boolean canManage(Long userId, Long useeId) {
        Relation record = new Relation();
        record.setUserId(userId);
        record.setUseeId(useeId);
        record.setDeleted((byte) 0);
        return !CollectionUtils.isEmpty(relationDao.selectByCondition(record));
    }

    @Override
    public BaseResponse create(Long userId, RelationPojos.CreateRequest request) {
        Relation record = new Relation();
        record.setUserId(userId);
        record.setDeleted((byte) 0);
        // 还不能查删除的关系，可能数据太多撑爆内存
        List<RelationDTO> allExists = relationDao.selectByCondition(record);
        // 不能关注太多
        if (allExists.size() + request.data.size() > 64) {
            return new BaseResponse(ErrorCode.ERR_OVER_LIMIT);
        }

        // 不应该这样做。但目前的场景都是一个个修改的，偷个懒不想加接口了。
        List<RelationDTO> result = new ArrayList<>();
        for (RelationDTO item : request.data) {
            record = new Relation();
            record.setUserId(item.getUserId());
            record.setUseeId(item.getUseeId());
            List<RelationDTO> exists = relationDao.selectByCondition(record);
            if (!CollectionUtils.isEmpty(exists)) {
                // 已存在了？
                for (RelationDTO exist : exists) {
                    Relation newRecord = new Relation();
                    newRecord.setId(exist.getId());
                    newRecord.setDeleted((byte) 0);
                    relationDao.updateByPrimaryKeySelective(newRecord);

                    exist.setDeleted((byte) 0);
                    result.add(exist);
                }
            } else {
                relationDao.insertSelective(record);
                if (record.getCreateTime() != null) {
                    record.setCreateTime(new Date());
                }
                result.add(item);
            }
        }

        for (RelationDTO item : result) {
            item.adapt();
        }
        return new RelationPojos.Response(result);
    }

    @Override
    public BaseResponse update(Long userId, RelationPojos.CreateRequest request) {
        List<RelationDTO> result = new ArrayList<>(request.data.size());
        // 不应该这样做。但目前的场景都是一个个修改的，偷个懒不想加接口了。
        for (RelationDTO item : request.data) {
            RelationDTO exist = relationDao.selectByPrimaryKey(item.getId());
            if (exist == null) {
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }
            if (!userId.equals(exist.getUserId()) && !userId.equals(exist.getUseeId())) {
                return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
            }

            // 总有点东西可更新。
            item.setUpdateTime(new Date());
            item.setUserId(null); // 不允许通过 update 修改归属
            item.setUseeId(null);
            relationDao.updateByPrimaryKeySelective(item);
            if (item.getDeleted() == null || item.getDeleted() != 1) {
                RelationDTO updated = relationDao.selectByPrimaryKey(item.getId());
                updated.adapt();
                result.add(updated);
            }
        }
        return new RelationPojos.Response(result);
    }

    @Override
    public BaseResponse list(Long userId, RelationPojos.ListRequest request) {
        List<RelationDTO> relations = relationDao.selectByCondition(request);
        RelationPojos.ListResponse response = new RelationPojos.ListResponse(relations);
        if (CollectionUtils.isEmpty(relations)) {
            return response;
        }

        List<Long> userIds = relations.stream()
                .map(o -> !ObjectUtils.isEmpty(request.getUserId()) ? o.getUseeId() : o.getUserId()).toList();
        List<UserDTO> users = userDao.selectByIds(userIds, false);
        Map<Long, StatEntity> stats = null;
        if (!ObjectUtils.isEmpty(request.getUserId()) && Boolean.TRUE.equals(request.withStat) &&
                !CollectionUtils.isEmpty(userIds)) {
            stats = routineDao.selectCount(userIds, new Date(DateUtils.getStartOfDay(System.currentTimeMillis())))
                    .stream().collect(Collectors.toMap(StatEntity::getId, Function.identity()));
        }
        for (UserDTO user : users) {
            user.adaptMore();
            StatEntity stat = stats != null ? stats.get(user.getId()) : null;
            if (stat != null) {
                stat.adapt();
                user.setRoutine(stat);
            }
        }
        for (RelationDTO relation : relations) {
            relation.adapt();
        }

        response.users = users;
        return response;
    }

    @Override
    public BaseResponse stat(Long userId) {
        RelationPojos.StatResponse response = new RelationPojos.StatResponse();
        response.useeCount = relationDao.selectUseeCount(userId);
        response.userCount = relationDao.selectUserCount(userId);
        return response;
    }
}
