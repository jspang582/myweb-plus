package cn.sgst.mywebplus.core.filters;

import cn.hutool.http.HTMLFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 去除xss标签
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 13:57
 */
public class XssFilterRequestWrapper extends AbstractFilterRequestWrapper {

    private final HTMLFilter htmlFilter;

    public XssFilterRequestWrapper(HttpServletRequest request, HTMLFilter htmlFilter) {
        super(request);
        this.htmlFilter = htmlFilter;
    }

    @Override
    protected String filter(String value) {
        return htmlFilter.filter(value);
    }
}
