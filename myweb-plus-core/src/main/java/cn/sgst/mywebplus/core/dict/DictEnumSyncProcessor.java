package cn.sgst.mywebplus.core.dict;


import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * 数据字典同步枚举处理器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 13:51
 */
public interface DictEnumSyncProcessor {

    /**
     * 处理同步
     * 字典数据依赖provider,如果没有提供,dictData就是空
     * @param dictType 数据字典类型
     * @param dictData 字典数据
     * @param enumType 枚举类型
     * @throws DictEnumSyncException 数据字典同步枚举异常
     * @see DictDetailsProvider
     */
    void processSync(String dictType, @Nullable Collection<DictDetails> dictData, Class<? extends Enum<?>> enumType) throws DictEnumSyncException;
}
