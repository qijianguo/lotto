package com.yincheng.game.common.exception;

/**
 * @author Administrator
 */
public interface IExceptionEnum {
    /**
     * 获取状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 获取提示信息
     *
     * @return 提示信息
     */
    String getMessage();
}