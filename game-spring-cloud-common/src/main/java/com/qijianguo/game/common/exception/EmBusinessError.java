package com.qijianguo.game.common.exception;

/**
 * @author qijianguo
 */
public enum EmBusinessError implements CommonError {

    // 10000开头是通用错误类型,
    UNKNOW_ERROR(10001, "System error, try again later."),
    SYSTEM_BUSY(10002, "System busy, try again later."),
    DATE_FORMAT_ERROR(10003, "Date format error."),
    REPEAT_COMMIT_ERROR(10004, "Resubmit error."),
    PARAMETER_ERROR(10005, "Params error."),
    SOURCE_NOT_FOUND(10006, "Source not found error."),

    // USER
    /* 20000开头是用户模块错误码 */
    USER_NO_PERMISSION(20000, "No permission."),
    USER_TOKEN_EXPIRED(20001, "Please login."),

    // ACCOUNT
    ACCOUNT_INSUFFICIENT_BALANCE(30000, "Your account balance is insufficient."),
    REWARD_REPEATED_ERROR(30001, "Cannot get reward repeated."),
    ACCOUNT_PREPAID_ORDER_NOT_FOUND(30002, "Prepaid order not found."),
    ACCOUNT_ORDER_NO_PAY(30003, "Order no success"),
    WITHDRAW_BANK_NOT_FOUND(30004, "Bank info not found."),
    WITHDRAW_ALREADY_REVIEWED(30005, "Withdraw already reviewed."),
    WITHDRAW_CREDIT_LIMITED(30006, "The withdrawal amount is too small."),

    INVALID_FACEBOOK_CODE(30002, "Verify the code failed."),
    INVALID_FACEBOOK_ACC_TOKEN(20003, "Verify the access token failed."),
    INVALID_PHONE_CODE(31000, "Invalid phone code."),


    GAME_CONFIG_NOT_FOUND(40001, "Lotto config not found."),

    PERIOD_DRAWN(40002, "Lotto period already been drawn."),

    SINOPAY_SIGN_ERROR(50001, "Sign error."),
    SINOPAY_ORDER_FIELD(50002, "Prepaid failed."),
    SINOPAY_CURRENCY_NOT_SUPPORT(50003, "Currency not support."),
    SINOPAY_DISBURSE_FIELD(50004, "Disburse failed.")

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
