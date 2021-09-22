package cn.sgst.mywebplus.core;


import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * web日志记录开关
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 16:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WebLogRegistrar.class)
public @interface EnableWebLog {

    /**
     * 需要记录web日志的beanName,支持通配符
     */
    String[] beanNames() default {"*Controller"};


    /**
     * 是否显示执行时间
     */
    boolean showExecutionTime() default true;

    /**
     * 是否显示返回值
     */
    boolean showReturnVal() default true;

    /**
     * 执行顺序,数值越小越先执行
     */
    int order() default Ordered.LOWEST_PRECEDENCE;
}
