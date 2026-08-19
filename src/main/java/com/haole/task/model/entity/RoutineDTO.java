package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haole.task.model.dto.CommentPojos;
import lombok.Data;

/**
 * 每日任务。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RoutineDTO extends Routine {
    /**
     * 统计。
     */
    public CommentPojos.Stat stat;

}
