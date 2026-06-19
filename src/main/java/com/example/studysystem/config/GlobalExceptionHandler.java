package com.example.studysystem.config;

import com.example.studysystem.vo.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ApiResult<Void> handle(Exception e) {
        return ApiResult.error(e.getMessage() == null ? "操作失败" : e.getMessage());
    }
}
