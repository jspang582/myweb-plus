package cn.sgst.mywebplus.autoconfigure;

import cn.sgst.mywebplus.core.error.DefaultErrorMessageHandler;
import cn.sgst.mywebplus.core.error.ErrorMessageHandler;
import cn.sgst.mywebplus.core.error.MywebPlusErrorAttributes;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

/**
 * <p>
 * 替换Spring Boot默认的MVC error配置
 * 如果不使用这里自定义的配置,可在启动类中排除该配置
 * {@link EnableAutoConfiguration Auto-configuration} to render errors via an MVC error
 * </p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/6 17:02
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@AutoConfigureBefore({WebMvcAutoConfiguration.class})
@EnableConfigurationProperties({MywebPlusErrorProperties.class})
public class MywebPlusErrorAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(value = ErrorMessageHandler.class)
    public ErrorMessageHandler errorMessageHandler() {
        return new DefaultErrorMessageHandler();
    }

    @Bean
    public ErrorAttributes errorAttributes(ErrorMessageHandler errorMessageHandler) {
        return new MywebPlusErrorAttributes(errorMessageHandler);
    }


    @Bean
    public ErrorController errorController(ErrorAttributes errorAttributes,
                                           ErrorViewResolver errorViewResolver,
                                           MywebPlusErrorProperties errorProperties) {
        return new MywebPlusErrorController(errorAttributes,
                errorViewResolver,
                errorProperties);
    }
}
