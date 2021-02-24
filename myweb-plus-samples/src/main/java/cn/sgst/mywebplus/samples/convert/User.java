package cn.sgst.mywebplus.samples.convert;

import cn.sgst.mywebplus.samples.serialize.SexEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 14:54
 */
@Data
public class User {

    private String name;
    private Integer age;
    //@JsonDeserialize(using = IEnumDeserializer.class)
    private SexEnum sex;
}
