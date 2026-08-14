package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 配置。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigDTO extends Config {

    public void adapt() {
        setDeleted(null);
        setUpdateTime(null);
    }
}
