package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 每日任务。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoutineDTO extends Routine {

    public void adapt() {
        setUpdateTime(null);
        setDeleted(null);
    }
}
