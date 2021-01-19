package cn.sgst.mywebplus.core.exception;

/**
 * 自定义404异常
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 17:22
 */
public class ResourceNotFoundException extends ServiceException {
    private static final int ERROR_CODE = 404;

    public ResourceNotFoundException() {
        super(ERROR_CODE, "请求资源不存在");
    }

    public ResourceNotFoundException(String errorMessage) {
        super(ERROR_CODE, errorMessage);
    }

}
