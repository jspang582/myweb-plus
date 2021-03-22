package cn.sgst.mywebplus.samples.enumsync;

import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.dict.DictDetails;
import cn.sgst.mywebplus.core.dict.DictEnumSyncException;
import cn.sgst.mywebplus.core.dict.DictEnumSyncProcessor;

import java.util.Collection;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 17:35
 */
public class DelEnumSyncProcessor implements DictEnumSyncProcessor {
    @Override
    public void processSync(String dictType, Collection<DictDetails> dictData, Class<? extends Enum<?>> enumType) throws DictEnumSyncException {
        System.out.println(StrUtil.format("dictType: {} 同步至 enumType : {}",dictType,enumType));
        System.out.println("dictData :" + dictData);
    }
}
