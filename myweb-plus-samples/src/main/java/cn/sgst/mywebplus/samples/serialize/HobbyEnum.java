package cn.sgst.mywebplus.samples.serialize;

import cn.sgst.mywebplus.core.enums.EnumUtils;
import cn.sgst.mywebplus.core.enums.IStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 爱好枚举
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/12 13:07
 */
@Getter
@AllArgsConstructor
public enum  HobbyEnum implements IStringEnum {

    /**
     * 唱
     */
    SING ("1","唱"),

    /**
     * 跳
     */
    JUMP ("2","跳"),

    /**
     * rap
     */
    RAP("3","rap"),

    /**
     * 篮球
     */
    Basketball("4","篮球"),
    ;

    private final String value;
    private final String text;


    @JsonCreator
    public static HobbyEnum fromValue(String value) {
        return EnumUtils.fromValue(HobbyEnum.class,value);
    }

}
