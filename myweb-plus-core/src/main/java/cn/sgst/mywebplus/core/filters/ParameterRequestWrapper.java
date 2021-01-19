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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 去除两端空格包装
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/4/7 9:21
 */
@Slf4j
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String , String[]> params = new HashMap<>();
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public ParameterRequestWrapper(HttpServletRequest request) {
        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        Map<String, String[]> requestMap=request.getParameterMap();
        if(log.isDebugEnabled()){
            log.debug("转化前参数："+JSON.toJSONString(requestMap));
        }
        this.params.putAll(requestMap);
        this.modifyParameterValues();
        if(log.isDebugEnabled()){
            log.debug("转化后参数："+JSON.toJSONString(params));
        }
    }

    /**
     * 重写getInputStream方法  @requestBody类型的请求参数必须通过流才能获取到值
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(log.isDebugEnabled()){
            log.debug("接收@RequestBody参数");
        }

        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
        if(header == null || (!header.equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE) && !header.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) ){
            return super.getInputStream();
        }
        //为空，直接返回
        String body = IoUtil.read(super.getInputStream(), Charset.forName("utf-8"));
        if (StringUtils.isEmpty(body)) {
            return super.getInputStream();
        }
        if(log.isDebugEnabled()){
            log.debug("转化前参数："+body);
        }
        // 修复bug : List<String>类型会转换失败
        try {
            Object object = JSON.parse(body);
            // 对值进行过滤,如果对象里有对象,会进行递归
            body = JSON.toJSONString(object,new MyValueFilter());

            if(log.isDebugEnabled()){
                log.debug("转化后参数："+body);
            }
        }catch (Exception e){
            log.error("解析RequestBody内容出错",e);
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(body.getBytes("utf-8"));
        return new MyServletInputStream(bis);
    }

    /**
     * 将parameter的值去除空格后重写回去
     */
    private void modifyParameterValues(){
        Set<String> set =params.keySet();
        Iterator<String> it=set.iterator();
        while(it.hasNext()){
            String key= it.next();
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }
            params.put(key, values);
        }
    }


    /**
     * 重写getParameter 参数从当前类中的map获取
     */
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

    /**
     * 重写getParameterValues
     */
    @Override
    public String[] getParameterValues(String name) {//同上
        return params.get(name);
    }

    class MyServletInputStream extends ServletInputStream {
        private ByteArrayInputStream bis;
        MyServletInputStream(ByteArrayInputStream bis){
            this.bis=bis;
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
        public void setReadListener(ReadListener listener) {

        }
        @Override
        public int read(){
            return bis.read();
        }
    }


    class MyValueFilter implements ValueFilter {

        @Override
        public Object process(Object object, String name, Object value) {
            if(value instanceof String){
                value = ((String)value).trim();
            }
            if(value instanceof JSONArray){
                JSONArray array = (JSONArray) value;
                for (int i = 0; i < array.size(); i++) {
                    Object element = array.get(i);
                    if(element instanceof String){
                        array.set(i,((String)element).trim());
                    }


                }
            }
            return value;
        }
    }
}
