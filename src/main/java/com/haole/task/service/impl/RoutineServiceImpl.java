package com.haole.task.service.impl;

import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.RoutineDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.RoutinePojos;
import com.haole.task.model.entity.Routine;
import com.haole.task.model.entity.RoutineDTO;
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

    public RoutineServiceImpl(RoutineDao routineDao) {
        this.routineDao = routineDao;
    }

    @Override
    public BaseResponse create(Long userId, RoutinePojos.CreateRequest request) {
        // 不应该这样做。但目前的场景都是一个个修改的，偷个懒不想加接口了。
        for (RoutineDTO item : request.data) {
            Routine record = new Routine();
            record.setTransaction(item.getTransaction());
            if (!CollectionUtils.isEmpty(routineDao.selectByCondition(record))) {
                LogUtils.logWarn(log, "RoutineExist", item.getTransaction());
                return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
            }

            routineDao.insertSelective(item);
            item.adapt();
        }
        return new RoutinePojos.Response(request.data);
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
        List<RoutineDTO> result = routineDao.selectByCondition(request);
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(RoutineDTO::adapt);
        }
        return new RoutinePojos.Response(result);
    }
}
