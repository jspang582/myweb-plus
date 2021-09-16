package cn.sgst.mywebplus.core.filters;

import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 开启sql注入过滤
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 16:55
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SqlInjectionFilterRegistrar.class)
public @interface EnableSqlInjectionFilter {

    /**
     * 请求方式
     */
    String[] methods() default {"GET"};

    /**
     * 排序
     */
    int order() default Ordered.LOWEST_PRECEDENCE;
}
