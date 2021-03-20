package cn.sgst.mywebplus.core.validation;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;

/**
 * 通过反射获取ObjectError私有类的ConstraintViolation属性值
 * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/19 23:09
 */
public class SpringViolationErrorReflectFactory {

    /**
     * 反射获取{@link org.springframework.validation.beanvalidation.SpringValidatorAdapter $ViolationFieldError } 私有属性violation的值
     * @return {@link ConstraintViolation}
     */
    public static ConstraintViolation getViolation(@NonNull ObjectError objectError) {
        Assert.notNull(objectError,"objectError must not be null");
        Class clazz;
        String className;
        if(objectError instanceof FieldError) {
            className = "org.springframework.validation.beanvalidation.SpringValidatorAdapter$ViolationFieldError";
        }else {
            className = "org.springframework.validation.beanvalidation.SpringValidatorAdapter$ViolationObjectError";
        }
        try {
            clazz = Class.forName(className);
            Field field = clazz.getDeclaredField("violation");
            field.setAccessible(true);
            return  (ConstraintViolation) field.get(objectError);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
