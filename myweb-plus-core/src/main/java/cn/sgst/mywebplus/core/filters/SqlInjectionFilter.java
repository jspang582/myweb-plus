package cn.sgst.mywebplus.core.filters;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * <p>
 *     防sql注入过滤器
 * </p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/10 9:54
 */
@Slf4j
public class SqlInjectionFilter implements Filter {

    public SqlInjectionFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        /**
         * get请求执行sql过滤
         */
        String method = req.getMethod();
        if("GET".equalsIgnoreCase(method)) {
            SqlInjectionRequestWrapper sqlInjectionRequestWrapper = new SqlInjectionRequestWrapper(req);
            chain.doFilter(sqlInjectionRequestWrapper,response);
        } else {
            chain.doFilter(req,response);
        }


    }
}
