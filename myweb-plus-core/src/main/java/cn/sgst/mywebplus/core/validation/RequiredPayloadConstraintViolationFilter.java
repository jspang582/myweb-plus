package cn.sgst.mywebplus.core.validation;

import cn.sgst.mywebplus.core.validation.group.Required;

/**
 * 基于Required和Payload的违反约束过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/24 10:13
 */
public class RequiredPayloadConstraintViolationFilter extends AbstractPayloadConstraintViolationFilter {


    @Override
    public Class<?> acceptClass() {
        return Required.class;
    }
}
