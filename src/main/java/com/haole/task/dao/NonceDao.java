package com.haole.task.dao;

import com.haole.task.model.entity.Nonce;
import com.haole.task.model.entity.NonceDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NonceDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Nonce record);

    NonceDTO selectByPrimaryKey(Long id);

    List<NonceDTO> selectByCondition(Nonce record);

    int updateByPrimaryKeySelective(Nonce record);
}