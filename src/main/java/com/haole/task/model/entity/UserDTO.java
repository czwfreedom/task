package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haole.task.model.dto.RoutinePojos;
import lombok.Data;

/**
 * 用户DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDTO extends User {

    /**
     * 暂时没有想好要怎么返回数据，先直接放到用户下面吧。
     */
    public RoutinePojos.UserStat routine;

    @Override
    public void adapt() {
        super.adapt();
        setOpenId(null);
    }

    public void adaptMore() {
        this.adapt();
        this.setToken(null);
        this.setRoles(null);
        this.setLoginTime(null);
    }
}
