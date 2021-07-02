package cn.sgst.mywebplus.autoconfigure;

import cn.sgst.mywebplus.core.filters.ParameterFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;

/**
 *  配置去除参数两端空格的过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 13:54
 */
@Configuration
@ConditionalOnClass({DispatcherType.class,DispatcherServlet.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class MywebPlusParameterFilterAutoConfiguration {


    /**
     * 去除参数头尾空格过滤器
     */
    @Bean
    public FilterRegistrationBean paramsFilterRegistration() {
        FilterRegistrationBean<ParameterFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ParameterFilter());
        registration.addUrlPatterns("/*");
        registration.setName("parameterFilter");
        registration.setOrder(1);
        return registration;
    }
}
