package cn.sgst.mywebplus.core.dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 定义字典基本项
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/25 15:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dict<K,V> implements Serializable {

    /**
     * 字典value
     */
    private K value;

    /**
     * 字典文本
     */
    private V text;

}
