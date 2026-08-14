package com.haole.task.model.dto;

import com.haole.task.model.entity.ConfigDTO;

import java.util.Collection;
import java.util.List;

/**
 * 配置相关pojo
 */
public interface ConfigPojos {

    class ListRequest {
        public Collection<Long> ids;
        public Collection<Long> userIds;
        public Collection<String> tags;
        public Collection<Byte> types;
        public Boolean brief;
    }

    class ListResponse extends DataResponse<List<ConfigDTO>> {
        public ListResponse(List<ConfigDTO> data) {
            super(data);
        }
    }
}
