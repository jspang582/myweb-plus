package cn.sgst.mywebplus.core.serializer;

import cn.sgst.mywebplus.core.dict.Dict;
import cn.sgst.mywebplus.core.enums.EnumUtils;
import cn.sgst.mywebplus.core.enums.IEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

/**
 * IEnum字典序列化器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/4/2 23:22
 */

public class IEnumClassSerializer extends JsonSerializer<Class<? extends IEnum>> {

    @SuppressWarnings("unchecked")
    @Override
    public void serialize(Class<? extends IEnum> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        List<? extends Dict<?, ?>> enumDicts = EnumUtils.getEnumDicts(value);
        gen.writeStartArray();
        for (Dict<?, ?> dict : enumDicts) {
            gen.writeObject(dict);
        }
        gen.writeEndArray();
    }

}
