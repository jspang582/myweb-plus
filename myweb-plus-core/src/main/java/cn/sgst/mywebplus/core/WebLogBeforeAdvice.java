package cn.sgst.mywebplus.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 记录请求信息的advice
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/21 10:32
 */
@Slf4j
public class WebLogBeforeAdvice implements MethodBeforeAdvice {


    @Override
    public void before(Method method, Object[] args, Object target) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("请求地址 : " + request.getRequestURL().toString());
        log.info("HTTP METHOD : " + request.getMethod());
        log.info("CLASS_METHOD : " + method.getDeclaringClass().getName() + "."
                + method.getName());
        log.info("参数 : " + Arrays.toString(args));
    }
}
