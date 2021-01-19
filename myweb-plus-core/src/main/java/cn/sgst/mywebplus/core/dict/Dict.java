package cn.sgst.mywebplus.core.dict;

import lombok.Data;

import java.io.Serializable;

/**
 * 定义字典基本项
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/25 15:09
 */
@Data
public class Dict<K,V> implements Serializable {

    /**
     * 字典value
     */
    private K value;

    /**
     * 字典文本
     */
    private V text;

    public Dict() {
    }

    public Dict(K value, V text) {
        this.value = value;
        this.text = text;
    }
}
