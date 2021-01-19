package cn.sgst.mywebplus.core.validation.constraintvalidators;


import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.validation.constraints.StringArrayLimit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验字符串多个值元素个数
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/10/18 9:45
 */
public class StringArrayLimitValidator implements ConstraintValidator<StringArrayLimit, String> {

    private boolean ignoreEmpty;
    private int min;
    private int max;
    private String separator;


    @Override
    public void initialize(StringArrayLimit annotation) {
        ignoreEmpty = annotation.ignoreEmpty();
        min = annotation.min();
        max = annotation.max();
        separator = annotation.separator();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if(ignoreEmpty && StrUtil.isBlank(value)) {
            return true;
        }
        String[] elements = StrUtil.split(value,separator);
        return elements.length >= min && elements.length <= max;
    }
}
