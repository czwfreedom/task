package com.haole.task.dao;

import com.haole.task.model.entity.UserInfo;
import com.haole.task.model.entity.UserInfoDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserInfo record);

    UserInfoDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);
}