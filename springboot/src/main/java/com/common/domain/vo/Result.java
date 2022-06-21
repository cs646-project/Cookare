package com.common.domain.vo;

import com.common.constants.ApiErrorCode;
import com.common.constants.Constants;

/**
 * 包装返回前端的对象
 * @param <T>
 */

public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static Result success() {
        Result result = new Result<>();
        result.setCode(0);
        result.setMsg("成功");
        return result;
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> success(T data) {
        ApiErrorCode aec = ApiErrorCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiErrorCode.ERROR;
        }
        return restResult(aec, data);
    }

    public static <T> Result<T> error(String msg) {
        return restResult(Constants.ERROR, msg, null);
    }

    public static <T> Result<T> restResult(ApiErrorCode errorCode, T data) {
        return restResult(errorCode.getCode(), errorCode.getMsg(), data);
    }

    private static <T> Result<T> restResult(Integer code, String msg, T data) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}