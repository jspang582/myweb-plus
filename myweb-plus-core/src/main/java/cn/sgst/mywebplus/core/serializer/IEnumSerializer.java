package cn.sgst.mywebplus.core.serializer;

import cn.sgst.mywebplus.core.dict.Dict;
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


    @Override
    public void serialize(IEnum iEnum, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(new Dict<>(iEnum.getValue(),iEnum.getText()));
    }

}
