package cn.sgst.mywebplus.samples.enumsync;

import cn.sgst.mywebplus.core.dict.DictDetails;
import cn.sgst.mywebplus.core.dict.DictDetailsProvider;
import cn.sgst.mywebplus.core.dict.LoadDictDetailsException;
import cn.sgst.mywebplus.core.dict.SimpleDict;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 默认字典数据提供者
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/22 14:31
 */
public class DefaultDictDetailsProvider implements DictDetailsProvider {

    private Collection<DictDetails> dictDetails;

    public DefaultDictDetailsProvider() {
        dictDetails = new ArrayList<>();
        dictDetails.add(new SimpleDict("sex_type","0","正常"));
        dictDetails.add(new SimpleDict("sex_type","1","不正常"));
    }


    @Override
    public Collection<DictDetails> loadDictsByDictType(String dictType) throws LoadDictDetailsException {
        return dictDetails.stream().filter(dict -> Objects.equals(dictType,dict.getDictType())).collect(Collectors.toList());
    }

    @Override
    public Collection<DictDetails> loadAllDicts() throws LoadDictDetailsException {
        return dictDetails;
    }
}
