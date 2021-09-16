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
@Import(TrimFilterRegistrar.class)
public @interface EnableTrimFilter {

    /**
     * 排序
     */
    int order() default Ordered.HIGHEST_PRECEDENCE;
}