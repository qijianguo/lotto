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
    TIME_ERROR(10007, "系统时间异常，请重新设置"),
    SIGN_ERROR(10010, "Sign error."),
    PARAMETER_ERROR(11000, "Params error."),

    // USER
    USER_NO_PERMISSION(11001, "No permission."),
    USER_TOKEN_EXPIRED(20001, "Please login."),
    USER_TOKEN_INVALID(20002, "Invalid token"),

    /* 20000开头是用户模块错误码 */
    ACCOUNT_INSUFFICIENT_BALANCE(30000, "Your account balance is insufficient."),

    REWARD_REPEATED_ERROR(30001, "Cannot get reward repeated."),

    INVALID_PHONE_CODE(31000, "Invalid phone code！"),


    GAME_CONFIG_NOT_FOUND(400001, "Game config not found")


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
