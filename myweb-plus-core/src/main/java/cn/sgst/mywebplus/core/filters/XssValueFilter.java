package cn.sgst.mywebplus.core.filters;

import cn.hutool.http.HTMLFilter;

/**
 * xss值过滤
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/20 0:24
 */
public class XssValueFilter implements ValueFilter {


    private final HTMLFilter htmlFilter;

    public XssValueFilter(HTMLFilter htmlFilter) {
        this.htmlFilter = htmlFilter;
    }

    @Override
    public String process(String value) {
        return htmlFilter.filter(value);
    }


    public HTMLFilter getHtmlFilter() {
        return htmlFilter;
    }
}
