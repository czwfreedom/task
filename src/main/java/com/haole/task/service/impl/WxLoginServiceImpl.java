package com.haole.task.service.impl;

import com.haole.task.config.WxConfig;
import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.UserDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.WxPojos;
import com.haole.task.model.entity.User;
import com.haole.task.model.entity.UserDTO;
import com.haole.task.service.WxLoginService;
import com.haole.task.utils.IdGenerator;
import com.haole.task.utils.JsonUtils;
import com.haole.task.utils.LogUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 微信小程序登录服务
 */
@Service
public class WxLoginServiceImpl implements WxLoginService {

    private static final Logger log = LogUtils.getLogger(WxLoginService.class.getSimpleName());
    private static final String WX_CODE2SESSION_URL =
            "https://api.weixin.qq.com/sns/jscode2session?appid={1}&secret={2}&js_code={3}&grant_type=authorization_code";
    private final RestTemplate restTemplate;
    private final UserDao userDao;

    @Resource
    private WxConfig wxConfig;

    public WxLoginServiceImpl(UserDao userDao) {
        this.restTemplate = new RestTemplate();
        this.userDao = userDao;
    }

    @Override
    public BaseResponse login(WxPojos.WxLoginRequest request) {
        WxPojos.MiniLoginInfo wxInfo = null;
        try {
            ResponseEntity<byte[]> response = restTemplate.getForEntity(WX_CODE2SESSION_URL, byte[].class,
                    wxConfig.getMini().getAppId(), wxConfig.getMini().getAppSecret(), request.getCode());
            if (response != null) {
                wxInfo = JsonUtils.parseJsonEntity(response, WxPojos.MiniLoginInfo.class);
            }
        } catch (Throwable e) {
            LogUtils.logWarn(log, "WxLoginFailed", e);
            return new BaseResponse(ErrorCode.ERR_WX_API_FAILED);
        }
        if (wxInfo == null || wxInfo.errorCode != 0 || ObjectUtils.isEmpty(wxInfo.openId)) {
            LogUtils.logWarn(log, "WxLoginFailed", wxInfo != null ? wxInfo.errorCode : -1);
            return new BaseResponse(ErrorCode.ERR_WX_API_FAILED);
        }
        // 目前只有一台机器，且没有redis，所以没有分布式锁。
        return doLogin(wxInfo);
    }

    protected synchronized BaseResponse doLogin(WxPojos.MiniLoginInfo wxInfo) {
        User record = new User();
        record.setWxUnionId(wxInfo.unionId);
        record.setOpenId(wxInfo.openId);
        record.setDeleted((byte) 0);
        List<UserDTO> users = userDao.selectByCondition(record);
        if (CollectionUtils.isEmpty(users)) {
            UserDTO user = new UserDTO();
            user.setId(IdGenerator.nextShortId());
            user.setWxUnionId(wxInfo.unionId);
            user.setOpenId(wxInfo.openId);
            user.setLoginTime(new Date());
            user.setToken(generateToken());
            userDao.insertSelective(user);
            users = userDao.selectByCondition(record);
        }

        if (!CollectionUtils.isEmpty(users)) {
            users.forEach(UserDTO::adapt);
        }
        return new WxPojos.WxLoginResponse(users);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
