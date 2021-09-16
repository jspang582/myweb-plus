package cn.sgst.mywebplus.core.filters;


import cn.sgst.mywebplus.core.util.SqlInjectionUtil;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *     去除sql注入关键词
 * </p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/10 10:01
 */
@Slf4j
public class SqlInjectionFilterRequestWrapper extends AbstractFilterRequestWrapper {


    public SqlInjectionFilterRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected String filter(String value) {
        return SqlInjectionUtil.filterSql(value);
    }
}
