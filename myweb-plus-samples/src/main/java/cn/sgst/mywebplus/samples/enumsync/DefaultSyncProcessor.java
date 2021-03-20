package cn.sgst.mywebplus.samples.enumsync;

import cn.hutool.core.util.EnumUtil;
import cn.sgst.mywebplus.core.dict.DictEnumSyncException;
import cn.sgst.mywebplus.core.dict.DictEnumSyncProcessor;
import cn.sgst.mywebplus.core.enums.DynamicEnumUtils;

import java.util.List;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 15:39
 */
public class DefaultSyncProcessor implements DictEnumSyncProcessor {
    @Override
    public void processSync(String dictType, Class<? extends Enum<?>> enumType) throws DictEnumSyncException {
        DynamicEnumUtils.addEnum(
                enumType,
                "RANDOM",
                new Class<?>[] {String.class,String.class},
                new Object[] {"F","女"}
                );
        List<String> names = EnumUtil.getNames(enumType);
        System.out.println(names);
        List<Object> values = EnumUtil.getFieldValues(enumType, "value");
        System.out.println(values);
        List<Object> texts = EnumUtil.getFieldValues(enumType, "text");
        System.out.println(texts);
    }
}
