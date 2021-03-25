package cn.sgst.mywebplus.core.validation;

import cn.sgst.mywebplus.core.validation.group.Required;

/**
 * 基于Required和Group组的违反约束过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/24 10:14
 */
public class RequiredGroupConstraintViolationFilter extends AbstractGroupConstraintViolationFilter {


    @Override
    public Class<?> acceptClass() {
        return Required.class;
    }
}
