package com.haole.task.aop;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * 请求追踪 Filter，每个请求进入时：
 * <ul>
 *   <li>生成唯一 rid（UUID，截取前 12 位）</li>
 *   <li>记录请求 url</li>
 *   <li>注入到 MDC，后续所有日志自动携带这两个字段</li>
 * </ul>
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String rid = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        String url = request.getRequestURI();
        CachedBodyRequestWrapper wrappedRequest = new CachedBodyRequestWrapper(request);
        wrappedRequest.setAttribute("cachedBody", wrappedRequest.getBody());

        try {
            MDC.put("trace", " [" + rid + "][" + url + "]");
            filterChain.doFilter(wrappedRequest, response);
        } finally {
            MDC.clear();
        }
    }
}
