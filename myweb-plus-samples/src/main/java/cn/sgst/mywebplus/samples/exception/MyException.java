package cn.sgst.mywebplus.samples.exception;

import cn.sgst.mywebplus.core.exception.ServiceException;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 17:10
 */
public class MyException extends ServiceException {
    public MyException(Integer code, String errorMessage) {
        super(code, errorMessage);
    }
}
