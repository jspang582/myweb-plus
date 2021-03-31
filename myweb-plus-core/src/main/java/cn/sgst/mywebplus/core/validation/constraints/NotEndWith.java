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
 * 不能以什么结尾
 * <p>whitespace, empty ("") or null  is valid</p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/30 16:39
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@Repeatable(NotEndWith.List.class)
@ReportAsSingleViolation
@Empty
@ConstraintComposition(CompositionType.OR)
@NotEndWith.EndWithInvert(suffix = "")
public @interface NotEndWith {

    String message() default "{cn.sgst.mywebplus.core.validation.constraints.NotEndWith.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 后缀
     */
    @OverridesAttribute(constraint = EndWithInvert.class, name = "suffix") String suffix();

    // 同时指定多个时使用
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        NotEndWith[] value();
    }


    /**
     * 反转
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Constraint(validatedBy = {})
    @ConstraintComposition(CompositionType.ALL_FALSE)
    @EndWith(suffix = "")
    @interface EndWithInvert {
        String message() default "";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        @OverridesAttribute(constraint = EndWith.class, name = "suffix") String suffix();
    }

}
