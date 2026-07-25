package com.haole.task.dao;

import com.haole.task.model.entity.User;
import com.haole.task.model.entity.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(User record);

    UserDTO selectByPrimaryKey(Long id);

    List<UserDTO> selectByCondition(User record);

    int updateByPrimaryKeySelective(User record);
}