package cn.sgst.mywebplus.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常的封装
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 17:12
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {

    /**
     * 错误状态码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 带状态码和错误信息的构造函数
     *
     * @param code         错误状态码
     * @param errorMessage 错误信息
     */
    public ServiceException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    /**
     * 带状态码、错误信息和错误堆栈的构造函数
     *
     * @param code         错误状态码
     * @param errorMessage 错误信息
     * @param e            错误堆栈
     */
    public ServiceException(Integer code, String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.code = code;
        this.errorMessage = errorMessage;
    }


    /**
     * 带错误枚举的构造函数
     *
     * @param exception 错误枚举
     */
    public ServiceException(IExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    /**
     * 带错误枚举和错误堆栈的构造函数
     *
     * @param exception 错误枚举
     * @param e         错误堆栈
     */
    public ServiceException(IExceptionEnum exception, Throwable e) {
        super(exception.getMessage(), e);
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

}
