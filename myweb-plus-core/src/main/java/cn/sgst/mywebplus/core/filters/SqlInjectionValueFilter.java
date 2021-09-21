package cn.sgst.mywebplus.core.filters;

import cn.sgst.mywebplus.core.util.SqlInjectionUtil;

/**
 * sql注入过滤
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/20 0:27
 */
public class SqlInjectionValueFilter implements ValueFilter {

    @Override
    public String process(String value) {
        return SqlInjectionUtil.filterSql(value);
    }
}
