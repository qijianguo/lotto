package com.yincheng.game.web.advice;

import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author qijianguo
 * 异常信息统一处理
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    /**
     * 系统自定义异常
     *
     * @param e 自定义异常信息
     * @return 系统统一的异常信息
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result handleBusinessException(BusinessException e) {
        /*ApiRequestParams apiRequestParams = ThreadLocalUtils.get(ThreadLocalKey.REQUEST_PARAM.name(), new ApiRequestParams());
        if (apiRequestParams != null) {
            log.warn("request params：{}, error_code= [{}], error_msg= [{}], params=[{}]", apiRequestParams, e.getErrorCode(), e.getErrorMsg());
        }*/
        return Result.fail(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 系统内部错误
     *
     * @param e 系统异常
     * @return 系统异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result onException(Exception e) {
        /*ApiRequestParams apiRequestParams = ThreadLocalUtils.get(ThreadLocalKey.REQUEST_PARAM.name(), new ApiRequestParams());
        if (apiRequestParams != null) {
            log.error("request params：{}, error message：{}, stack: {}", apiRequestParams, e.getMessage(), e);
        }*/
        return Result.fail(EmBusinessError.UNKNOW_ERROR.getErrorCode(), EmBusinessError.UNKNOW_ERROR.getErrorMsg());
    }

}
