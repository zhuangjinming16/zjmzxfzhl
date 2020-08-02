package com.zjmzxfzhl.common.core.validator.constraints;

import com.zjmzxfzhl.common.core.validator.LengthForUtf8Validator;
import com.zjmzxfzhl.common.core.validator.constraints.LengthForUtf8.List;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@Documented
@Constraint(validatedBy = {LengthForUtf8Validator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface LengthForUtf8 {
    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "{org.hibernate.validator.constraints.Length.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        LengthForUtf8[] value();
    }
}
