package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 每日任务。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelationDTO extends Relation {
    public void adapt() {
        setUpdateTime(null);
        setDeleted(null);
    }
}
