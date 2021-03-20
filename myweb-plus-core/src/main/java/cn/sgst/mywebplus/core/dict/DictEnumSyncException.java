package cn.sgst.mywebplus.core.dict;

import cn.hutool.core.util.StrUtil;

/**
 * 数据字典同步枚举异常
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 13:38
 */
public class DictEnumSyncException extends RuntimeException{

    /**
     * 提供带错误信息的构造函数
     * @param message 错误信息
     */
    public DictEnumSyncException(String message) {
        super(message);
    }


    /**
     * 封装错误信息的构造函数
     * @param dictType 数据字典type
     * @param enumType 枚举type
     * @param e 异常
     */
    public DictEnumSyncException(String dictType, Class<? extends Enum<?>> enumType, Throwable e) {
        super(StrUtil.format("Failed to sync dictType : {} to enumType : {}",dictType,enumType.getName()),e);
    }
}
