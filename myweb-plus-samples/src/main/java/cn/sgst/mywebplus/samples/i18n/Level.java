package cn.sgst.mywebplus.samples.i18n;

import cn.sgst.mywebplus.core.SpringContextHolder;
import cn.sgst.mywebplus.core.enums.IEnum;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/23 14:49
 */

public enum Level implements IEnum<String, String> {
    LOW("0", "level.LOW"),
    MEDIUM("1", "level.MEDIUM"),
    HIGH("2", "level.HIGH"),;
    private final String value;
    private final String text;

    Level(String value, String text) {
        this.value = value;
        this.text = text;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getText() {
        return SpringContextHolder.getBean(MessageSource.class).getMessage(text, null, text, LocaleContextHolder.getLocale());
    }

}
