package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 资源。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResourceDTO extends Resource {
    /**
     * 已上传。
     */
    private Boolean uploaded;
}
