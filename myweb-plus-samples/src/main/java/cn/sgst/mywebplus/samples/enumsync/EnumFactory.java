package cn.sgst.mywebplus.samples.enumsync;

import cn.sgst.mywebplus.core.enums.IStringEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 15:37
 */
public class EnumFactory {


    @AllArgsConstructor
    @Getter
    public enum SexEnum implements IStringEnum {

        /**
         * 男
         */
        MALE("M","男"),
        ;
        private final String value;
        private final String text;
    }


    @AllArgsConstructor
    @Getter
    public enum StatusEnum implements IStringEnum {
        ;
        private final String value;
        private final String text;
    }
}
