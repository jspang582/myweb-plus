package cn.sgst.mywebplus.core.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *  过滤请求参数两端空格
 * </p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/4/7 9:19
 */
public class ParameterFilter implements Filter {


    public ParameterFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ParameterRequestWrapper parameterRequestWrapper = new ParameterRequestWrapper((HttpServletRequest) (request));
        chain.doFilter(parameterRequestWrapper,response);
    }
}
