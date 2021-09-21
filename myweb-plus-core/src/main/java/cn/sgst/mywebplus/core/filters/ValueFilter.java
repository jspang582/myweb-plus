package cn.sgst.mywebplus.core.filters;

/**
 * 值过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/20 0:22
 */
@FunctionalInterface
public interface ValueFilter {


    /**
     * 处理字符串值
     *
     * @param value 处理前的值
     * @return 处理后的值
     */
    String process(String value);
}
