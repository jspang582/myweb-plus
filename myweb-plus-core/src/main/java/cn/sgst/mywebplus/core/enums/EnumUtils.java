package cn.sgst.mywebplus.core.enums;

import cn.sgst.mywebplus.core.dict.Dict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 基于IEnum实现的枚举工具类
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 15:35
 */
public class EnumUtils {

    /**
     * 指定类是否为Enum类
     *
     * @param clazz 类
     * @return 是否为Enum类
     */
    public static boolean isEnum(Class<?> clazz) {
        if(clazz == null) {
            throw new IllegalArgumentException("clazz must not be null");
        }
        return clazz.isEnum();
    }


    /**
     * Enum对象转String，调用{@link Enum#name()} 方法
     *
     * @param e Enum
     * @return name值
     */
    public static String toString(Enum<?> e) {
        return null != e ? e.name() : null;
    }

    /**
     * 获得枚举类中各枚举对象下value的值
     *
     * @param clazz 枚举类
     * @return value值列表
     */
    public static <K, V,T extends IEnum<K, V>>  List<K> getValues(Class<T> clazz) {
        return getEnumConstants(clazz).stream().map(IEnum::getValue).collect(Collectors.toList());
    }


    /**
     * 获得枚举类中各枚举对象下text的值
     *
     * @param clazz 枚举类
     * @return text值列表
     */
    public static <K, V,T extends IEnum<K, V>> List<V> getTexts(Class<T> clazz) {
        return getEnumConstants(clazz).stream().map(IEnum::getText).collect(Collectors.toList());
    }

    /**
     * 获得枚举类中各枚举对象
     *
     * @param clazz clazz 枚举类
     * @return 枚举对象列表
     */
    public static <K, V,T extends IEnum<K, V>> List<T> getEnumConstants(Class<T> clazz) {
        assertEnum(clazz);
        final T[] enums = clazz.getEnumConstants();
        if (enums == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(enums);
    }



    /**
     * 枚举类中的枚举对象转成字典
     *
     * @param clazz 枚举类
     * @return 字典集合
     */
    public static <K, V,T extends IEnum<K, V>> List<Dict<K,V>> getEnumDicts(Class<T> clazz) {
        List<T> enumConstants = getEnumConstants(clazz);
        return enumConstants.stream().map(iEnum -> new Dict<>(iEnum.getValue(), iEnum.getText())).collect(Collectors.toList());
    }



    /**
     * 根据value获取对应的枚举对象
     *
     * @param value value值
     * @return 枚举对象
     */
    public static <K, V,T extends IEnum<K,V>> T fromValue(Class<T> clazz, Object value) {
        assertEnum(clazz);
        List<T> enumConstants = getEnumConstants(clazz);
        for (T e : enumConstants) {
            if (Objects.equals(value, e.getValue())) {
                return e;
            }
        }
        return null;
    }


    /**
     * 根据text获取对应的枚举对象
     *
     * @param text text值
     * @return 枚举对象
     */
    public static <K, V,T extends IEnum<K,V>> T fromText(Class<T> clazz, Object text) {
        assertEnum(clazz);
        List<T> enumConstants = getEnumConstants(clazz);
        for (T e : enumConstants) {
            if (Objects.equals(text, e.getText())) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据value获取对应的text
     *
     * @param value value值
     * @return 对应的text
     */
    public static <K, V,T extends IEnum<K,V>> V getTextFromValue(Class<T> clazz, Object value) {
        IEnum<K, V> e = fromValue(clazz, value);
        return e == null ? null : e.getText();
    }


    /**
     * 根据text获取对应的value
     *
     * @param text text值
     * @return 对应的value值
     */
    public static <K, V,T extends IEnum<K,V>> K getValueFromText(Class<T> clazz, Object text) {
        IEnum<K, V> e = fromText(clazz, text);
        return e == null ? null : e.getValue();
    }

    /**
     * 添加一个枚举类的所有枚举值,看起来像继承
     *
     * @param to 要修改的枚举类型
     * @param from 来源枚举类型
     */
    public static <T extends Enum<?>,G extends Enum<?>> void extendIEnum(Class<T> to,Class<G> from) {
        if(from.isAssignableFrom(IEnum.class)) {
            throw new RuntimeException("class " + from + " is not an instance of IEnum");
        }
        G[] constants = from.getEnumConstants();
        for (G constant : constants) {
            IEnum iEnum = (IEnum)constant;
            DynamicEnumUtils.addEnum(to,constant.name(),new Class<?>[] {iEnum.getValue().getClass(),iEnum.getText().getClass()}, new Object[] {iEnum.getValue(),iEnum.getText()});
        }
    }


    /**
     * 断言是否是枚举类型
     *
     * @param clazz 类
     */
    private static void assertEnum(Class<?> clazz) {
        if(!isEnum(clazz)) {
            throw new IllegalArgumentException("clazz must be enum type");
        }
    }
}
