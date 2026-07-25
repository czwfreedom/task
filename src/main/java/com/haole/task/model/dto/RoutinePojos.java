package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haole.task.model.entity.RoutineDTO;

import java.util.List;

/**
 * 日常相关pojo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RoutinePojos {

    class CreateRequest {
        public List<RoutineDTO> data;
    }

    class Response extends DataResponse<List<RoutineDTO>> {
        public Response(List<RoutineDTO> data) {
            super(data);
        }
    }

    class ListRequest extends RoutineDTO {
    }
}
