package com.haole.task.controller;

import com.haole.task.aop.RequestLog;
import com.haole.task.auth.SkipAuth;
import com.haole.task.model.dto.BaseResponse;
import com.haole.task.model.dto.WxPojos;
import com.haole.task.service.WxLoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信登录控制器
 */
@RestController
public class WxController {

    private final WxLoginService wxLoginService;

    public WxController(WxLoginService wxLoginService) {
        this.wxLoginService = wxLoginService;
    }


    @SkipAuth
    @RequestLog
    @PostMapping("/v1/wx/mini/login")
    public BaseResponse miniLogin(@RequestBody WxPojos.WxLoginRequest request) {
        return wxLoginService.login(request);
    }
}
