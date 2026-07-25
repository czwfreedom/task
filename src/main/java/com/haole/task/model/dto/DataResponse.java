package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 数据响应
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse<T> extends BaseResponse {
    @JsonProperty("data")
    private T data;

    public DataResponse(T data) {
        super();
        this.data = data;
    }
}
