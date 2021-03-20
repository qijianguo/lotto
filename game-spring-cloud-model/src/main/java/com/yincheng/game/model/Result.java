package com.yincheng.game.model;

import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.common.exception.ExceptionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qijianguo
 */
@Data
@NoArgsConstructor
@ApiModel("公共返回结果")
public class Result<T> {

    @ApiModelProperty(value = "成功：ok， 失败：fail", dataType = "String", example = "ok", position = 1)
    private String status;
    @ApiModelProperty(value = "状态码", dataType = "Integer", position = 2)
    private Integer code;
    @ApiModelProperty(value = "描述信息", dataType = "String", position = 3)
    private String message;
    @ApiModelProperty(value = "返回结果", dataType = "Object", position = 5)
    private T result;

    private Result create(String status, Integer code, String message, T result) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.result = result;
        return this;
    }

    public static Result success() {
        return new Result().create("ok", ExceptionEnum._200.getCode(), ExceptionEnum._200.getMessage(), null);
    }

    public static Result success(Object result) {
        return new Result().create("ok", ExceptionEnum._200.getCode(), ExceptionEnum._200.getMessage(), result);
    }

    public static Result success(Integer code, String message, Object result) {
        return new Result().create("ok", code, message, result);
    }

    public static Result fail(Integer code, String message) {
        return new Result().create("fail", code, message, null);
    }

    public static Result fail(EmBusinessError error) {
        return new Result().create("fail", error.getErrorCode(), error.getErrorMsg(), null);
    }

    public static Result fail(EmBusinessError error, String message) {
        return new Result().create("fail", error.getErrorCode(), message, null);
    }

}
