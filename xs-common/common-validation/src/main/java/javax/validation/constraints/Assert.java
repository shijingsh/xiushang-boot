package javax.validation.constraints;

import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Repeatable(Assert.List.class)
public @interface Assert {

    /**
     * SPEL表达式
     * @return
     */
    String value();

    boolean result() default true;

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({FIELD, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Assert[] value();
    }
}
