package cn.sgst.mywebplus.core.validation.constraints;

import cn.sgst.mywebplus.core.validation.constraintvalidators.StringDateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 校验字符串时间格式是否正确
 * whitespace, empty ("") or null  is valid
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/31 11:00
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(StringDateTime.List.class)
@Constraint(validatedBy = {StringDateTimeValidator.class})
public @interface StringDateTime {
    String message() default "{cn.sgst.mywebplus.core.validation.constraints.StringDateTime}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 需要校验的时间格式
     */
    String pattern() default "yyyy-MM-dd HH:mm:ss";


    /**
     * 是否是严格模式,严格模式下长度必须相等
     */
    boolean strict() default true;

    // 同时指定多个时使用
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        StringDateTime[] value();
    }
}
