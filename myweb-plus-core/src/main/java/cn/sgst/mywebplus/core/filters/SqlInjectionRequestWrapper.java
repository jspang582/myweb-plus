package cn.sgst.mywebplus.core.filters;

import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.util.SqlInjectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.ValueFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *     去除sql注入关键词
 * </p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/10 10:01
 */
@Slf4j
public class SqlInjectionRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>(); // url参数
    private  byte[] body; // 用于保存读取body中数据

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public SqlInjectionRequestWrapper(HttpServletRequest request) throws IOException{
        super(request);
        Map<String, String[]> requestMap = request.getParameterMap();
        this.params.putAll(requestMap);
        this.modifyParameterValues();
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    private void modifyParameterValues() {
        Set<String> set =params.keySet();
        Iterator<String> it=set.iterator();
        while(it.hasNext()){
            String key= it.next();
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = SqlInjectionUtil.filterSql(values[i]);
            }
            params.put(key, values);
        }
    }


    @Override
    public String[] getParameterValues(String name) {
       return params.get(name);
    }


    @Override
    public String getParameter(String name) {
        String[]values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }


    @Override
    public Map<String, String[]> getParameterMap() {
       return params;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        String bodyStr = new String(body, Charset.forName("utf-8"));
        if(StrUtil.isNotEmpty(bodyStr)) {
            try {
                Object object = JSON.parse(bodyStr);
                // 对值进行过滤,如果对象里有对象,会进行递归
                bodyStr = JSON.toJSONString(object, new ValueFilter() {
                    @Override
                    public Object process(Object object, String name, Object value) {
                        if(value instanceof String){
                            value = SqlInjectionUtil.filterSql((String)value);
                        }
                        if(value instanceof JSONArray){
                            JSONArray array = (JSONArray) value;
                            for (int i = 0; i < array.size(); i++) {
                                Object element = array.get(i);
                                if(element instanceof String){
                                    array.set(i,SqlInjectionUtil.filterSql((String)element));
                                }
                            }
                        }
                        return value;
                    }
                });

            }catch (Exception e){
               log.error(e.getMessage(),e);
            }
        }

        final ByteArrayInputStream bis = new ByteArrayInputStream(bodyStr.getBytes("utf-8"));
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
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





}
