package cn.sgst.mywebplus.samples.convert;

import cn.sgst.mywebplus.samples.serialize.HobbyEnum;
import cn.sgst.mywebplus.samples.serialize.SexEnum;
import lombok.Data;

import java.util.List;

/**
 * 用户实体
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 14:54
 */
@Data
public class User {

    private String name;
    private Integer age;
    private SexEnum sex;
    private List<HobbyEnum> hobbyies;
}
