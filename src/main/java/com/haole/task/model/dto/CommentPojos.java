package com.haole.task.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.haole.task.model.entity.CommentDTO;
import com.haole.task.model.entity.StatEntity;
import com.haole.task.model.entity.UserDTO;

import java.util.List;

/**
 * 评论相关pojo
 */
public interface CommentPojos {

    class ListRequest extends CommentDTO {

    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    class ListResponse extends DataResponse<List<CommentDTO>> {
        public List<UserDTO> users;

        public ListResponse(List<CommentDTO> data) {
            super(data);
        }
    }

    class Stat extends StatEntity {
        public Integer comment;
    }
}
