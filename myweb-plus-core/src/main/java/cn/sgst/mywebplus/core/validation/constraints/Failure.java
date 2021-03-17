package cn.sgst.mywebplus.core.validation.constraints;


import cn.sgst.mywebplus.core.validation.constraintvalidators.FailureValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 校验失败
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/10/19 14:00
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Failure.List.class)
@Constraint(validatedBy = {FailureValidator.class})
public @interface Failure {


    String message() default "{cn.sgst.mywebplus.core.validation.constraints.Failure.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    // 同时指定多个时使用
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        Failure[] value();
    }
}
