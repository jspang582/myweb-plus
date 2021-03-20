package cn.sgst.mywebplus.core.dict;


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
     *
     * @param dictType 数据字典类型
     * @param enumType 枚举类型
     * @throws DictEnumSyncException 数据字典同步枚举异常
     */
    void processSync(String dictType, Class<? extends Enum<?>> enumType) throws DictEnumSyncException;
}
