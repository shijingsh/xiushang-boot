package javax.validation.constraints;

import com.xiushang.validation.validator.AmountConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 金钱
 **/
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { AmountConstraintValidator.class })
public @interface Amount {
    /**
     * 是否必填 默认是必填的
     * @return
     */
    boolean required() default true;

    String message() default "{Amount.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return 匹配的正则表达式
     */
    String regexp() default "";

    /**
     * @return 此数字接受的最大整数位数
     */
    int integer() default -1;

    /**
     * @return 此数字接受的最大小数位数
     */
    int fraction() default -1;

    /**
     * 最小值
     * @return
     */
    double min() default 0.0d;

    /**
     * 最大值
     * @return
     */
    double max() default Double.MAX_VALUE;

    /**
     * 是否可以为0
     * @return
     */
    boolean canZero() default true;

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Amount[] value();
    }
}
