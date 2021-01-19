package cn.sgst.mywebplus.extensions.feign;

import com.alibaba.fastjson.JSON;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

/**
 * 2xx响应成功后调用
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/11/17 9:48
 */
@Slf4j
public class MywebPlusFeignDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if(type instanceof Class || type instanceof ParameterizedType
                || type instanceof WildcardType) {
            Response.Body body = response.body();
            String content = Util.toString(body.asReader(Util.UTF_8));
            MywebPlusFeignResponse responseEntity = JSON.parseObject(content, MywebPlusFeignResponse.class);
            if (responseEntity.getSuccess()) {
                Object data = responseEntity.getData();
                String dataText = JSON.toJSONString(data);
                log.info("调用服务响应成功,返回数据: {}",dataText);
                return JSON.parseObject(dataText, type);
            }
            log.error("调用服务响应失败,body内容: {}",content);
            throw new MywebPlusFeignDecoderException(responseEntity.getCode(), responseEntity.getMessage(), response.request());
        }
        throw new DecodeException(response.status(),
                "type is not an instance of Class or ParameterizedType: " + type,
                response.request());
    }


}
