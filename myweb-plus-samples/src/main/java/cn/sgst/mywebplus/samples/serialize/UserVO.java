package cn.sgst.mywebplus.samples.serialize;

import cn.sgst.mywebplus.core.serializer.IEnumClassSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 11:17
 */
@Data
public class UserVO {

    private Integer age;
    private String name;
    private SexEnum sex;
    @JsonSerialize(using = IEnumClassSerializer.class)
    private  Class<SexEnum> sexDicts;
    public UserVO() {
        sexDicts = SexEnum.class;
    }

}
