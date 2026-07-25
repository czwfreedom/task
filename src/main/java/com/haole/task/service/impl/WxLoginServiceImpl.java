package com.haole.task.service.impl;

import com.haole.task.config.WxConfig;
import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.UserDao;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.WxPojos;
import com.haole.task.service.WxLoginService;
import com.haole.task.utils.LogUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

/**
 * 微信小程序登录服务
 */
@Service
public class WxLoginServiceImpl implements WxLoginService {

    private static final Logger log = LoggerFactory.getLogger(WxLoginService.class.getSimpleName());
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
            wxInfo = restTemplate.getForObject(WX_CODE2SESSION_URL, WxPojos.MiniLoginInfo.class,
                    wxConfig.getMini().getAppId(), wxConfig.getMini().getAppSecret(), request.getCode());
        } catch (Throwable e) {
            return new BaseResponse(ErrorCode.ERR_WX_API_FAILED);
        }
        if (wxInfo == null || wxInfo.errorCode != 0 || ObjectUtils.isEmpty(wxInfo.openId) ||
                ObjectUtils.isEmpty(wxInfo.unionId)) {
            LogUtils.logWarn(log, "WxLoginFailed", wxInfo != null ? wxInfo.errorCode : -1);
            return new BaseResponse(ErrorCode.ERR_WX_API_FAILED);
        }

        return new BaseResponse();
    }
//
//    /**
//     * 小程序登录
//     */
//    public WxLoginResponse login(String code) {
//        // 1. 调用微信接口换取 unionId
//        String wxResp;
//        try {
//            wxResp = restTemplate.getForObject(WX_CODE2SESSION_URL, String.class, appId, appSecret, code);
//        } catch (Exception e) {
//            log.error("call wechat code2session failed", e);
//            return WxLoginResponse.fail(ErrorCode.ERR_INVALID_PARAM, "微信接口调用失败");
//        }
//
//        if (wxResp == null || wxResp.isEmpty()) {
//            return WxLoginResponse.fail(ErrorCode.ERR_INVALID_PARAM, "微信接口无响应");
//        }
//
//        // 2. 解析微信返回
//        WxLoginService.WxSessionResult sessionResult;
//        try {
//            sessionResult = objectMapper.readValue(wxResp, WxLoginService.WxSessionResult.class);
//        } catch (Exception e) {
//            log.error("parse wechat response failed: {}", wxResp, e);
//            return WxLoginResponse.fail(ErrorCode.ERR_INVALID_PARAM, "微信响应解析失败");
//        }
//
//        if (sessionResult.errcode != null && sessionResult.errcode != 0) {
//            log.warn("wechat error: errcode={}, errmsg={}", sessionResult.errcode, sessionResult.errmsg);
//            return WxLoginResponse.fail(ErrorCode.ERR_INVALID_PARAM, "微信登录失败: " + sessionResult.errmsg);
//        }
//
//        String unionId = sessionResult.unionid;
//        if (unionId == null || unionId.isEmpty()) {
//            log.warn("unionid is empty, openid={}", sessionResult.openid);
//            return WxLoginResponse.fail(ErrorCode.ERR_INVALID_PARAM, "获取 unionId 失败，请确保小程序已绑定开放平台");
//        }
//
//        // 3. 查库
//        UserDTO existUser = userDao.selectByUnionId(unionId);
//
//        if (existUser == null) {
//            // 4. 新用户：创建
//            User newUser = new User();
//            newUser.setWxUnionId(unionId);
//            newUser.setToken(generateToken());
//            newUser.setLoginTime(new Date());
//            newUser.setCreateTime(new Date());
//            userDao.insertSelective(newUser);
//
//            log.info("new user created: id={}, unionId={}", newUser.getId(), unionId);
//            return WxLoginResponse.ok(newUser.getId(), null, null, null, newUser.getToken());
//        } else {
//            // 5. 老用户：更新登录时间
//            User updateUser = new User();
//            updateUser.setId(existUser.getId());
//            updateUser.setLoginTime(new Date());
//            userDao.updateByPrimaryKeySelective(updateUser);
//
//            log.info("user login: id={}, unionId={}", existUser.getId(), unionId);
//            return WxLoginResponse.ok(existUser.getId(), existUser.getName(),
//                    existUser.getNickname(), existUser.getAvatar(), existUser.getToken());
//        }
//    }
//
//    private String generateToken() {
//        return UUID.randomUUID().toString().replace("-", "");
//    }
}
