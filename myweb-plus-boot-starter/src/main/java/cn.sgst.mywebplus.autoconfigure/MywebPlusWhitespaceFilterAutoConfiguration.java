package cn.sgst.mywebplus.autoconfigure;

import cn.sgst.mywebplus.core.filters.EnableWhitespaceFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
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
@EnableWhitespaceFilter
public class MywebPlusWhitespaceFilterAutoConfiguration {

}
