package com.common.handler;

import com.common.domain.vo.Result;
import com.common.exceptions.CkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CExceptionHandler {
    @ExceptionHandler(CkException.class)
    public Result CkException(CkException e) {
        log.error("CkException", e);
        return Result.error(e.getCode());
    }
}
