package javax.validation.constraints;

import com.xiushang.validation.validator.EnumCheckConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举检查
 **/
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Repeatable(EnumCheck.List.class)
@Constraint(validatedBy = EnumCheckConstraintValidator.class)
public @interface EnumCheck {
    /**
     * 是否必填 默认是必填的
     *
     * @return
     */
    boolean required() default true;

    /**
     * 验证失败的消息
     *
     * @return
     */
    String message() default "{EnumCheck.message}";

    /**
     * 分组的内容
     *
     * @return
     */
    Class<?>[] groups() default {};

    /**
     * 错误验证的级别
     *
     * @return
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 枚举的Class
     *
     * @return
     */
    Class<? extends Enum<?>> enumClass() default EnumCheckConstraintValidator.Empty.class ;

    /**
     * 枚举校验的字段 （默认形式）
     * @return
     */
    String enumField() default "code";

    /**
     * 枚举校验的方法
     * @return
     */
    String enumMethod() default "";

    /**
     * 枚举
     *
     * @return
     */
    String[] enumCode() default {};

    /**
     * 枚举,逗号分隔
     *
     * @return
     */
    String enumCodeString() default "";

    /**
     * 配置校验
     *
     * @return
     */
    String configName() default "";

    /**
     * 配置校验组
     *
     * @return
     */
    String configGroup() default "enums";

    /**
     * 校验结果是否反转
     *
     * @return
     */
    boolean reverse() default false;

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EnumCheck[] value();
    }
}
