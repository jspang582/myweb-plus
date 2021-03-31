package cn.sgst.mywebplus.core.validation.constraints;


import cn.sgst.mywebplus.core.validation.constraintvalidators.EndWithValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 必须以什么结尾
 * <p>whitespace, empty ("") or null  is valid</p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/30 16:08
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EndWith.List.class)
@Documented
@Constraint(validatedBy = {EndWithValidator.class})
public @interface EndWith {


    String message() default "{cn.sgst.mywebplus.core.validation.constraints.EndWith.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 后缀
     */
    String suffix();

    // 同时指定多个时使用
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        EndWith[] value();
    }
}
