package com.common.domain.vo;

import com.common.constants.ApiCode;
import com.common.constants.Constants;

public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public static <T> Result<T> success(T data) {
        ApiCode aec = ApiCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiCode.ERROR;
        }
        return restResult(aec, data);
    }

    public static <T> Result<T> error(String msg) {
        return restResult(ApiCode.ERROR, msg, null);
    }

    public static <T> Result<T> restResult(ApiCode errorCode, T data) {
        return restResult(errorCode, errorCode.getMsg(), data);
    }

    private static <T> Result<T> restResult(ApiCode code, String msg, T data) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code.getCode());
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}