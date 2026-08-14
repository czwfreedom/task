package com.haole.task.service.impl;

import com.haole.task.dao.ConfigDao;
import com.haole.task.dao.UserInfoDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.UserInfoPojos;
import com.haole.task.model.entity.ConfigDTO;
import com.haole.task.model.entity.UserInfoDTO;
import com.haole.task.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户信息
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoDao userInfoDao;
    private final ConfigDao configDao;

    public UserInfoServiceImpl(UserInfoDao userInfoDao, ConfigDao configDao) {
        this.userInfoDao = userInfoDao;
        this.configDao = configDao;
    }

    @Override
    public BaseResponse update(Long userId, UserInfoDTO request) {
        request.setUpdateTime(new Date());
        userInfoDao.insertSelective(request);

        // 返回具体数据。
        UserInfoPojos.ListRequest record = new UserInfoPojos.ListRequest();
        record.setId(request.getId());
        return list(userId, record);
    }

    @Override
    public BaseResponse list(Long userId, UserInfoPojos.ListRequest request) {
        UserInfoPojos.ListResponse response = new UserInfoPojos.ListResponse();
        UserInfoDTO info = userInfoDao.selectByPrimaryKey(request.getId());
        if (info == null) {
            return response;
        }

        if (info.getRoutineTemplate() != null) {
            ConfigDTO config = configDao.selectByPrimaryKey(info.getRoutineTemplate());
            if (config != null) {
                config.adapt();
                response.routineTemplate = config;
            }
        }

        info.adapt();
        response.setData(info);
        return response;
    }
}
