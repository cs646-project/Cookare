package com.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ApiErrorCode {
    /**
     * 失败
     */
    ERROR(0, "Operation failed!"),
    /**
     * 成功
     */
    SUCCESS(1, "Operation succeeded!"),
    ;

    private final Integer code;
    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return String.format(" ErrorCode:{code=%s, msg=%s} ", code, msg);
    }
}
