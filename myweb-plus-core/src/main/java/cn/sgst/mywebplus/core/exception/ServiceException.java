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

    /* 错误状态码 */
    private Integer code;

    /* 错误信息 */
    private String errorMessage;

    public ServiceException(Integer code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public ServiceException(IExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

}
