package com.qijianguo.game.common.exception;

/**
 * @author qijianguo
 * @version 1.0
 * @Title: CommonError
 * @Description: TODO
 * @date 2019/2/16 12:40
 */
public interface CommonError {

    /**
     * 错误码
     * @return
     */
    int getErrorCode();

    /**
     * 错误信息
     * @return
     */
    String getErrorMsg();

    CommonError setErrorMsg(String errorMsg);
}
