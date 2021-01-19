package cn.sgst.mywebplus.core.validation.constraintvalidators;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.util.ValidateUtil;
import cn.sgst.mywebplus.core.validation.constraints.StringDateTime;

import java.util.Date;
import java.util.Objects;

/**
 * 校验字符串时间格式
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/31 11:09
 */
public class StringDateTimeValidator extends AbstractEmptyBasedValidator<StringDateTime, String> {

    private String pattern;
    private boolean strict;

    @Override
    public void initialize(StringDateTime stringDateTime) {
        this.pattern = stringDateTime.pattern();
        this.strict = stringDateTime.strict();
    }

    @Override
    public boolean isValid(String value) {
        if (strict) {
            if (StrUtil.length(pattern) != StrUtil.length(value)) {
                return false;
            }
        }
        try {
            Date date = DateUtil.parse(value, pattern);
            // 防止 2019-13-25 => 2020-01-25
            String formatTime = DateUtil.format(date, pattern);
            return Objects.equals(formatTime, value);
        } catch (Exception e) {
            return false;
        }
    }
}
