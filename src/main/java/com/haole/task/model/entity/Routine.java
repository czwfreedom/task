package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * t_routine
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Routine extends InfoEntity {
    /**
     * 任务状态
     */
    private Byte status;
    /**
     * 任务类型
     */
    private Integer category;
    /**
     * 任务子类型
     */
    private Integer subcategory;
    /**
     * 归属用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 计划时长。
     */
    private Long duration;
    /**
     * 任务日期
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date date;

    /**
     * 任务计划时间
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date planTime;
    /**
     * 防重提交
     */
    private String transaction;
    /**
     * 详情
     */
    private String detail;
    /**
     * 图片或者视频详情
     */
    private String medias;
    /**
     * 反馈
     */
    private String remark;
    /**
     * 图片或者视频反馈
     */
    private String mediaRemark;
    /**
     * 保留扩展
     */
    private String extra;
    /**
     * 完成时间
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date finishTime;
}