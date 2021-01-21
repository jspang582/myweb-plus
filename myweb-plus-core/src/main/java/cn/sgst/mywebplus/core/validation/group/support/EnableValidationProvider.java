package cn.sgst.mywebplus.core.validation.group.support;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 抽象动态校验组开关
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/21 10:18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AbstractGroupSequenceProvider.RequestHolder.class})
public @interface EnableValidationProvider {
}
