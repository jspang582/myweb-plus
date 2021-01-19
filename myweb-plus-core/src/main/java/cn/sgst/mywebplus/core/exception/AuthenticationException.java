package cn.sgst.mywebplus.core.exception;

/**
 * 未认证异常
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 17:27
 */
public class AuthenticationException extends ServiceException {

    private static final int ERROR_CODE = 403;

    public AuthenticationException() {
        super(ERROR_CODE, "未授权");
    }

    public AuthenticationException(String errorMessage) {
        super(ERROR_CODE,errorMessage);
    }

}
