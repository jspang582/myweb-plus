package cn.sgst.mywebplus.core.filters;


import cn.hutool.http.HTMLFilter;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * XSS过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 13:56
 */
public class XssFilter extends AntPathMatcherSupport implements Filter {

    @Getter
    private FilterConfig filterConfig;
    @Setter
    @Getter
    private Map<String,Object> conf = new HashMap<>();


    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (shouldFilter((HttpServletRequest) request)) {
            chain.doFilter(new FilterRequestWrapper((HttpServletRequest) request, new XssValueFilter(new HTMLFilter(conf))), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

}
