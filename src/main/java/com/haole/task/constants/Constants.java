package com.haole.task.constants;

/**
 * Created by Castle at 2021-12-23
 */
public interface Constants {
    String HEADER_USER_ID = "user-id";
    String HEADER_USER_TOKEN = "user-token";

    String ENTITY_KEY_GENERATOR = "entityKeyGenerator";


    interface User {
        /**
         * 表示1为系统预留。
         */
        Long SYSTEM = 1L;
    }
}
