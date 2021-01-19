package cn.sgst.mywebplus.core;


import org.springframework.context.annotation.Import;
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
@Import({WebLogAspect.class})
public @interface EnableWebLog {
}
