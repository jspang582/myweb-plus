package cn.sgst.mywebplus.core.convert;


import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.enums.EnumUtils;
import cn.sgst.mywebplus.core.enums.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;


/**
 * 字符串转IEnum类型转换工厂
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 13:54
 */
@SuppressWarnings({"all"})
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
            return EnumUtils.fromValueFuzzy(enumType, source);
        }
    }

}
