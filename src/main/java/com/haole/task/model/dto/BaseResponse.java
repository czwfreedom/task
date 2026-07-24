package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.haole.task.constants.ErrorCode;
import lombok.Data;

/**
 * Created by Castle at 2021-12-23
 */
@Data
public class BaseResponse {

    @JsonProperty("errno")
    public int errorCode = ErrorCode.OK;

    @JsonProperty("msg")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String errorMessage;

    public BaseResponse() {
    }

    public BaseResponse(int errorCode) {
        this.errorCode = errorCode;
    }

    public BaseResponse(int code, String msg) {
        this.errorCode = code;
        this.errorMessage = msg;
    }

}
