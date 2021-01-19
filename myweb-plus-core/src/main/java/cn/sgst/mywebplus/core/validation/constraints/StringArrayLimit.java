package cn.sgst.mywebplus.core.validation.constraints;

import cn.sgst.mywebplus.core.validation.constraintvalidators.StringArrayLimitValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 校验字符串多个值元素个数的注解
 * {@code null} elements are considered valid.
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/10/18 9:45
 */
@Target({FIELD,PARAMETER,ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(StringArrayLimit.List.class)
@Constraint(validatedBy = {StringArrayLimitValidator.class})
public @interface StringArrayLimit {
    String message() default "元素个数必须在{min}到{max}之间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 是否忽略空值
     */
    boolean ignoreEmpty() default false;

    /**
     * 元素个数最小值
     */
    int min() default 0;


    /**
     * 元素个数最大值
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 元素分割符
     */
    String separator() default ",";


    // 同时指定多个时使用
    @Target({FIELD,PARAMETER,ANNOTATION_TYPE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        StringArrayLimit[] value();
    }
}
