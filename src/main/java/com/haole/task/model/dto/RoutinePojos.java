package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.haole.task.model.entity.RoutineDTO;
import com.haole.task.model.entity.StatEntity;
import com.haole.task.model.entity.UserDTO;
import lombok.Data;

import java.util.Date;
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
        /**
         * 相关ID
         */
        public List<UserDTO> users;

        public Response(List<RoutineDTO> data) {
            super(data);
        }
    }

    @Data
    class ListRequest extends RoutineDTO {
        public Boolean withStat;

        public Boolean brief;

        public Boolean withDelegated;

        @JsonFormat(shape = JsonFormat.Shape.NUMBER)
        public Date startDate;
        @JsonFormat(shape = JsonFormat.Shape.NUMBER)
        public Date endDate;
    }


    class UserStat extends StatEntity {
        public Integer finished;
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
