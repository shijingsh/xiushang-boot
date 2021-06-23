package com.xiushang.common.exception.handler;

import com.aliyuncs.http.HttpRequest;
import com.xiushang.common.exception.ServiceException;
import com.xiushang.constant.ConstantKey;
import com.xiushang.framework.log.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * GlobalExceptionHandler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public CommonResult handleException(Throwable e, HttpServletRequest request){
        // 打印堆栈信息
        log.info("==============================发生错误==============================");
        log.info(request.getContextPath());
        log.info(request.getQueryString());
        log.info(request.getHeader(ConstantKey.ACCESS_TOKEN));
        e.printStackTrace();
        return CommonResult.error(e.getMessage());
    }
    /**
     * 系统繁忙，请稍候再试"
     */
    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e, HttpServletRequest request) {
        log.info("==============================发生错误==============================");
        log.info(request.getContextPath());
        log.info(request.getQueryString());
        log.info(request.getHeader(ConstantKey.ACCESS_TOKEN));
        e.printStackTrace();
        return CommonResult.error(e.getMessage());
    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(value = ServiceException.class)
    CommonResult ServiceExceptionHandler(ServiceException e) {
        //log.error("Exception,exception:{}", e, e);
        e.printStackTrace();
        return CommonResult.error( e.getMessage());
    }

    /**
     * 没有权限 返回403视图
     */
    /*@ExceptionHandler(value = AuthorizationException.class)
    public CommonResult errorPermission(AuthorizationException e) {
        log.error("Exception,exception:{}", e, e);
         e.printStackTrace();
        return CommonResult.error( e.getMessage());

    }*/

    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public CommonResult entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        //log.error(ThrowableUtil.getStackTrace(e));
        e.printStackTrace();
        return CommonResult.error( e.getMessage());
    }

    /**
     * 处理validation 框架异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    CommonResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        e.printStackTrace();
        return CommonResult.error( e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public CommonResult badCredentialsException(BadCredentialsException e){
        // 打印堆栈信息
        //String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        e.printStackTrace();
        return CommonResult.error( e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult handleAuthorizationException(AccessDeniedException e)
    {
        //log.error(e.getMessage());
        e.printStackTrace();
        return CommonResult.error( e.getMessage());
    }
}
