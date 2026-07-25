package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haole.task.model.entity.UserDTO;
import lombok.Data;

import java.util.List;

/**
 * 微信相关pojo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface WxPojos {

    @JsonIgnoreProperties(ignoreUnknown = true)
    class BaseResponse {
        @JsonProperty("errcode")
        public int errorCode;

        @JsonProperty("errmsg")
        public String errorMsg;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class MiniLoginInfo extends BaseResponse {
        @JsonProperty("openid")
        public String openId;

        @JsonProperty("session_key")
        public String sessionKey;

        @JsonProperty("unionid")
        public String unionId;
    }

    /**
     * 登录用。
     */
    @Data
    class WxLoginRequest {
        public String code;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class WxLoginResponse extends BaseResponse {
        public List<UserDTO> data;
    }
}
