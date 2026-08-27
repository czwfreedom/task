package com.haole.task.service.impl;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.haole.task.config.AliyunConfig;
import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.ResourceDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.DataResponse;
import com.haole.task.model.dto.ResourcePojos;
import com.haole.task.model.entity.ResourceDTO;
import com.haole.task.service.ResourceService;
import com.haole.task.utils.IdGenerator;
import com.haole.task.utils.LogUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源服务
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger log = LogUtils.getLogger(ResourceServiceImpl.class.getSimpleName());

    private final ResourceDao resourceDao;
    private final OSSClient ossClient;

    @Resource
    private AliyunConfig aliyunConfig;

    public ResourceServiceImpl(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;

        // 帮我创建。
    }

    @Override
    public BaseResponse create(Long userId, ResourceDTO request) {
        // 先排重。
        List<ResourceDTO> exists = resourceDao.selectByHashes(Collections.singletonList(request.getHash()));
        if (!CollectionUtils.isEmpty(exists)) {
            for (ResourceDTO item : exists) {
                if (item.getChecked() != null && item.getChecked() != 0) {
                    adapt(item);
                    return new DataResponse<>(item);
                }
            }
        }

        request.setCreator(userId);
        request.setId(IdGenerator.nextId());

        ResourceDTO.OSSUpload upload = new ResourceDTO.OSSUpload();
        upload.setKey(generateUploadKey(request));
        request.setUpload(upload);


        return null;
    }

    @Override
    public BaseResponse update(Long userId, ResourcePojos.UpdateRequest request) {
        List<Long> ids = request.getData().stream().map(ResourceDTO::getId).collect(Collectors.toList());
        List<ResourceDTO> exists = resourceDao.selectByIds(ids);
        if (exists.size() != request.getData().size()) {
            return new BaseResponse(ErrorCode.ERR_INVALID_PARAM);
        }

        for (ResourceDTO item : request.getData()) {
            // 让总有点东西可以更新。
            item.setUpdateTime(new Date());
            resourceDao.updateByPrimaryKeySelective(item);
        }

        return list(userId, new ResourcePojos.ListRequest(ids));
    }

    @Override
    public BaseResponse list(Long userId, ResourcePojos.ListRequest request) {
        List<ResourceDTO> items = new ArrayList<>(request.getIds().size());
        if (!CollectionUtils.isEmpty(request.getIds())) {
            items = resourceDao.selectByIds(request.getIds());
        }

        if (!CollectionUtils.isEmpty(items)) {
            for (ResourceDTO item : items) {
                adapt(item);
            }
        }
        return new ResourcePojos.ListResponse(items);
    }

    protected String generateUploadKey(ResourceDTO request) {
        return "";
    }


    private void adapt(ResourceDTO item) {
        item.adapt();
    }

}
