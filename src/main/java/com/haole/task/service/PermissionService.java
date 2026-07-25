package com.haole.task.service;

import com.haole.task.model.entity.UserDTO;

/**
 * 当前请求的用户上下文，基于 ThreadLocal。
 * 鉴权通过后由拦截器存入，业务代码通过 {@link #getUser()} 获取。
 */
public final class PermissionService {

    private static final ThreadLocal<UserDTO> USER_HOLDER = new ThreadLocal<>();

    private PermissionService() {
    }

    /**
     * 获取当前请求的用户，未鉴权时返回 null。
     */
    public static UserDTO getUser() {
        return USER_HOLDER.get();
    }

    /**
     * 设置当前请求的用户（由 AuthInterceptor 调用）。
     */
    public static void setUser(UserDTO user) {
        USER_HOLDER.set(user);
    }

    /**
     * 清理（由 AuthInterceptor 在 afterCompletion 中调用）。
     */
    public static void clear() {
        USER_HOLDER.remove();
    }
}
