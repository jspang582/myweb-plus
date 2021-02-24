package cn.sgst.mywebplus.samples.serialize;

import cn.sgst.mywebplus.core.serializer.IEnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 11:17
 */
@Data
public class UserVO {

    private Integer age;
    private String name;
    @JsonSerialize(using = IEnumSerializer.class)
    private SexEnum sex;
}
