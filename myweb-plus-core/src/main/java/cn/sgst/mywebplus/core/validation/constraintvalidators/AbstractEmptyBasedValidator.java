package cn.sgst.mywebplus.core.validation.constraintvalidators;

import cn.sgst.mywebplus.core.util.ValidateUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * 基于空结果返回true
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/6 9:50
 */
public abstract class AbstractEmptyBasedValidator<A extends Annotation,T> implements ConstraintValidator<A,T> {
    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
       if(ValidateUtil.isEmpty(value)) {
           return true;
       }
       return isValid(value);
    }

    /**
     * 子类实现校验
     * @param value 待校验的值
     * @return true or false
     */
    protected abstract boolean isValid(T value);
}
