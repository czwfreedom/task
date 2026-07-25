package com.haole.task.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @RequestLog 注解的切面实现，在目标方法前后记录入参与出参。
 */
@Aspect
@Component
public class RequestLogAspect {

    private static final Logger inputLog = LoggerFactory.getLogger("INPUT");
    private static final Logger outputLog = LoggerFactory.getLogger("OUTPUT");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(com.haole.task.aop.RequestLog)")
    public void requestLogPointcut() {
    }

    @Around("requestLogPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequestLog requestLogAnno = method.getAnnotation(RequestLog.class);

        // 1. 记录 API 输入（在业务逻辑执行之前，保证日志顺序正确）
        String rawBody = readRawBody();
        inputLog.info("v1={}", rawBody);

        long start = System.currentTimeMillis();
        Object result;
        try {
            // 2. 执行 Controller 业务逻辑（此间的日志会自然排在上面的日志 之后）
            result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;

            // 3. 记录 API 输出
            if (requestLogAnno.logResponse()) {
                outputLog.info("elapsed={} v1={}", elapsed, toJson(result));
            } else {
                outputLog.info("elapsed={} v1={}", elapsed, result == null ? "null" : result.getClass().getSimpleName());
            }
        } catch (Throwable t) {
            long elapsed = System.currentTimeMillis() - start;
            outputLog.error("elapsed={} error={}", elapsed, t.toString(), t);
            throw t;
        }

        return result;
    }

    private String readRawBody() {
        Object attr = request.getAttribute("cachedBody");
        if (attr instanceof byte[] body && body.length > 0) {
            return new String(body, StandardCharsets.UTF_8).replaceAll("\\s+", "");
        }
        return "";
    }

    private String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }
}
