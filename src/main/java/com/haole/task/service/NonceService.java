package com.haole.task.service;

/**
 * 一些排重操作。
 */
public interface NonceService {
    /**
     * 检查唯一值是否被用了。
     *
     * @return null 表示没有过，true 表示被自己用了，false 表示被别人用了。
     */
    Boolean check(Long userId, String value);

    /**
     * 记录起来。
     */
    int create(Long userId, String value);
}
