package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haole.task.model.entity.ResourceDTO;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * 资源相关pojo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ResourcePojos {

    @Data
    class UpdateRequest {
        public Collection<ResourceDTO> data;
    }

    @Data
    class ListRequest {
        public List<Long> ids;

        public ListRequest() {

        }

        public ListRequest(List<Long> ids) {
            this.ids = ids;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class ListResponse extends DataResponse<List<ResourceDTO>> {
        public ListResponse(List<ResourceDTO> data) {
            super(data);
        }
    }
}
