package cn.sgst.mywebplus.core.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Digits;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 只能是整数
 * <p>null is Valid</p>
 * <p>不支持字符串类型</p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/8/18 15:15
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Integer.List.class)
@Documented
@Constraint(validatedBy = { })
@ReportAsSingleViolation
@Digits(integer = java.lang.Integer.MAX_VALUE,fraction = 0)
public @interface Integer {

    String message() default "{cn.sgst.mywebplus.core.validation.constraints.Integer.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    // 同时指定多个时使用
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        Integer[] value();
    }
}
