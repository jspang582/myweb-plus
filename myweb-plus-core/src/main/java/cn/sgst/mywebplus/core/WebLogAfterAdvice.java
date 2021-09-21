package cn.sgst.mywebplus.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * 记录返回值的advice
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/21 10:37
 */
@Slf4j
public class WebLogAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) {
        log.debug("返回值 : " + returnValue);
    }
}
