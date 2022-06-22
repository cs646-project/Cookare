package com.common.exceptions;

public class CkException extends RuntimeException {

    private String code;

    private String message;

    private Object[] args;

    public CkException(String code) {
        this.code = code;
    }

    public CkException(String code, Object... args) {
        this.code = code;
        this.args = args;
    }

    public CkException(Throwable e, String code) {
        this.code = code;
        this.message = e.getMessage();
    }

    public CkException(Throwable e, String code, String... args) {
        this.code = code;
        this.message = e.getMessage();
        this.args = args;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public String getCode() {
        return this.code;
    }

    public Object[] getArgs() {
        return this.args;
    }
}
