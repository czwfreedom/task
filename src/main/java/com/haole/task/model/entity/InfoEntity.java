package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 常规带名字的实体。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public abstract class InfoEntity extends DBEntity {
    private String name;
    private String nickname;
}
