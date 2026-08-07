package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

/**
 * 统计实体。
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatEntity implements IIdEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer count;

    public void adapt() {
        this.id = null;
    }

    @Override
    public Long getId() {
        return id;
    }
}
