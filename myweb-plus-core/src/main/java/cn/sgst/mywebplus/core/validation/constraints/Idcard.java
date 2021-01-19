package cn.sgst.mywebplus.core.validation.constraints;

import cn.sgst.mywebplus.core.validation.constraintvalidators.IdCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验身份证号码格式
 * 支持15位、18位、港澳台身份证
 * whitespace, empty ("") or null  is valid
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/4/3 14:31
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(Idcard.List.class)
@Documented
@Constraint(validatedBy = IdCardValidator.class)
public @interface Idcard {

    String message() default "无效的身份证号码";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    IdcardType[] types() default {IdcardType.ID15, IdcardType.ID18};


    /**
     * Defines several {@link Idcard} constraints on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Idcard[] value();
    }


    /**
     * 身份证号类型
     */
     enum  IdcardType {
        /**
         * 15位身份证
         */
        ID15,

        /**
         * 18位身份证
         */
        ID18,
        /**
         * 10位身份证(港澳台)
         */
        ID10,
    }
}
