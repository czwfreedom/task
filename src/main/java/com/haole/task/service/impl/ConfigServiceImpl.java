package com.haole.task.service.impl;

import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.ConfigDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.ConfigPojos;
import com.haole.task.model.dto.DataResponse;
import com.haole.task.model.entity.ConfigDTO;
import com.haole.task.service.ConfigService;
import com.haole.task.utils.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 配置服务。
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    private final ConfigDao configDao;

    public ConfigServiceImpl(ConfigDao configDao) {
        this.configDao = configDao;
    }

    @Override
    public ConfigDTO get(Long id) {
        return configDao.selectByPrimaryKey(id);
    }

    @Override
    public BaseResponse create(Long userId, ConfigDTO request) {
        List<ConfigDTO> exists = configDao.selectBy(null, Collections.singletonList(userId), null,
                Collections.singletonList(request.getType()), false);
        if (exists.size() >= 16) {
            return new BaseResponse(ErrorCode.ERR_OVER_LIMIT);
        }

        // 没必要用大数字？
        request.setId(IdGenerator.nextShortId());
        int effected = configDao.insertSelective(request);
        if (effected <= 0) {
            return new BaseResponse(ErrorCode.ERR_SERVER_FAILED);
        }
        ConfigDTO result = configDao.selectByPrimaryKey(request.getId());
        result.adapt();
        return new DataResponse<>(result);
    }

    @Override
    public BaseResponse update(Long userId, ConfigDTO request) {
        ConfigDTO exist = configDao.selectByPrimaryKey(request.getId());
        if (exist == null) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }
        // 总有点东西可以更新。
        request.setUpdateTime(new Date());
        configDao.updateByPrimaryKeySelective(request);

        if (request.getDeleted() == null || request.getDeleted() == 0) {
            ConfigDTO result = configDao.selectByPrimaryKey(request.getId());
            result.adapt();
            return new DataResponse<>(result);
        }
        return new BaseResponse();
    }

    @Override
    public BaseResponse list(Long userId, ConfigPojos.ListRequest request) {
        List<ConfigDTO> result = configDao.selectBy(request.ids, request.userIds, request.tags,
                request.types, !Boolean.TRUE.equals(request.brief));
        for (ConfigDTO item : result) {
            item.adapt();
        }
        return new ConfigPojos.ListResponse(result);
    }
}
