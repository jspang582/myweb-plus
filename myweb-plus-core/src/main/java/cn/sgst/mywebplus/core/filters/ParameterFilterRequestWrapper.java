package cn.sgst.mywebplus.core.filters;


import cn.hutool.core.util.StrUtil;
import javax.servlet.http.HttpServletRequest;

/**
 * 去除两端空格包装
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/4/7 9:21
 */
public class ParameterFilterRequestWrapper extends AbstractFilterRequestWrapper {

    public ParameterFilterRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected String filter(String value) {
        return StrUtil.trim(value);
    }
}
