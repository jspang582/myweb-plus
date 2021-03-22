package cn.sgst.mywebplus.core.dict;

import java.io.Serializable;

/**
 * 字典详情接口
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/22 9:49
 */
public interface DictDetails extends Serializable {


    /**
     * 获取字典类型
     * @return String
     */
    String getDictType();

    /**
     * 获取字典Value
     * @return String
     */
    String getDictValue();


    /**
     * 获取字典Text
     * @return String
     */
    String getDictText();
}
