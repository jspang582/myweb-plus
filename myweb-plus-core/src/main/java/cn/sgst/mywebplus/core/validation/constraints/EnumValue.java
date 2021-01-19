
package cn.sgst.mywebplus.core.validation.constraints;

import cn.sgst.mywebplus.core.enums.IEnum;
import cn.sgst.mywebplus.core.validation.constraintvalidators.EnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 校验枚举值
 * whitespace, empty ("") or null  is valid
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/22 11:35
 */

@Target({FIELD,PARAMETER,ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(EnumValue.List.class)
@Constraint(validatedBy = {EnumValueValidator.class})
public @interface EnumValue {

    String message() default "非法的枚举值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     *需要校验的枚举Class
     */
    Class<? extends IEnum> target();


    /**
     * 是否允许多值
     */
    boolean multiAllowed() default false;

    /**
     * 分隔符
     */
    String separator() default ",";

    // 同时指定多个时使用
    @Target({FIELD,PARAMETER,ANNOTATION_TYPE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        EnumValue[] value();
    }
}

