package cn.sgst.mywebplus.core.validation;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Field;

/**
 * 通过反射获取ObjectError私有类的ConstraintViolation属性值
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/19 23:09
 * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter
 */
public class SpringViolationErrorReflectFactory {

    /**
     * 反射获取{@link org.springframework.validation.beanvalidation.SpringValidatorAdapter $ViolationFieldError } 私有属性violation的值
     *
     * @return {@link ConstraintViolation}
     */
    public static ConstraintViolation<?> getViolation(@NonNull ObjectError objectError) {
        Assert.notNull(objectError, "objectError must not be null");
        try {
            Field field = objectError.getClass().getDeclaredField("violation");
            field.setAccessible(true);
            return (ConstraintViolation<?>) field.get(objectError);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
