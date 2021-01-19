package cn.sgst.mywebplus.core.enums;

/**
 * 定义枚举规范
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 15:34
 */
public interface IEnum<K,V> {

    K getValue();

    V getText();
}
