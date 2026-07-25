package com.haole.task.utils;

import java.security.SecureRandom;

/**
 * 随机 Long ID 生成器，防止资源被枚举。
 * 使用 SecureRandom 保证不可预测性，碰撞概率极低（1/2^63）。
 */
public final class IdGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private IdGenerator() {
    }

    /**
     * 生成一个正的随机 Long ID（1 ~ Long.MAX_VALUE）。
     * 63 位有效空间，碰撞概率接近于零。
     */
    public static long nextId() {
        long id;
        do {
            id = RANDOM.nextLong();
        } while (id <= 0);
        return id;
    }

    /**
     * 生成一个相对短的正随机 ID，取低 48 位（约 2.8e14 种可能），
     * 足够大防枚举，同时 ID 长度更短，前端/日志更友好。
     */
    public static long nextShortId() {
        long id;
        do {
            id = RANDOM.nextLong() & 0x0000_FFFF_FFFF_FFFFL;
        } while (id <= 0);
        return id;
    }
}
