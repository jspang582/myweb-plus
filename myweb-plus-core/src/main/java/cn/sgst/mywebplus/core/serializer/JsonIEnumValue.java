package cn.sgst.mywebplus.core.serializer;


import cn.sgst.mywebplus.core.enums.IEnum;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 指定序列化的枚举value值
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/4/14 13:18
 * @see IEnumValueSerializer
 */
@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonIEnumValue {

    /**
     * 枚举类型
     */
    Class<? extends IEnum> enumType();

    /**
     * 是否支持多值
     */
    boolean multiple() default false;

    /**
     * 分隔符,争对字符串类型
     */
    String separator() default ",";
}
