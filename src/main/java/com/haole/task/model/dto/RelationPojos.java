package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haole.task.model.entity.RelationDTO;
import com.haole.task.model.entity.UserDTO;

import java.util.List;

/**
 * 关系相关pojo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RelationPojos {

    class CreateRequest {
        public List<RelationDTO> data;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Response extends DataResponse<List<RelationDTO>> {
        public Response(List<RelationDTO> data) {
            super(data);
        }
    }

    class ListRequest extends RelationDTO {
        /**
         * 拉当天的统计。
         */
        public Boolean withStat;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class ListResponse extends Response {
        public List<UserDTO> users;

        public ListResponse(List<RelationDTO> data) {
            super(data);
        }
    }
}
