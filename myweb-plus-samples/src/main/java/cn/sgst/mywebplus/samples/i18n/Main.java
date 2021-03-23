package cn.sgst.mywebplus.samples.i18n;

import cn.sgst.mywebplus.core.enums.DynamicEnumUtils;
import cn.sgst.mywebplus.core.enums.EnumUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.Locale;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/23 14:49
 */
@SpringBootTest
public class Main {


    @Test
    void test() {
        LocaleContextHolder.setLocale(Locale.US);
        List<String> texts = EnumUtils.getTexts(Level.class);
        System.out.println(texts);
        DynamicEnumUtils.addEnum(
                Level.class,
                "RANDOM",
                new Class<?>[] {String.class,String.class},
                new Object[] {"3","level.BAD"}
        );
        System.out.println(EnumUtils.getTexts(Level.class));
    }
}
