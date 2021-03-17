package cn.sgst.mywebplus.core.validation.constraintvalidators;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.enums.IEnum;
import cn.sgst.mywebplus.core.validation.constraints.EnumValue;
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
    @SuppressWarnings("all")
    public boolean isValid(Object value) {
        // 只允许是Number 或者 String类型
        if(!(value instanceof Number || value instanceof String)) {
            throw new IllegalArgumentException("value :" + value + "只允许Number或者String类型");
        }
        List<String> enumValues = Arrays.stream(targetEnum.getEnumConstants()).map(item -> {
            // 调用toString()方法
            return item.getValue().toString();
        }).collect(Collectors.toList());
        // 不允许多值
        if (!multiAllowed) {
            return enumValues.contains(value.toString());
        }
        // 允许多值
        List<String> values = Arrays.asList(StrUtil.split(value.toString(), separator));
        return CollUtil.containsAll(enumValues, values);
    }

}
