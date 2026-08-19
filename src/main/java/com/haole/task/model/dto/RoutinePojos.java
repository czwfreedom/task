package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haole.task.model.entity.RoutineDTO;
import com.haole.task.model.entity.StatEntity;

import java.util.List;

/**
 * 日常相关pojo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RoutinePojos {

    class CreateRequest {
        public List<RoutineDTO> data;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Response extends DataResponse<List<RoutineDTO>> {
        public Response(List<RoutineDTO> data) {
            super(data);
        }
    }

    class ListRequest extends RoutineDTO {
        public Boolean withStat;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Stat {
        /**
         * 累计任务。
         */
        public Integer total;
        /**
         * 累计完成任务。
         */
        public Integer finished;
        /**
         * 累计天数。
         */
        public Integer days;
        /**
         * 累计连续天数。
         */
        public Integer rowDays;
    }
}
