package cn.sgst.mywebplus.core.validation.constraints;


import cn.sgst.mywebplus.core.validation.constraintvalidators.StartWithValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 校验必须以什么为开头
 * <p>whitespace, empty ("") or null  is valid</p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/30 16:08
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(StartWith.List.class)
@Documented
@Constraint(validatedBy = {StartWithValidator.class})
public @interface StartWith {


    String message() default "{cn.sgst.mywebplus.core.validation.constraints.StartWith.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 前缀
     */
    String prefix();

    // 同时指定多个时使用
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        StartWith[] value();
    }
}
