package com.xiushang.config;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 自定义版本接口注解
 * @author 765199214
 *
 */
@Target({
	ElementType.METHOD, //接口用于方法上
	ElementType.TYPE	//接口用于类上
})
@Retention(RetentionPolicy.RUNTIME) //只在运行时才有效
@Documented //标识这是个注解并应该被 javadoc工具记录
@Mapping //标识映射
public @interface ApiVersion {
	/**
	 * 标识版本号
	 * @return
	 */
	int value() default 1;
}
