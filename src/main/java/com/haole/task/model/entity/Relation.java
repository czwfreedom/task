package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

/**
 * t_relation
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Relation extends DBEntity {
    /**
     * 类型，保留
     */
    private Byte type;
    /**
     * 星标
     */
    private Byte star;
    /**
     * 用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 被关注用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long useeId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 保留扩展
     */
    private String extra;
}