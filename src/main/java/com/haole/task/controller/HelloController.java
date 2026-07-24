package com.haole.task.controller;

import com.haole.task.model.dto.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    public HelloController() {
    }

    @PostMapping("/v1/hello")
    public BaseResponse hello() {
        return new BaseResponse();
    }
}
