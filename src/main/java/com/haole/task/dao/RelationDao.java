package com.haole.task.dao;

import com.haole.task.model.entity.Relation;
import com.haole.task.model.entity.RelationDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Relation record);

    RelationDTO selectByPrimaryKey(Long id);

    List<RelationDTO> selectByCondition(Relation record);

    int updateByPrimaryKeySelective(Relation record);
}