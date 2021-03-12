package cn.sgst.mywebplus.autoconfigure;

import cn.sgst.mywebplus.core.enums.IEnum;
import cn.sgst.mywebplus.core.serializer.IEnumSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

/**
 * 配置jackson
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/12 11:21
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class MywebPlusJacksonAutoConfiguration {


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
    static class JacksonObjectMapperConfiguration {
        @Bean
        @Primary
        @ConditionalOnMissingBean
        ObjectMapper jacksonObjectMapper(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Jackson2ObjectMapperBuilder builder) {
            ObjectMapper objectMapper = builder.createXmlMapper(false).build();
            //将json属性的空值null转化为空字符串""
            objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                @Override
                public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString("");
                }
            });
            SimpleModule module = new SimpleModule();
            // 添加IEnum枚举序列化器
            module.addSerializer(IEnum.class, new IEnumSerializer());
            objectMapper.registerModule(module);
            return objectMapper;
        }
    }
}
