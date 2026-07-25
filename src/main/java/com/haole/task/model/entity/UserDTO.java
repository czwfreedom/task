package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 用户DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends User {

    public void adapt() {
        setUpdateTime(null);
        setOpenId(null);
        setDeleted(null);
    }
}
