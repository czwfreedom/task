package com.haole.task.dao;

import com.haole.task.model.entity.Relation;
import com.haole.task.model.entity.RelationDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Relation record);

    RelationDTO selectByPrimaryKey(Long id);

    List<RelationDTO> selectByCondition(Relation record);

    /**
     * 表示查 userId 可以查看的用户数量
     */
    Integer selectUseeCount(@Param("userId") Long userId);

    /**
     * 表示查可以查看 userId 的用户数量
     */
    Integer selectUserCount(@Param("userId") Long userId);

    int updateByPrimaryKeySelective(Relation record);
}