package com.haole.task.dao;

import com.haole.task.model.entity.Routine;
import com.haole.task.model.entity.RoutineDTO;
import com.haole.task.model.entity.StatEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface RoutineDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Routine record);

    RoutineDTO selectByPrimaryKey(Long id);

    List<RoutineDTO> selectByCondition(Routine record);

    List<StatEntity> selectCount(@Param("userIds") Collection<Long> userIds, @Param("date") Date date);

    Map<String, Object> selectTotalStat(@Param("userId") Long userId);

    List<Date> selectDistinctDates(@Param("userId") Long userId);

    int updateByPrimaryKeySelective(Routine record);
}