package cn.sgst.mywebplus.samples.serialize;

import cn.sgst.mywebplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 性别枚举
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/2/24 11:17
 */
@Getter
@AllArgsConstructor
public enum SexEnum implements IEnum<String,String> {
    /**
     * 男
     */
    MALE("M","男"),

    /**
     * 女
     */
    FEMALE("F","女"),
    ;

    private final String sexCode;
    private final String sexDesc;

    @JsonCreator
    public static SexEnum fromValue(String value) {
        for (SexEnum item : SexEnum.values()) {
            if(item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }


    @Override
    public String getValue() {
        return sexCode;
    }

    @Override
    public String getText() {
        return sexDesc;
    }
}
