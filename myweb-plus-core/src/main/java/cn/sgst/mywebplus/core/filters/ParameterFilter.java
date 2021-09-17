package cn.sgst.mywebplus.core.filters;

import lombok.Getter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 * 过滤请求参数两端空格
 * </p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/4/7 9:19
 */
public class ParameterFilter extends AntPathMatcherSupport implements Filter {

    @Getter
    private FilterConfig filterConfig;


    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (shouldFilter((HttpServletRequest) request)) {
            chain.doFilter(new ParameterFilterRequestWrapper((HttpServletRequest) (request)), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
