package com.qijianguo.game.common.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qijianguo
 * @version 1.0
 * @Title: BusinessException
 * @Description: TODO 包装器业务异常类实现
 * @date 2019/2/16 12:51
 */
@Slf4j
public class BusinessException extends RuntimeException implements CommonError {

    private CommonError commonError;

    /**
     * 直接接收EmBusinessError的传参，用于构造业务异常
     *
     * @param commonError
     */
    public BusinessException(CommonError commonError) {
        super();
        this.commonError = commonError;
    }

    /**
     * 自定义errorMsg，构造业务异常
     *
     * @param commonError
     * @param errorMsg    自定义错误信息
     */
    public BusinessException(CommonError commonError, String errorMsg) {
        super();
        this.commonError = commonError;
        this.commonError = setErrorMsg(errorMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }
}
