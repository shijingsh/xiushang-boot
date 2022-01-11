package javax.validation.constraints;

import com.xiushang.validation.validator.DataIdConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 数据主键ID
 * @author: kancy
 * @date: 2019/12/11 10:40
 **/
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { DataIdConstraintValidator.class })
public @interface DataId {
    /**
     * 是否必填 默认是必填的
     * @return
     */
    boolean required() default true;

    String message() default "{DataId.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long min() default 1;

    long max() default Long.MAX_VALUE;

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        DataId[] value();
    }
}
