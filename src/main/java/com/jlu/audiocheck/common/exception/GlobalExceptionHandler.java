package com.jlu.audiocheck.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.jlu.audiocheck.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //NotLogin
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NotLoginException.class)
    public Result handler(NotLoginException e){
        log.error("登录状态异常：" + e.getMessage());
        return Result.fail("请重新登录！");
    }
    //Assert
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.error("Assert异常：" + e.getMessage());
        return Result.fail(e.getMessage());
    }
    //其他
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("运行时异常：" + e.getMessage());
        return Result.fail("出错了！");
    }
}
