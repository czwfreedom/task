package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * t_user
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class User extends InfoEntity {
    /**
     * 角色
     */
    private Byte roles;
    /**
     * 类型，保留
     */
    private Byte type;
    /**
     * 性别，保留
     */
    private Byte gender;
    /**
     * token
     */
    private String token;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 微信union_id
     */
    private String wxUnionId;
    /**
     * 微信开发ID
     */
    private String openId;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 登录时间
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date loginTime;
}