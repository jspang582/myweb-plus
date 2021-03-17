package cn.sgst.mywebplus.core.validation.constraints;


import cn.sgst.mywebplus.core.enums.IEnum;
import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 校验多个枚举值
 * whitespace, empty ("") or null  is valid
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/17 14:35
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(EnumValues.List.class)
@ReportAsSingleViolation
@EnumValue
@Constraint(validatedBy = {})
public @interface EnumValues {

    String message() default "{cn.sgst.mywebplus.core.validation.constraints.EnumValues.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    /**
     *需要校验的枚举Class
     */
    @OverridesAttribute(constraint = EnumValue.class, name = "target") Class<? extends IEnum> target();


    /**
     * 是否允许多值
     */
    @OverridesAttribute(constraint = EnumValue.class, name = "multiAllowed") boolean multiAllowed() default true;

    /**
     * 分隔符
     */
    @OverridesAttribute(constraint = EnumValue.class, name = "separator") String separator() default ",";

    // 同时指定多个时使用
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        EnumValues[] value();
    }
}
