package cn.sgst.mywebplus.core.filters;

import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 开启去除参数两边空格
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 17:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WhitespaceFilterRegistrar.class)
public @interface EnableWhitespaceFilter {

    /**
     * 需要拦截的请求,支持通配符
     */
    String[] includeUrlPatterns() default {"/*"};

    /**
     * 不需要拦截的请求,支持通配符
     */
    String[] excludeUrlPatterns() default {};

    /**
     * 排序
     */
    int order() default Ordered.HIGHEST_PRECEDENCE;
}
