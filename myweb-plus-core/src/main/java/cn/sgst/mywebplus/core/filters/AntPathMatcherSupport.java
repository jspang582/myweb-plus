package cn.sgst.mywebplus.core.filters;

import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 关于路径通配符匹配的支持
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/17 14:07
 */
public class AntPathMatcherSupport extends AntPathMatcher {

    /**
     * 排除的路径通配符
     */
    private String[] excludeUrlPatterns = {};

    /**
     * 是否应该过滤
     */
    public boolean shouldFilter(HttpServletRequest request) {
        boolean shouldFilter = true;
        for (String excludeUrlPattern : excludeUrlPatterns) {
            if (match(excludeUrlPattern, request.getRequestURI())) {
                shouldFilter = false;
                break;
            }
        }
        return shouldFilter;
    }

    /**
     * 获取通配符
     */
    public String[] getExcludeUrlPatterns() {
        return excludeUrlPatterns;
    }

    /**
     * 设置通配符
     */
    public void setExcludeUrlPatterns(String[] excludeUrlPatterns) {
        this.excludeUrlPatterns = excludeUrlPatterns;
    }
}
