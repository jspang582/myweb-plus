package cn.sgst.mywebplus.core.validation.constraints;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * only whitespace, empty ("") or null  is valid
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/17 17:25
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Empty.List.class)
@Documented
@Constraint(validatedBy = { })
@ReportAsSingleViolation
@NotBlank
@ConstraintComposition(CompositionType.ALL_FALSE)
public @interface Empty {

    String message() default "{cn.sgst.mywebplus.core.validation.constraints.Empty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 同时指定多个时使用
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        Empty[] value();
    }
}
