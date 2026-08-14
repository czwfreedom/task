package com.haole.task.dao;

import com.haole.task.model.entity.Config;
import com.haole.task.model.entity.ConfigDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ConfigDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Config record);

    ConfigDTO selectByPrimaryKey(Long id);

    List<ConfigDTO> selectBy(@Param("ids") Collection<Long> ids,
                             @Param("userIds") Collection<Long> userIds,
                             @Param("tags") Collection<String> tags,
                             @Param("types") Collection<Byte> types,
                             @Param("withDetail") Boolean withDetail);

    int updateByPrimaryKeySelective(Config record);
}