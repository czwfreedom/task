package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

/**
 * t_config
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Config extends InfoEntity {
    /**
     * 类型，保留
     */
    private Byte type;
    /**
     * 已用次数
     */
    private Integer used;
    /**
     * 优先级，预留
     */
    private Integer priority;
    /**
     * 预留，用于引用别的表
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ref;
    /**
     * 归属用户，为了偷懒，这个也许不是真正的用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 标签，预留
     */
    private String tag;
    /**
     * 内容
     */
    private String value;
    /**
     * 保留扩展
     */
    private String extra;
}