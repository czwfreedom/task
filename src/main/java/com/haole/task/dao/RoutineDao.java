package com.haole.task.dao;

import com.haole.task.model.entity.Routine;
import com.haole.task.model.entity.RoutineDTO;
import com.haole.task.model.entity.StatEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface RoutineDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Routine record);

    RoutineDTO selectByPrimaryKey(Long id);

    List<RoutineDTO> selectByCondition(Routine record);

    List<StatEntity> selectCount(@Param("userIds") Collection<Long> userIds, @Param("date") Date date);


    int updateByPrimaryKeySelective(Routine record);
}