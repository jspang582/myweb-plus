package cn.sgst.mywebplus.core.exception;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 17:30
 */
public class ForbiddenException extends ServiceException {

    private static final int ERROR_CODE = 401;

    public ForbiddenException() {
        super(ERROR_CODE, "禁止访问");
    }

    public ForbiddenException(String errorMessage) {
        super(ERROR_CODE, errorMessage);
    }

}
