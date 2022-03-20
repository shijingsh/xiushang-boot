package com.xiushang.common.exception.handler;

import com.xiushang.common.exception.InternalCodeAuthenticationServiceException;
import com.xiushang.common.exception.ServiceException;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.log.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
        this.printLog(request);
        log.error("Throwable,exception:{}", e, e);

        return CommonResult.error(e.getMessage());
    }
    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(RuntimeException.class)
    public CommonResult handleException(RuntimeException e, HttpServletRequest request){
        // 打印堆栈信息
        this.printLog(request);
        log.error("RuntimeException,exception:{}", e, e);

        return CommonResult.error(e.getMessage());
    }

    /**
     * 自定义全局异常处理
     */
    @ExceptionHandler(value = ServiceException.class)
    CommonResult ServiceExceptionHandler(ServiceException e) {
        log.error("ServiceException,exception:{}", e, e);

        return CommonResult.error( e.getMessage());
    }


    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public CommonResult entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        log.error("EntityNotFoundException,exception:{}", e, e);
        return CommonResult.error( e.getMessage());
    }


    @ExceptionHandler(BadCredentialsException.class)
    public CommonResult badCredentialsException(BadCredentialsException e){
        // 打印堆栈信息
        //String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error("BadCredentialsException,exception:{}", e, e);
        return CommonResult.error( e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult handleAuthorizationException(AccessDeniedException e)
    {
        log.error("AccessDeniedException,exception:{}", e, e);
        return CommonResult.error( e.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    public CommonResult bindExceptionHandle(BindException exception) {
        log.error("BindException,exception:{}", exception, exception);
        BindingResult result = exception.getBindingResult();

        return getValidMessage(result);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException,exception:{}", exception, exception);
        BindingResult result = exception.getBindingResult();

        return getValidMessage(result);
    }


    @ExceptionHandler(value = InternalCodeAuthenticationServiceException.class)
    public CommonResult methodArgumentNotValidExceptionHandle(InternalCodeAuthenticationServiceException exception) {
        log.error("InternalCodeAuthenticationServiceException,exception:{}", exception, exception);

        return CommonResult.error(exception.getErrorCode(), exception.getMessage());
    }

    private CommonResult getValidMessage(BindingResult result){
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(error -> {
            log.error("field: " + error.getField() + ", msg:" + error.getDefaultMessage());
            String e = error.getDefaultMessage();
            if(e.endsWith("!")){
                errorMsg.append(error.getDefaultMessage());
            }else {
                errorMsg.append(error.getDefaultMessage()).append("!");
            }
        });

        return CommonResult.error(errorMsg.toString());
    }

    private void printLog(HttpServletRequest request){
        log.info("==============================发生错误==============================");
        log.info("url:"+request.getRequestURL());
        log.info("queryStr:"+request.getQueryString());
        log.info(SecurityConstants.AUTH_HEADER_STRING+":"+request.getHeader(SecurityConstants.AUTH_HEADER_STRING));
    }
}
