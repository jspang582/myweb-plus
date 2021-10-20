package cn.sgst.mywebplus.core.dict;

/**
 * 数据字典同步到枚举类的命令
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/10/20 22:31
 */
public interface DictEnumSyncCommand {


    /**
     * 同步指定数据字典
     *
     * @param dictType 数据字典类型
     */
    void sync(String dictType);

    /**
     * 同步所有
     */
    void syncAll();
}
