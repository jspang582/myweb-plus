package cn.sgst.mywebplus.core.dict;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.sgst.mywebplus.core.enums.DynamicEnumUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 一般的同步处理器
 * <p>需要配合Provider使用,这样dictData才有数据</p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/22 10:41
 * @see DictDetailsProvider
 */

public class GenericSyncProcessor implements DictEnumSyncProcessor {
    @Override
    public void processSync(String dictType, Collection<DictDetails> dictData, Class<? extends Enum<?>> enumType) throws DictEnumSyncException {
        if (dictData == null) {
            dictData = new ArrayList<>();
        }
        // 先清空枚举值
        DynamicEnumUtils.delAllEnum(enumType);
        // 再循环添加
        for (DictDetails dict : dictData) {
            Object dictValue = dict.getDictValue();
            String dictText = dict.getDictText();
            DynamicEnumUtils.addEnum(
                    enumType,
                    IdUtil.simpleUUID().toUpperCase(),
                    new Class<?>[]{dictValue.getClass(), String.class},
                    new Object[]{dict.getDictValue(), dictText}
            );
        }
        System.out.println(EnumUtil.getFieldValues(enumType,"text"));
    }
}
