package cn.sgst.mywebplus.core.error;

import cn.sgst.mywebplus.core.enums.EnumUtils;
import cn.sgst.mywebplus.core.exception.IExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@link ErrorMessageHandler} 的默认实现
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/6 9:36
 */
public class DefaultErrorMessageHandler implements ErrorMessageHandler {
    @Override
    public String describeErrorStatus(Integer errorStatus) {
        return EnumUtils.getTextFromValue(ErrorHttpStatus.class,errorStatus);
    }

    @AllArgsConstructor
    @Getter
    private enum ErrorHttpStatus implements IExceptionEnum {
        BAD_REQUEST(400,"参数错误"),
        UNAUTHORIZED(401,"未认证"),
        FORBIDDEN(403,"禁止访问"),
        NOT_FOUND(404,"请求未找到"),
        INTERNAL_SERVER_ERROR(500,"服务器开小差了"),
        ;
        private final Integer code;
        private final String message;
    }
}
