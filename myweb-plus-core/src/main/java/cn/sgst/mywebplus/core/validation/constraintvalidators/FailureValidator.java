package cn.sgst.mywebplus.core.validation.constraintvalidators;

import cn.sgst.mywebplus.core.validation.constraints.Failure;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验不通过
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/10/19 14:01
 */
public class FailureValidator implements ConstraintValidator<Failure,Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }
}
