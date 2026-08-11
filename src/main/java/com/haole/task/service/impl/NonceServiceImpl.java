package com.haole.task.service.impl;

import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.NonceDao;
import com.haole.task.model.entity.Nonce;
import com.haole.task.model.entity.NonceDTO;
import com.haole.task.service.NonceService;
import com.haole.task.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 唯一值服务。
 */
@Service
public class NonceServiceImpl implements NonceService {
    private static final Logger log = LogUtils.getLogger(NonceService.class.getSimpleName());


    private final NonceDao nonceDao;


    public NonceServiceImpl(NonceDao nonceDao) {
        this.nonceDao = nonceDao;
    }


    @Override
    public Boolean check(Long userId, String value) {
        Nonce record = new Nonce();
        record.setValue(value);
        record.setDeleted((byte) 0);
        List<NonceDTO> items = nonceDao.selectByCondition(record);
        if (CollectionUtils.isEmpty(items)) {
            return null;
        }

        for (NonceDTO item : items) {
            if (userId.equals(item.getUser())) {
                return true;
            }
        }
        LogUtils.log(log, "NonceUsed", value);
        return false;
    }

    @Override
    public int create(Long userId, String value) {
        try {
            Nonce record = new Nonce();
            record.setUser(userId);
            record.setValue(value);
            int effected = nonceDao.insertSelective(record);
            return effected > 0 ? 0 : ErrorCode.ERR_DUPLICATED_OPERATE;
        } catch (Throwable ignore) {
            return ErrorCode.ERR_DUPLICATED_OPERATE;
        }
    }
}
