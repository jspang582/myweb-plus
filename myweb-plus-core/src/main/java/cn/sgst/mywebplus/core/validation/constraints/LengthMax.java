package cn.sgst.mywebplus.core.validation.constraints;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.Length;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 字符串长度上限
 * <p>whitespace, empty ("") or null  is valid</p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/7/5 18:48
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(LengthMax.List.class)
@Documented
@Constraint(validatedBy = { })
@ReportAsSingleViolation
@ConstraintComposition(CompositionType.OR)
@Empty
@Length
public @interface LengthMax {

    @OverridesAttribute(constraint = Length.class, name = "max")
    int value() default Integer.MAX_VALUE;


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "{cn.sgst.mywebplus.core.validation.constraints.LengthMax.message}";



    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        LengthMax[] value();
    }
}
