package cn.sgst.mywebplus.core.validation.constraints;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 不能以什么开头
 * <p>whitespace, empty ("") or null  is valid</p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/30 17:30
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@Repeatable(NotStartWith.List.class)
@ReportAsSingleViolation
@Empty
@ConstraintComposition(CompositionType.OR)
@NotStartWith.StartWithInvert(prefix = "")
public @interface NotStartWith {

    String message() default "{cn.sgst.mywebplus.core.validation.constraints.NotStartWith.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 后缀
     */
    @OverridesAttribute(constraint = StartWithInvert.class, name = "prefix") String prefix();

    // 同时指定多个时使用
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        NotStartWith[] value();
    }


    /**
     * 反转
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = {})
    @ConstraintComposition(CompositionType.ALL_FALSE)
    @StartWith(prefix = "")
    @interface StartWithInvert {
        String message() default "";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        @OverridesAttribute(constraint = StartWith.class, name = "prefix") String prefix();
    }
}
