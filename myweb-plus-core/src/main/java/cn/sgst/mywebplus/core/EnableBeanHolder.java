package cn.sgst.mywebplus.core;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * SpringBean持有开关
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/22 14:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SpringContextHolder.class)
public @interface EnableBeanHolder {
}
