package com.haole.task.constants;

/**
 * Created by Castle at 2021-12-23
 */
public interface ErrorCode {

    int OK = 0;

    /**
     * 非法token
     */
    int ERR_INVALID_TOKEN = 10001;
    /**
     * 非法参数
     */
    int ERR_INVALID_PARAM = 10002;
    /**
     * 执行该接口的权限不足
     */
    int ERR_NO_PERMISSION = 10003;

    /**
     * 所有重复的操作，包括重复签到这些。
     */
    int ERR_DUPLICATED_OPERATE = 10004;

    /**
     * 微信接口调用失败
     */
    int ERR_WX_API_FAILED = 10005;

    /**
     * 超过限制了。
     */
    int ERR_OVER_LIMIT = 10006;

    /**
     * 未知的错误。
     */
    int ERR_SERVER_FAILED = 20001;

}
