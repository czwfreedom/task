package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * t_relation
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Relation implements IIdEntity {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 是否已删除
     */
    private Byte deleted;
    /**
     * 类型，保留
     */
    private Byte type;
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
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTime;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updateTime;
}