package cn.sgst.mywebplus.core.validation.constraintvalidators;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.enums.EnumUtils;
import cn.sgst.mywebplus.core.enums.IEnum;
import cn.sgst.mywebplus.core.validation.constraints.EnumValue;

import java.util.List;

/**
 * 校验枚举值合法性
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/22 13:04
 */
public class EnumValueValidator extends AbstractEmptyBasedValidator<EnumValue, Object> {

    private Class<? extends IEnum> enumType;
    private boolean multiAllowed;
    private String separator;

    @Override
    public void initialize(EnumValue enumValue) {
        enumType = enumValue.target();
        multiAllowed = enumValue.multiAllowed();
        separator = enumValue.separator();
    }


    @Override
    @SuppressWarnings("all")
    public boolean isValid(Object value) {
        String strVal = Convert.toStr(value);
        // 不允许多值
        if (!multiAllowed) {
            return EnumUtils.fromValueFuzzy(enumType, strVal) != null;
        }
        // 允许多值
        List<String> vals = StrUtil.splitTrim(strVal, separator);
        for (String val : vals) {
            IEnum iEnum = EnumUtils.fromValueFuzzy(enumType, val);
            if (iEnum == null) {
                return false;
            }
        }
        return true;
    }

}
