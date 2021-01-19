package cn.sgst.mywebplus.extensions.feign;

import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;

/**
 * Http状态码不是2xx时调用
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 15:40
 */
@Slf4j
public class MywebPlusFeignErrorDecoder extends ErrorDecoder.Default {
    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = super.decode(methodKey, response);
        // 如果是RetryableException，则返回继续重试
        if (exception instanceof RetryableException) {
            return exception;
        }
        log.info("远程调用Sgst服务出错,错误Http状态码: {}",response.status());
        FeignException feignException = (FeignException)exception;
        if(feignException.responseBody().isPresent()) {
            ByteBuffer responseBody  = feignException.responseBody().get();
            String content;
            try {
                content = StandardCharsets.UTF_8.newDecoder().decode(responseBody.asReadOnlyBuffer()).toString();
                log.info("错误信息body: {}",content);
            } catch ( final CharacterCodingException e) {
                throw new RuntimeException(e);
            }
            MywebPlusFeignResponse responseEntity = JSONObject.parseObject(content, MywebPlusFeignResponse.class);
            // 包装body内异常信息
            throw new MywebPlusFeignDecoderException(responseEntity.getCode(),responseEntity.getMessage(),response.request());
        }else {
            // 包装http的状态信息
            throw new MywebPlusFeignDecoderException(response.status(),response.reason(),response.request());
        }
    }
}
