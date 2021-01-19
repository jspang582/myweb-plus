package cn.sgst.mywebplus.extensions.feign;

import feign.FeignException;
import feign.Request;
import lombok.Getter;

/**
 * 定义转码异常
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 15:37
 */
@Getter
public class MywebPlusFeignDecoderException extends FeignException {

    private final int code;
    private final String message;

    public MywebPlusFeignDecoderException(int status, String message, Request request) {
        super(status, message, request);
        this.code = status;
        this.message = message;
    }
}
