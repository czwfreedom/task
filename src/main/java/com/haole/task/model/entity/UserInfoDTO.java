package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 用户DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserInfoDTO extends UserInfo {
}
