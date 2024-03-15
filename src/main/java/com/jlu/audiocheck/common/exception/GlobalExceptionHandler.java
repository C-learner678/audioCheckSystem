package com.jlu.audiocheck.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.jlu.audiocheck.common.result.Result;
import com.jlu.audiocheck.ruleTool.RuleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //请求参数
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e){
        log.error("请求参数异常：" + e.getMessage());
        StringBuffer sb = new StringBuffer();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        allErrors.forEach(msg -> sb.append(msg.getDefaultMessage()).append("；"));
        return Result.fail(sb.toString());
    }
    //登录状态
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = NotLoginException.class)
    public Result handler(NotLoginException e){
        log.error("登录状态异常：" + e.getMessage());
        return Result.fail("请重新登录！");
    }
    //规则校验
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuleException.class)
    public Result handler(RuleException e){
        log.error("RuleException异常：" + e.getMessage());
        return Result.fail("规则不符合要求：" + e.getMessage());
    }
    //Assert
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e){
        log.error("Assert异常：" + e.getMessage());
        return Result.fail(e.getMessage());
    }
    //IO
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IOException.class)
    public Result handler(IOException e){
        log.error("IO异常：" + e.getMessage());
        return Result.fail("出错了！");
    }
    //其他
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("运行时异常：" + e.getMessage());
        return Result.fail("出错了！");
    }
}
