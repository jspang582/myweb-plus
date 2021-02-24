package cn.sgst.mywebplus.core.convert;


import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.enums.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Objects;

/**
 * 字符串转IEnum类型转换工厂
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 13:54
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class StringToIEnumConverterFactory implements ConverterFactory<String, IEnum> {

    @Override
    public <T extends IEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToIEnumConverter(targetType);
    }


    private static class StringToIEnumConverter<T extends IEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        private StringToIEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (StrUtil.isEmpty(source)) {
                return null;
            }
            return getIEnum(enumType, source);
        }
    }


    private static <T extends IEnum> T getIEnum(Class<T> targetType, String source) {
        T[] enumConstants = targetType.getEnumConstants();
        for (T enumObj : enumConstants) {
            if (Objects.equals(source, enumObj.getValue().toString())) {
                return enumObj;
            }
        }
        return null;
    }
}
