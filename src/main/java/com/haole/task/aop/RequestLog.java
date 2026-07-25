package com.haole.task.aop;

import java.lang.annotation.*;

/**
 * API 请求日志注解，记录每个接口的入参与出参。
 * <p>
 * logResponse = false 时，只输出摘要（记录返回类型和状态码），不输出完整返回值。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLog {

    /** 是否记录完整的接口返回值，默认 true */
    boolean logResponse() default true;
}
