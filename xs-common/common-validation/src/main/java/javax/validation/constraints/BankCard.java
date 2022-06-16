package javax.validation.constraints;

import com.xiushang.validation.validator.BankCardConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * BankCard
 * <p>
 **/
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Repeatable(BankCard.List.class)
@Constraint(validatedBy = { BankCardConstraintValidator.class })
public @interface BankCard {

    /**
     * 是否必填 默认是必填的
     * @return
     */
    boolean required() default true;

    String message() default "{BankCard.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return the regular expression to match
     */
    String regexp() default "^([1-9]{1})(\\d{15}|\\d{18})$";

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        BankCard[] value();
    }
}

