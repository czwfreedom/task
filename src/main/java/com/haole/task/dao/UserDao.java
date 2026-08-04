package com.haole.task.dao;

import com.haole.task.model.entity.User;
import com.haole.task.model.entity.UserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(User record);

    UserDTO selectByPrimaryKey(Long id);

    List<UserDTO> selectByCondition(User record);

    List<UserDTO> selectByIds(@Param("ids") Collection<Long> ids, @Param("withDetail") Boolean withDetail);

    int updateByPrimaryKeySelective(User record);
}