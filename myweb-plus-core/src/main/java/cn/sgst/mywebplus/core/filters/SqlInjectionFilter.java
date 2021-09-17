package cn.sgst.mywebplus.core.filters;


import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 * 防sql注入过滤器
 * </p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/10 9:54
 */
@Slf4j
public class SqlInjectionFilter extends AntPathMatcherSupport implements Filter {

    @Getter
    private FilterConfig filterConfig;
    /**
     * 请求方式
     */
    @Getter
    @Setter
    private String[] methods;


    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (shouldFilter(req)) {
            String method = req.getMethod();
            if (ArrayUtil.containsIgnoreCase(methods, method)) {
                SqlInjectionFilterRequestWrapper sqlInjectionRequestWrapper = new SqlInjectionFilterRequestWrapper(req);
                chain.doFilter(sqlInjectionRequestWrapper, response);
            } else {
                chain.doFilter(req, response);
            }

        } else {
            chain.doFilter(req, response);
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }

}
