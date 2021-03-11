package cn.sgst.mywebplus.core.util;

import cn.sgst.mywebplus.core.validation.BeanValidationResult;
import cn.sgst.mywebplus.core.validation.ValidationUtils;

import javax.validation.ValidationException;
import javax.validation.groups.Default;

/**
 * 断言
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/11 13:05
 */
public class Assert {

    /**
     * Bean校验合格
     * @param bean 需要校验的bean
     */
    public static void isValidBean(Object bean) {
        isValidBean(bean,Default.class);
    }

    /**
     * Bean校验合格
     * @param bean 需要校验的bean
     * @param groups 校验组
     */
    public static void isValidBean(Object bean,Class<?>... groups) {
        BeanValidationResult validationResult = ValidationUtils.warpValidate(bean, groups);
        if(!validationResult.isSuccess()) {
            throw new ValidationException(validationResult.getErrorMessages().toString());
        }
    }
}
