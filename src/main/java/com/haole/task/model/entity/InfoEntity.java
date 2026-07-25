package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * 常规带名字的实体。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public abstract class InfoEntity implements IInfoEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;
    private String nickname;
    private Byte deleted;


    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTime;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updateTime;
}
