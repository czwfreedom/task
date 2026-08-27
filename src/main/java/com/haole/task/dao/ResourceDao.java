package com.haole.task.dao;

import com.haole.task.model.entity.Resource;
import com.haole.task.model.entity.ResourceDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Resource record);

    ResourceDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);
}