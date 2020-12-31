package com.yintu.rixing.config.controller;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.util.ResponseDataUtil;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2020/11/26 13:54
 * @Version: 1.0
 */
@RestControllerAdvice
public class GlobalExceptionController {

    @Log(level = EnumLogLevel.ERROR, module = "系统全局", description = "参数异常")
    @ExceptionHandler(BindException.class)
    public Map<String, Object> bindException(BindException e) {
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(" ");
        }
        return ResponseDataUtil.error(sb.toString());
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统全局", description = "数据库异常")
    @ExceptionHandler(SQLException.class)
    public Map<String, Object> sqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return ResponseDataUtil.error("值不能重复，操作失败" + e.getMessage());
        }
        return ResponseDataUtil.error("数据库异常，操作失败");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统全局", description = "文件上传异常")
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Map<String, Object> maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResponseDataUtil.error("文件上传异常，文件过大");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统全局", description = "服务器异常")
    @ExceptionHandler(BaseRuntimeException.class)
    public Map<String, Object> baseRuntimeException(BaseRuntimeException e) {
        return ResponseDataUtil.error(e.getMessage());
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统全局", description = "服务器运行异常")
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> runtimeException(RuntimeException e) {
        if (e instanceof NullPointerException) {
            return ResponseDataUtil.error("空指针异常");
        }
        return ResponseDataUtil.error(e.getMessage());
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统全局", description = "服务器异常")
    @ExceptionHandler(Exception.class)
    public Map<String, Object> exception(Exception e) {
        return ResponseDataUtil.error(e.getMessage());
    }

}
