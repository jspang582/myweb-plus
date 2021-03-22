package cn.sgst.mywebplus.core.dict;

import cn.hutool.core.util.IdUtil;
import cn.sgst.mywebplus.core.enums.DynamicEnumUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 一般的同步处理器
 * <p>需要配合Provider使用,这样dictData才有数据</p>
 *
 * @see DictDetailsProvider
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/22 10:41
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
            DynamicEnumUtils.addEnum(
                    enumType,
                    IdUtil.simpleUUID().toUpperCase(),
                    new Class<?>[]{String.class, String.class},
                    new Object[]{dict.getDictValue(), dict.getDictText()}
            );
        }
    }
}
