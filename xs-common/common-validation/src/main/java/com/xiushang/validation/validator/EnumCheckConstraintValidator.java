package com.xiushang.validation.validator;

import com.xiushang.validation.config.ApplicationContextHolder;
import com.xiushang.validation.properties.EnumProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ValidationException;
import javax.validation.constraints.EnumCheck;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


public class EnumCheckConstraintValidator extends CheckEmptyConstraintValidator<EnumCheck, Object> {
    private static Logger log = LoggerFactory.getLogger(EnumCheckConstraintValidator.class);

    /**
     * 缓存的方式，校验是否存在这个enumCode
     */
    private static Map<String, Boolean> enumCodeExistCache = new HashMap(64);

    /**
     * 缓存enumMethod
     */
    private static Map<String, Method> enumMethodCache = new HashMap();

    /**
     * 验证的值不为空时，验证结果
     * @param value
     * @return
     */
    @Override
    protected boolean check(Object value) {

        if(annotation.enumClass() != Empty.class){
            return doValidByEnumClass(value);
        }

        if(annotation.enumCode().length > 0 || annotation.enumCodeString().length() > 0){
            return doValidByEnumCode(value);
        }

        return doValidBySpringConfig(value);
    }

    /**
     * 验证的值为空时，返回结果
     *
     * @return
     */
    @Override
    protected boolean requestEmptyResult() {
        return !annotation.required();
    }

    /**
     * 通过Spring枚举配置校验
     * @param value
     * @return
     */
    private boolean doValidBySpringConfig(Object value) {
        Binder binder = Binder.get(ApplicationContextHolder.getContext().getEnvironment());
        // 默认形式
        String propertyKey = String.format("%s.%s", getConfigGroup(), getConfigName()).toLowerCase();
        BindResult<EnumProperties> bindResult = binder.bind(propertyKey, EnumProperties.class);
        Assert.isTrue(bindResult.isBound(), String.format("EnumCheck missing configuration: not found (%s)", propertyKey));
        EnumProperties enumMeta = bindResult.get();
        // 不启用校验，直接返回验证通过
        if (!enumMeta.isEnabled()){
            return true;
        }
        Assert.notEmpty(enumMeta.getItems(), "EnumCheck missing configuration：enum items is empty");
        List<String> list = new ArrayList();
        enumMeta.getItems().stream().forEach(item -> {
            Object itemValue = item.getValue();
            if (Objects.nonNull(itemValue)){
                list.add(String.valueOf(itemValue));
            }
        });
        boolean isValid = list.contains(String.valueOf(value));
        return annotation.reverse()? !isValid : isValid;
    }


    /**
     * 枚举代码校验
     * @param value
     * @return
     */
    private boolean doValidByEnumCode(Object value) {
        List<String> enumCodes = Arrays.asList(annotation.enumCode());
        if (enumCodes.isEmpty()){
            enumCodes.addAll(Arrays.asList(StringUtils.split(annotation.enumCodeString(), ",")));
        }
        boolean isValid = enumCodes.contains(String.valueOf(value));
        return annotation.reverse()? !isValid : isValid;
    }

    /**
     * 通过枚举类校验
     * @param value
     * @return
     */
    private boolean doValidByEnumClass(Object value) {
        if (StringUtils.isEmpty(annotation.enumMethod())){
            Class<? extends Enum<?>> enumClass = annotation.enumClass();
            boolean isValid = existEnumCode(enumClass, String.valueOf(value), annotation.enumField());
            return annotation.reverse()? !isValid : isValid;
        }
        return doValidByEnumMethod(value);
    }

    /**
     * 调用枚举的方法
     * @param value
     * @return
     */
    private boolean doValidByEnumMethod(Object value) {
        Class<? extends Enum<?>> enumClass = annotation.enumClass();
        boolean isValid = false;
        try {
            Method method = getEnumMethod();
            Class<?> parameterType = method.getParameterTypes()[0];
            isValid = (boolean) method.invoke(enumClass, castObjectValue(value, parameterType));
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("EnumCheck enumClass not found "+ annotation.enumMethod() +" method.");
        } catch (Exception e) {
            throw new ValidationException(e);
        }
        return annotation.reverse()? !isValid : isValid;
    }

    /**
     * 获取枚举校验方法对象
     * @return
     * @throws NoSuchMethodException
     */
    private Method getEnumMethod() throws NoSuchMethodException {
        String cacheKey = String.format("%s-%s", annotation.enumClass().getName(), annotation.enumMethod());
        Method method = enumMethodCache.get(annotation.enumMethod());
        if (method == null){
            Method[] declaredMethods = annotation.enumClass().getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if(annotation.enumMethod().equals(declaredMethod.getName()) && declaredMethod.getParameterCount() == 1){
                    method = declaredMethod;
                    ReflectionUtils.makeAccessible(method);
                    enumMethodCache.putIfAbsent(cacheKey, method);
                    break;
                }
            }
        }
        if(method == null){
            throw new NoSuchMethodException();
        }
        return method;
    }

    /**
     * 判断是否存在枚举代码
     * @param clazz
     * @param value
     * @param key
     * @return
     */
    private boolean existEnumCode(Class<?> clazz, String value, String key) {
        String cacheKey = String.format("%s-%s-%s",clazz.getName(), key, value);
        if (!enumCodeExistCache.containsKey(cacheKey)){
            try {
                // 忽略多线程因素
                for (Object result : clazz.getEnumConstants()) {
                    Field codeField = result.getClass().getDeclaredField(key);
                    ReflectionUtils.makeAccessible(codeField);
                    if (value.equals(String.valueOf(ReflectionUtils.getField(codeField, result)))) {
                        enumCodeExistCache.putIfAbsent(cacheKey, true);
                        break;
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return enumCodeExistCache.containsKey(cacheKey);
    }

    /**
     * 获取默认配置前缀
     * @return
     */
    private String getConfigGroup() {
        return ApplicationContextHolder.getContext().getEnvironment()
                .getProperty("emum-check.config-group", annotation.configGroup());
    }

    /**
     * 获取Spring枚举配置的枚举名称
     * @return
     */
    private String getConfigName() {
        String configName = annotation.configName();
        if(StringUtils.isEmpty(configName)){
            configName = getValidBasePath(getContext());
            if(StringUtils.isEmpty(configName)){
                throw new IllegalArgumentException("EnumCheck missing configuration.");
            }
        }
        return configName;
    }

    /**
     * 类型转换
     * @param value
     * @param type
     * @return
     */
    private Object castObjectValue(Object value, Class<?> type) {
        if(type.isInstance(value)){
            return type.cast(value);
        }
        if(type == String.class){
            return String.valueOf(value).trim();
        }
        if(type == Integer.class || type == int.class){
            return Integer.valueOf(String.valueOf(value).trim());
        }
        if(type == Long.class || type == long.class){
            return Long.valueOf(String.valueOf(value).trim());
        }
        if(type == Double.class || type == double.class){
            return Double.valueOf(String.valueOf(value).trim());
        }
        if(type == Float.class || type == float.class){
            return Float.valueOf(String.valueOf(value).trim());
        }
        return value;
    }

    /**
     * Empty Class Flg
     */
    public enum Empty{}
}
