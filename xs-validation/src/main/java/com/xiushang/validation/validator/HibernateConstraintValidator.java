package com.xiushang.validation.validator;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HibernateConstraintValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    private static Logger logger = LoggerFactory.getLogger(HibernateConstraintValidator.class);

    /**
     * 校验的值是否为空
     */
    private static ThreadLocal<Boolean> emptyResultThreadLocal = ThreadLocal.withInitial(() -> Boolean.FALSE);
    /**
     * 上下文
     */
    private static ThreadLocal<HibernateConstraintValidatorContext> contextThreadLocal = new ThreadLocal<>();

    /**
     * 注解对象
     */
    protected A annotation;

    @Override
    public void initialize(A constraintAnnotation) {
        this.annotation = constraintAnnotation;
        doInitialize();
    }

    /**
     * 初始化方法
     */
    protected void doInitialize() {

    }

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {

        boolean isValid = false;
        try {
            HibernateConstraintValidatorContext hibernateContext = (HibernateConstraintValidatorContext) context;
            isValid = doValid(value, hibernateContext);

            // 校验不通过
            if (!isValid) {
                String validBasePath = getValidBasePath(hibernateContext);
                logger.debug("{} is {} , parameter verification does not pass.", validBasePath, value);

                hibernateContext.addMessageParameter("value", value);
                hibernateContext.addMessageParameter("fieldValue", value);

                // 由于为值为空时导致的校验不通过
                if (getEmptyResult()) {
                    context.disableDefaultConstraintViolation();
                    String message = String.format("{%s.empty.message}", getAnnotationClassName());
                    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                }
            }
        } finally {
            removeEmptyResult();
        }
        return isValid;
    }

    /**
     * 执行校验
     * @param value
     * @return
     */
    protected abstract boolean isValid(T value);

    /**
     * 执行校验
     * @param value
     * @param context
     * @return
     */
    private boolean doValid(T value, HibernateConstraintValidatorContext context) {
        try {
            contextThreadLocal.set(context);
            return isValid(value);
        } finally {
            contextThreadLocal.remove();
        }
    }

    /**
     * 获取上下文
     * @return
     */
    protected HibernateConstraintValidatorContext getContext(){
        return contextThreadLocal.get();
    }

    /**
     * 判空逻辑
     * @param obj
     * @return
     */
    protected boolean isEmpty(Object obj) {
        return StringUtils.isEmpty(obj);
    }

    /**
     * 设置空结果
     * @param flag
     */
    protected void setEmpty(boolean flag) {
        emptyResultThreadLocal.set(Boolean.valueOf(flag));
    }

    /**
     * 移除空结果
     */
    protected void removeEmptyResult() {
        emptyResultThreadLocal.remove();
    }

    /**
     * 获取空结果
     * @return
     */
    protected boolean getEmptyResult() {
        return emptyResultThreadLocal.get();
    }

    /**
     * 获取注解类名
     * @return
     */
    private String getAnnotationClassName() {
        Type typeArgument = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return typeArgument.getTypeName();
    }

    protected String getValidBasePath(HibernateConstraintValidatorContext context) {
        String basePath = null;
        try {
            Field basePathField = context.getClass().getDeclaredField("basePath");
            basePathField.setAccessible(true);
            basePath = String.valueOf(basePathField.get(context));
            context.addMessageParameter("fieldName", basePath);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return basePath;
    }
}
