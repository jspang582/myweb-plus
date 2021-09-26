package cn.sgst.mywebplus.core.filters;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.util.IOUtils;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 对值进行过滤的请求处理器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/20 0:35
 */
@Slf4j
public class FilterRequestWrapper extends HttpServletRequestWrapper {

    private final ValueFilter valueFilter;

    public FilterRequestWrapper(HttpServletRequest request, ValueFilter valueFilter) {
        super(request);
        this.valueFilter = valueFilter;
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
        String body = IoUtil.read(super.getInputStream(), IOUtils.UTF8);
        if (StringUtils.isEmpty(body)) {
            return super.getInputStream();
        }
        // 修复bug : List<String>类型会转换失败
        try {
            Object object = JSON.parse(body);
            // 对值进行过滤,如果对象里有对象,会进行递归
            body = JSON.toJSONString(object, (com.alibaba.fastjson.serializer.ValueFilter) (obj, name, value) -> {
                if (value instanceof String) {
                    value = valueFilter.process((String) value);
                }
                if (value instanceof JSONArray) {
                    JSONArray array = (JSONArray) value;
                    for (int i = 0; i < array.size(); i++) {
                        Object element = array.get(i);
                        if (element instanceof String) {
                            array.set(i, valueFilter.process((String) element));
                        }
                    }
                }
                return value;
            });

        } catch (Exception e) {
            log.error("解析RequestBody内容出错", e);
        }

        final ByteArrayInputStream bis = new ByteArrayInputStream(body.getBytes(IOUtils.UTF8));
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
     * 重写getParameterMap
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        // 避免直接修改原map
        HashMap<String, String[]> params = new HashMap<>(super.getParameterMap());
        Set<String> set = params.keySet();
        for (String key : set) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = valueFilter.process(values[i]);
            }
            params.put(key, values);
        }
        return params;
    }

    /**
     * 重写getParameterValues
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        for (int i = 0; i < values.length; i++) {
            values[i] = valueFilter.process(values[i]);
        }
        return values;
    }

    /**
     * 重写getParameter
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return valueFilter.process(value);
    }

}
