package cn.sgst.mywebplus.core.validation;

import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;

/**
 * 反射获取私有属性violation的工具类
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/10/19 9:56
 */
@SuppressWarnings("all")
public class ValidationViolationUtils {


    /**
     * 反射获取{@link org.springframework.validation.beanvalidation.SpringValidatorAdapter $ViolationFieldError } 私有属性violation的值
     * @return {@link ConstraintViolation}
     */

    public static ConstraintViolation getViolationFieldValue(@NotNull FieldError error) {
        Assert.notNull(error,"fieldError must not be null");
        Class clazz = null;
        try {
            clazz = Class.forName("org.springframework.validation.beanvalidation.SpringValidatorAdapter$ViolationFieldError");
            Field field = clazz.getDeclaredField("violation");
            field.setAccessible(true);
            return  (ConstraintViolation) field.get(error);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反射获取{@link org.springframework.validation.beanvalidation.SpringValidatorAdapter $ViolationFieldError } 私有属性adapter的值
     * @return ConstraintViolation
     */
    public static SpringValidatorAdapter getAdapterFieldValue(FieldError error) {
        Assert.notNull(error,"fieldError must not be null");
        Class clazz = null;
        try {
            clazz = Class.forName("org.springframework.validation.beanvalidation.SpringValidatorAdapter$ViolationFieldError");
            Field field = clazz.getDeclaredField("adapter");
            field.setAccessible(true);
            return  (SpringValidatorAdapter) field.get(error);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
