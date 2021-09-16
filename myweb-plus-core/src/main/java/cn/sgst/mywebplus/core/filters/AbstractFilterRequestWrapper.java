package cn.sgst.mywebplus.core.filters;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.ValueFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 请求包装抽象类
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 14:53
 */
@Slf4j
public abstract class AbstractFilterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();

    public AbstractFilterRequestWrapper(HttpServletRequest request) {
        super(request);
        this.params.putAll(request.getParameterMap());
    }

    /**
     * 重写getInputStream方法  @requestBody类型的请求参数必须通过流才能获取到值
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
        if (header == null || (!header.equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE) && !header.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE))) {
            return super.getInputStream();
        }
        //为空，直接返回
        String body = IoUtil.read(super.getInputStream(), Charset.forName("utf-8"));
        if (StringUtils.isEmpty(body)) {
            return super.getInputStream();
        }
        // 修复bug : List<String>类型会转换失败
        try {
            Object object = JSON.parse(body);
            // 对值进行过滤,如果对象里有对象,会进行递归
            body = JSON.toJSONString(object, new ValueFilter() {
                @Override
                public Object process(Object object, String name, Object value) {
                    if (value instanceof String) {
                        value = filter((String) value);
                    }
                    if (value instanceof JSONArray) {
                        JSONArray array = (JSONArray) value;
                        for (int i = 0; i < array.size(); i++) {
                            Object element = array.get(i);
                            if (element instanceof String) {
                                array.set(i, filter((String) element));
                            }
                        }
                    }
                    return value;
                }
            });

        } catch (Exception e) {
            log.error("解析RequestBody内容出错", e);
        }

        final ByteArrayInputStream bis = new ByteArrayInputStream(body.getBytes("utf-8"));
        return new ServletInputStream() {

            @Override
            public int read() {
                return bis.read();
            }

            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    /**
     * 重写getParameter 参数从当前类中的map获取
     */
    @Override
    public String getParameter(String name) {
        String[] values = getModifiedParameterValues().get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return getModifiedParameterValues();
    }

    /**
     * 重写getParameterValues
     */
    @Override
    public String[] getParameterValues(String name) {
        //同上
        return getModifiedParameterValues().get(name);
    }


    /**
     * 获取修改后的参数
     */
    private Map<String,String[]> getModifiedParameterValues() {
        Set<String> set = params.keySet();
        for (String key : set) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = filter(values[i]);
            }
            params.put(key, values);
        }
        return params;
    }

    /**
     * 过滤字符串
     *
     * @param value 原字符串
     * @return 新字符串
     */
    protected abstract String filter(String value);

}
