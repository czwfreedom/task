package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

/**
 * t_nonce
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Nonce extends DBEntity {
    /**
     * 类型，保留
     */
    private Byte type;
    /**
     * 可用次数
     */
    private Integer count;
    /**
     * 已用次数
     */
    private Integer used;

    /**
     * 创建的用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creator;

    /**
     * 使用的用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long user;

    /**
     * 唯一值
     */
    private String value;
}