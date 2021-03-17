package cn.sgst.mywebplus.core.validation.constraints;

import cn.sgst.mywebplus.core.validation.constraintvalidators.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验手机号格式
 * whitespace, empty ("") or null  is valid
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/4/3 14:31
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Phone.List.class)
@Documented
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {

    String message() default "{cn.sgst.mywebplus.core.validation.constraints.Phone.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link Phone} constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Phone[] value();
    }
}
