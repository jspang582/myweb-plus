package cn.sgst.mywebplus.core.serializer;

import cn.sgst.mywebplus.core.enums.IEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * IEnum枚举序列化器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 10:52
 */
public class IEnumSerializer extends JsonSerializer<IEnum> {

    private static final String VALUE_FIELD_NAME = "value";
    private static final String TEXT_FIELD_NAME = "text";

    @Override
    public void serialize(IEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField(VALUE_FIELD_NAME, value.getValue());
        gen.writeObjectField(TEXT_FIELD_NAME, value.getText());
        gen.writeEndObject();
    }

}
