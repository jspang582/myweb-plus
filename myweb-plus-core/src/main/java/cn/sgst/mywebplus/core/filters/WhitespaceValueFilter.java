package cn.sgst.mywebplus.core.filters;

import cn.hutool.core.util.StrUtil;

/**
 * 去除两边空格过滤
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/20 0:28
 */
public class WhitespaceValueFilter implements ValueFilter {

    @Override
    public String process(String value) {
        return StrUtil.trim(value);
    }
}
