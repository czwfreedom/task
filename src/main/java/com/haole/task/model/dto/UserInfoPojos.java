package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haole.task.model.entity.ConfigDTO;
import com.haole.task.model.entity.UserInfoDTO;
import lombok.Data;

/**
 * 用户相关pojo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface UserInfoPojos {

    class ListRequest extends UserInfoDTO {

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    class ListResponse extends DataResponse<UserInfoDTO> {
        public ConfigDTO routineTemplate;
    }
}
