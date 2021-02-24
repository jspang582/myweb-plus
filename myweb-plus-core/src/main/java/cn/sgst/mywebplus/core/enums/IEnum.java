package cn.sgst.mywebplus.core.enums;

/**
 * 定义枚举规范
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 15:34
 */
public interface IEnum<K,V> {


    /**
     * 获取value值
     *
     * @return value
     */
    K getValue();


    /**
     * 获取文本值
     * @return text
     */
    V getText();
}
