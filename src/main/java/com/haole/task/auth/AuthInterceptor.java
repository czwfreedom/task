package com.haole.task.auth;

import com.haole.task.constants.Constants;
import com.haole.task.constants.ErrorCode;
import com.haole.task.dao.UserDao;
import com.haole.task.model.entity.UserDTO;
import com.haole.task.service.PermissionService;
import com.haole.task.utils.LogUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * 登录鉴权拦截器。
 * 默认所有 API 需要鉴权，标注 {@link SkipAuth} 的方法/类跳过。
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger log = LogUtils.getLogger(AuthInterceptor.class.getSimpleName());

    private final UserDao userDao;

    public AuthInterceptor(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        // 类或方法上有 @SkipAuth 则跳过
        if (handlerMethod.getBeanType().isAnnotationPresent(SkipAuth.class) ||
                handlerMethod.hasMethodAnnotation(SkipAuth.class)) {
            return true;
        }

        String userId = request.getHeader(Constants.HEADER_USER_ID);
        String userToken = request.getHeader(Constants.HEADER_USER_TOKEN);
        if (ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(userToken)) {
            writeError(response, ErrorCode.ERR_INVALID_TOKEN);
            return false;
        }

        Long id;
        try {
            id = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            writeError(response, ErrorCode.ERR_INVALID_TOKEN);
            return false;
        }

        UserDTO user = userDao.selectByPrimaryKey(id);
        if (user == null || !userToken.equals(user.getToken())) {
            writeError(response, ErrorCode.ERR_INVALID_TOKEN);
            return false;
        }

        // 存入上下文
        PermissionService.setUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        PermissionService.clear();
    }

    private void writeError(HttpServletResponse response, int code) throws IOException {
        writeError(response, code, null);
    }

    private void writeError(HttpServletResponse response, int code, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        if (ObjectUtils.isEmpty(msg)) {
            response.getWriter().write("{\"errno\":" + code + "\"}");
        } else {
            response.getWriter().write("{\"errno\":" + code + ",\"msg\":\"" + msg + "\"}");
        }
    }
}
