package com.haole.task.service;

import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.WxPojos;

/**
 * 微信小程序登录服务
 */
public interface WxLoginService {

    /**
     * 小程序登录
     */
    BaseResponse login(WxPojos.WxLoginRequest request);
}
