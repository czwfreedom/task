package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * t_comment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Comment extends DBEntity {
    /**
     * 类型，保留
     */
    private Byte type;
    /**
     * 是否点赞/是否评论
     */
    private Byte attrs;
    /**
     * 用于引用别的表
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ref;
    /**
     * 归属用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 详情
     */
    private String detail;
    /**
     * 图片或者视频详情
     */
    private String medias;
    /**
     * 保留扩展
     */
    private String extra;
    /**
     * 点赞时间
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date praiseTime;
    /**
     * 评论时间
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date commentTime;
}