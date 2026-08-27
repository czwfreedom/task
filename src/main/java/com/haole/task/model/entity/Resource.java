package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * t_resource
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resource extends InfoEntity {
    /**
     * 类型
     */
    private Byte type;
    /**
     * 质量，保留
     */
    private Byte quality;
    /**
     * 图片宽度
     */
    private Integer width;
    /**
     * 图片高度
     */
    private Integer height;
    /**
     * 文件大小
     */
    private Integer size;
    /**
     * 时长
     */
    private Integer duration;
    /**
     * 文件时间
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date time;
    /**
     * 用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creator;
    /**
     * Bucket
     */
    private String bucket;
    /**
     * hash，用于排重。
     */
    private String hash;
    /**
     * 名字
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 预留的标签
     */
    private String tag;
    /**
     * 保留扩展
     */
    private String extra;
}