package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

/**
 * t_user_info
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserInfo extends DBEntity {
    /**
     * 日程模板
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long routineTemplate;
    /**
     * 保留扩展
     */
    private String extra;
}