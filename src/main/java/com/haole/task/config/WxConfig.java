package com.haole.task.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("wx")
public class WxConfig {

    private Mini mini;

    @Data
    public static class Mini {
        private String appId;
        private String appSecret;
    }
}
