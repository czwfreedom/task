package com.haole.task.auth;

import java.lang.annotation.*;

/**
 * 标记在 Controller 方法或类上，跳过登录鉴权检查。
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SkipAuth {
}
