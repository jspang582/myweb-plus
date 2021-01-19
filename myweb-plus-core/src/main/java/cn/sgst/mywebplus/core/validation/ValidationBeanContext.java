package cn.sgst.mywebplus.core.validation;

import cn.sgst.mywebplus.core.SpringContextHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 临时存放被验证的bean对象
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/2/16 12:35
 */
@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST)
public class ValidationBeanContext {


    private Object bean = null;

    public void set(Object obj) {
        this.bean = obj;
    }
    @SuppressWarnings("unchecked")
    public <T> T get() {
        return (T) bean;
    }

    public static ValidationBeanContext create() {
        return SpringContextHolder.getBean(ValidationBeanContext.class);
    }
}
