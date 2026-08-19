package com.haole.task.service.impl;

import com.haole.task.dao.UserDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.DataResponse;
import com.haole.task.model.entity.User;
import com.haole.task.model.entity.UserDTO;
import com.haole.task.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日常服务
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDTO get(Long id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public List<UserDTO> get(List<Long> ids, Boolean withDetail) {
        return userDao.selectByIds(ids, withDetail);
    }

    @Override
    public BaseResponse update(Long userId, UserDTO request) {
        User newRecord = new User();
        newRecord.setId(request.getId());
        newRecord.setName(request.getName());
        newRecord.setNickname(request.getNickname());
        newRecord.setAvatar(request.getAvatar());
        newRecord.setPhone(request.getPhone());
        userDao.updateByPrimaryKeySelective(newRecord);

        UserDTO user = userDao.selectByPrimaryKey(userId);
        user.adapt();
        return new DataResponse<>(user);
    }
}
