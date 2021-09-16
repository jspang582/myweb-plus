package cn.sgst.mywebplus.core.filters;


import cn.hutool.http.HTMLFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 13:56
 */
public class XssFilter implements Filter {

    @Getter
    private FilterConfig filterConfig;
    @Setter
    @Getter
    private String[] excludeUrlPatterns = {};


    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (shouldFilter((HttpServletRequest) request)) {
            HTMLFilter htmlFilter = new HTMLFilter();
            chain.doFilter(new XssFilterRequestWrapper((HttpServletRequest) request, htmlFilter), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }


    /**
     * 是否应该过滤
     */
    protected boolean shouldFilter(HttpServletRequest request) {
        boolean shouldFilter = true;
        for (String excludeUrlPattern : excludeUrlPatterns) {
            AntPathMatcher matcher = new AntPathMatcher();
            if (matcher.match(excludeUrlPattern, request.getRequestURI())) {
                shouldFilter = false;
                break;
            }
        }
        return shouldFilter;
    }
}
