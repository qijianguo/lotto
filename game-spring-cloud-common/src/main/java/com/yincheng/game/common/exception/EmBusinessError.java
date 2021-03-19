package com.yincheng.game.common.exception;

/**
 * @author qijianguo
 */
public enum EmBusinessError implements CommonError {

    // 10000开头是通用错误类型,
    UNKNOW_ERROR(10001, "请求失败，请重试"),
    SYSTEM_BUSY(10002, "系统繁忙，请重试"),
    RESOURCE_NOT_FOUND(10003, "未查询到记录"),
    DATE_FORMAT_ERROR(10004, "日期格式不正确"),
    ACCESS_DENIED(10005, "用户状态异常"),
    REPEAT_COMMIT_ERROR(10006, "请不要重复提交哦"),
    SYSTEM_TIME_ERROR(10007, "系统时间异常，请重新设置"),
    RESOURCE_ALREADY_EXISTS(10009, "记录已存在"),
    SIGN_ERROR(10010, "签名错误"),
    PARAMETER_ERROR(11000, "参数有误哦"),

    /* 20000开头是用户模块错误码 */
    ACCOUNT_INSUFFICIENT_BALANCE(30000, "Your account balance is insufficient."),


    GAME_CONFIG_NOT_FOUND(400001, "game config not found")


    ;

    /* 30000开头的为账户模块错误码 */



    private Integer errorCode;

    private String errorMsg;


    EmBusinessError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
