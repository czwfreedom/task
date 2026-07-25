package com.haole.task.dao;

import com.haole.task.model.entity.User;
import com.haole.task.model.entity.UserDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(User record);

    UserDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);
}