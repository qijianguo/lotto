package com.yincheng.game.common.exception;

/**
 * @author qijianguo
 * @version 1.0
 * @Title: CommonError
 * @Description: TODO
 * @date 2019/2/16 12:40
 */
public interface CommonError {

    int getErrorCode();

    String getErrorMsg();

    CommonError setErrorMsg(String errorMsg);
}
