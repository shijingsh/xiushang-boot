package javax.validation.constraints;

import com.xiushang.validation.validator.YesOrNoConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否
 **/
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Repeatable(YesOrNo.List.class)
@Constraint(validatedBy = { YesOrNoConstraintValidator.class })
public @interface YesOrNo {

    /**
     * 是否必填 默认是必填的
     * @return
     */
    boolean required() default true;

    String message() default "{YesOrNo.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String yes() default "1";

    String no() default "0";

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        YesOrNo[] value();
    }
}
