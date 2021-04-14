package cn.sgst.mywebplus.samples.serialize;

import cn.sgst.mywebplus.core.serializer.IEnumValueSerializer;
import cn.sgst.mywebplus.core.serializer.IEnumClassSerializer;
import cn.sgst.mywebplus.core.serializer.IEnumSerializer;
import cn.sgst.mywebplus.core.serializer.JsonIEnumValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 11:17
 */
@Data
public class UserVO {


    private User user = new User();

    private Integer age;
    private String name;

    @JsonIEnumValue(enumType = HobbyEnum.class)
    @JsonSerialize(using = IEnumValueSerializer.class)
    private String hobby;

    @JsonSerialize(contentUsing = IEnumSerializer.class)
    private List<HobbyEnum> hobbies;

    @JsonSerialize(using = IEnumSerializer.class)
    private SexEnum sex;

    @JsonSerialize(using = IEnumClassSerializer.class)
    private Class<SexEnum> sexDicts;

    public UserVO() {
        sexDicts = SexEnum.class;
    }

    @JsonIEnumValue(enumType = HobbyEnum.class)
    @JsonSerialize(contentUsing = IEnumValueSerializer.class)
    public List<String> getMethodHobby() {
        return Arrays.asList("2", "3");
    }

    @Data
    public class User {
        @JsonIEnumValue(enumType = HobbyEnum.class,multiple = true)
        @JsonSerialize(using = IEnumValueSerializer.class)
        private String hobby1 = "1,2";
        @JsonIEnumValue(enumType = HobbyEnum.class)
        @JsonSerialize(contentUsing = IEnumValueSerializer.class)
        private List<String> hobbies1 = Arrays.asList("1", "4");
    }
}
