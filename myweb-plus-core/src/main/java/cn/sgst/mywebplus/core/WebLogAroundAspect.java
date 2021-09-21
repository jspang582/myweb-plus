package cn.sgst.mywebplus.core;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 记录耗时的advice
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/21 10:39
 */
@Slf4j
public class WebLogAroundAspect implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = invocation.proceed();
        log.info("耗时 : " + (System.currentTimeMillis() - startTime) + "(ms)");
        return ob;
    }
}
