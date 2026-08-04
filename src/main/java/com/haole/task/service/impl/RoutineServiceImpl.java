package com.haole.task.service.impl;

import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.RoutineDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.RoutinePojos;
import com.haole.task.model.entity.Routine;
import com.haole.task.model.entity.RoutineDTO;
import com.haole.task.service.RelationService;
import com.haole.task.service.RoutineService;
import com.haole.task.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日常服务
 */
@Service
public class RoutineServiceImpl implements RoutineService {

    private static final Logger log = LogUtils.getLogger(RoutineService.class.getSimpleName());

    private final RoutineDao routineDao;
    private final RelationService relationService;

    public RoutineServiceImpl(RoutineDao routineDao, RelationService relationService) {
        this.routineDao = routineDao;
        this.relationService = relationService;
    }

    @Override
    public BaseResponse create(Long userId, RoutinePojos.CreateRequest request) {
        Routine record = new Routine();
        record.setUserId(userId);
        record.setDate(request.data.get(0).getDate());
        record.setDeleted((byte) 0);
        List<RoutineDTO> exists = routineDao.selectByCondition(record);
        // 每天最多创建 16 条日常。
        if (exists.size() + request.data.size() > 16) {
            return new BaseResponse(ErrorCode.ERR_OVER_LIMIT);
        }


        // 不应该这样做。但目前的场景都是一个个修改的，偷个懒不想加接口了。
        for (RoutineDTO item : request.data) {
            if (find(exists, item.getTransaction()) != null) {
                LogUtils.logWarn(log, "RoutineExist", item.getTransaction());
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }

            routineDao.insertSelective(item);
            item.adapt();
        }
        return new RoutinePojos.Response(request.data);
    }

    protected RoutineDTO find(List<RoutineDTO> items, String transaction) {
        if (CollectionUtils.isEmpty(items)) {
            return null;
        }
        for (RoutineDTO item : items) {
            if (transaction.equals(item.getTransaction())) {
                return item;
            }
        }
        return null;
    }

    @Override
    public BaseResponse update(Long userId, RoutinePojos.CreateRequest request) {
        List<RoutineDTO> saved = new ArrayList<>(request.data.size());
        // 不应该这样做。但目前的场景都是一个个修改的，偷个懒不想加接口了。
        for (RoutineDTO item : request.data) {
            RoutineDTO exist = routineDao.selectByPrimaryKey(item.getId());
            if (exist == null) {
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }
            if (!userId.equals(exist.getUserId())) {
                return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
            }

            // 总有点东西可更新。
            item.setUpdateTime(new Date());
            item.setUserId(null); // 不允许通过 update 修改归属
            routineDao.updateByPrimaryKeySelective(item);
            if (item.getDeleted() == null || item.getDeleted() != 1) {
                RoutineDTO updated = routineDao.selectByPrimaryKey(item.getId());
                updated.adapt();
                saved.add(updated);
            }
        }
        return new RoutinePojos.Response(saved);
    }

    @Override
    public BaseResponse list(Long userId, RoutinePojos.ListRequest request) {
        if (!userId.equals(request.getUserId()) && !relationService.canManage(userId, request.getUserId())) {
            return new BaseResponse(ErrorCode.ERR_NO_PERMISSION);
        }

        List<RoutineDTO> result = routineDao.selectByCondition(request);
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(RoutineDTO::adapt);
        }
        return new RoutinePojos.Response(result);
    }
}
