package com.haole.task.dao;

import com.haole.task.model.entity.Resource;
import com.haole.task.model.entity.ResourceDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ResourceDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Resource record);

    ResourceDTO selectByPrimaryKey(Long id);

    List<ResourceDTO> selectByHashes(@Param("hashes") Collection<String> hashes);

    /**
     * 根据id查询资源
     */
    List<ResourceDTO> selectByIds(@Param("ids") Collection<Long> ids);

    int updateByPrimaryKeySelective(Resource record);
}