package cn.sgst.mywebplus.core.serializer;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.dict.Dict;
import cn.sgst.mywebplus.core.enums.EnumUtils;
import cn.sgst.mywebplus.core.enums.IEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 枚举value值序列化器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/4/14 0:39
 */
public class IEnumValueSerializer extends JsonSerializer<Object> {

    @SuppressWarnings("unchecked")
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        JsonStreamContext context = gen.getOutputContext();
        String currentName = context.getCurrentName();
        Object currentValue = context.getCurrentValue();
        if (context.inArray()) {
            currentName = context.getParent().getCurrentName();
            currentValue = context.getParent().getCurrentValue();
        }
        Class<?> type = currentValue.getClass();
        JsonIEnumValue annotation = findAnnotation(currentName, type);
        if (annotation == null) {
            gen.writeNull();
            return;
        }
        Class<? extends IEnum> enumType = annotation.enumType();
        boolean multiple = annotation.multiple();
        if (multiple) {
            String strVal = Convert.toStr(value);
            List<String> itemValues = StrUtil.splitTrim(strVal, annotation.separator());
            gen.writeStartArray();
            for (String itemValue : itemValues) {
                IEnum iEnum = EnumUtils.fromValueFuzzy(enumType, itemValue);
                if(iEnum != null) {
                    gen.writeObject(new Dict<>(iEnum.getValue(), iEnum.getText()));
                }else {
                    gen.writeNull();
                }
            }
            gen.writeEndArray();
            return;
        }
        IEnum iEnum = EnumUtils.fromValueFuzzy(enumType, value);
        if (iEnum == null) {
            gen.writeNull();
            return;
        }
        gen.writeObject(new Dict<>(iEnum.getValue(), iEnum.getText()));
    }


    /**
     * 先获取Method上注解,如果没有再获取Field注解
     */
    private JsonIEnumValue findAnnotation(String name, Class<?> type) {
        String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
        Method method = ReflectUtil.getMethodByName(type, methodName);
        JsonIEnumValue annotation = AnnotationUtil.getAnnotation(method, JsonIEnumValue.class);
        if (annotation != null) {
            return annotation;
        }
        Field field = ReflectUtil.getField(type, name);
        if (field != null) {
            return AnnotationUtil.getAnnotation(field, JsonIEnumValue.class);
        }
        return null;
    }

}



