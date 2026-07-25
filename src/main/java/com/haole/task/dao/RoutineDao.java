package com.haole.task.dao;

import com.haole.task.model.entity.Routine;
import com.haole.task.model.entity.RoutineDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Routine record);

    RoutineDTO selectByPrimaryKey(Long id);

    List<RoutineDTO> selectByCondition(Routine record);


    int updateByPrimaryKeySelective(Routine record);
}