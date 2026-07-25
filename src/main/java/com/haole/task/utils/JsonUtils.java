package com.haole.task.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

/**
 * 一些json函数。
 */
public class JsonUtils {
    public static <T> T parseJsonEntity(ResponseEntity<byte[]> entity, Class<T> type) throws RestClientException {
        if (entity.getStatusCode() != HttpStatus.OK) {
            throw new RestClientException("status code: " + entity.getStatusCode());
        }

        byte[] bodyData = entity.getBody();
        if (bodyData == null) {
            throw new RestClientException("null body");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new String(bodyData, StandardCharsets.UTF_8), type);
        } catch (Throwable e) {
            throw new RestClientException("request failed", e);
        }
    }
}
