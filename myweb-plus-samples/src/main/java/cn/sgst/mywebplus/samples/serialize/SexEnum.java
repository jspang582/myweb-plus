package cn.sgst.mywebplus.samples.serialize;

import cn.sgst.mywebplus.core.enums.IEnum;
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

    @Override
    public String getValue() {
        return sexCode;
    }

    @Override
    public String getText() {
        return sexDesc;
    }
}
