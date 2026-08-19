package com.haole.task.dao;

import com.haole.task.model.dto.CommentPojos;
import com.haole.task.model.entity.Comment;
import com.haole.task.model.entity.CommentDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Comment record);

    CommentDTO selectByPrimaryKey(Long id);

    List<CommentDTO> selectByRef(@Param("id") Long id, @Param("attrs") Byte attrs);

    // List<CommentPojos.Stat> selectStat(@Param("refs") List<Long> refs);

    int updateByPrimaryKeySelective(Comment record);
}