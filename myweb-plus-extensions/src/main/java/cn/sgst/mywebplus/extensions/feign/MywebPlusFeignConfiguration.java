package cn.sgst.mywebplus.extensions.feign;

import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;

/**
 * 远程调用Encoder、Decoder等自定义配置
 * @see {@code AsyncResponseHandler}
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 15:43
 */
public class MywebPlusFeignConfiguration {

    /**
     * 2xx响应自定义解析
     */
    @Bean
    public Decoder decoder() {
        // return new SgstDecoder();
        // 责任链模式调用
        return new OptionalDecoder(new ResponseEntityDecoder(new MywebPlusFeignDecoder()));
    }


    /**
     * 非2xx响应解析
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new MywebPlusFeignErrorDecoder();
    }
}
