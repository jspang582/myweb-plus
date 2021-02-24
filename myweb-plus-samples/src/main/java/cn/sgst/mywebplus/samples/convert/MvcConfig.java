package cn.sgst.mywebplus.samples.convert;

import cn.sgst.mywebplus.core.convert.StringToIEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC配置
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 14:50
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToIEnumConverterFactory());
    }
}
