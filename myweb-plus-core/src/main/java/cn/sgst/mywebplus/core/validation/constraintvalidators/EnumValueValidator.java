package cn.sgst.mywebplus.core.validation.constraintvalidators;

import cn.hutool.core.collection.CollUtil;
import cn.sgst.mywebplus.core.enums.IEnum;
import cn.sgst.mywebplus.core.validation.constraints.EnumValue;
import org.springframework.util.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 校验枚举值合法性
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/22 13:04
 */
public class EnumValueValidator extends AbstractEmptyBasedValidator<EnumValue, Object> {

    private Class<? extends IEnum> targetEnum;
    private boolean multiAllowed;
    private String separator;

    @Override
    public void initialize(EnumValue enumValue) {
        targetEnum = enumValue.target();
        multiAllowed = enumValue.multiAllowed();
        separator = enumValue.separator();
    }


    @Override
    public boolean isValid(Object value) {
        List<? extends IEnum> enumConstants = Arrays.asList(targetEnum.getEnumConstants());
        List<String> enumValues = enumConstants.stream().map(enumConstant -> {
            // 统一转成字符串比较,防止类型不一样时比较结果为不相等
            return enumConstant.getValue() == null ? null : enumConstant.getValue().toString();
        }).collect(Collectors.toList());
        if (!multiAllowed) {
            return enumValues.contains(String.valueOf(value));
        }
        List<String> values = Arrays.asList(StringUtils.split(String.valueOf(value), separator));
        return CollUtil.containsAll(enumValues, values);
    }
}
