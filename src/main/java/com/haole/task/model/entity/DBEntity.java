package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DBEntity implements IDBEntity {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Byte deleted;


    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTime;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date updateTime;

    public void adapt() {
        this.setDeleted(null);
        this.setUpdateTime(null);
    }
}
