package cn.sgst.mywebplus.core.exception;

import cn.sgst.mywebplus.core.enums.IEnum;

/**
 * 定义异常规范
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 17:14
 */
public interface IExceptionEnum extends IEnum<Integer,String> {

    /**
     * 获取异常的状态码
     */
    Integer getCode();

    /**
     * 获取异常的提示信息
     */
    String getMessage();


    @Override
    default Integer getValue() {
        return getCode();
    }

    @Override
    default String getText() {
        return getMessage();
    }
}
