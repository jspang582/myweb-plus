package cn.sgst.mywebplus.core.dict;

import java.util.Collection;

/**
 * 字典数据提供者
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/22 9:54
 */
public interface DictDetailsProvider {


    /**
     * 根据字典类型加载字典数据
     *
     * @param dictType 字典类型
     * @return 字典集合
     * @throws LoadDictDetailsException 获取字典异常
     */
     Collection<DictDetails> loadDictsByDictType(String dictType) throws LoadDictDetailsException;


    /**
     * 加载所有字典数据
     *
     * @return 字典集合
     * @throws LoadDictDetailsException 获取字典异常
     */
     Collection<DictDetails> loadAllDicts() throws LoadDictDetailsException;
}
