package cn.sgst.mywebplus.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.util.List;


/**
 * 测试枚举工具类
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 15:51
 */
public class IEnumTests {

    @Test
    public void getValues() {
        List<String> values = EnumUtils.getValues(SexEnum.class);
        System.out.println(values);
    }

    @Getter
    @AllArgsConstructor
    private enum SexEnum implements IEnum<String,String> {

        MALE("0", "男"),
        FEMALE("1", "女"),;

        private String value;
        private String text;
    }
}




