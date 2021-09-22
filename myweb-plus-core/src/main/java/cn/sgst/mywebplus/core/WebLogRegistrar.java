package cn.sgst.mywebplus.core;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;

/**
 * 注册advice和自动代理creator
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/21 10:45
 */
public class WebLogRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        registerBeforeAdvice(registry);
        final List<String> interceptorNames = new ArrayList<>();
        interceptorNames.add(Introspector.decapitalize(WebLogBeforeAdvice.class.getSimpleName()));

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EnableWebLog.class.getName()));
        boolean showExecutionTime = attributes.getBoolean("showExecutionTime");
        boolean showReturnVal = attributes.getBoolean("showReturnVal");

        if (showExecutionTime) {
            registerAroundAdvice(registry);
            interceptorNames.add(Introspector.decapitalize(WebLogAroundAspect.class.getSimpleName()));

        }
        if (showReturnVal) {
            registerAfterAdvice(registry);
            interceptorNames.add(Introspector.decapitalize(WebLogAfterAdvice.class.getSimpleName()));
        }
        registerAutoProxyCreator(attributes, registry, interceptorNames);
    }


    private void registerAutoProxyCreator(AnnotationAttributes attributes, BeanDefinitionRegistry registry, List<String> interceptorNames) {
        String[] beanNames = attributes.getStringArray("beanNames");
        int order = attributes.getNumber("order").intValue();
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(BeanNameAutoProxyCreator.class);
        builder.addPropertyValue("beanNames", beanNames);
        builder.addPropertyValue("interceptorNames", ArrayUtil.toArray(interceptorNames, String.class));
        builder.addPropertyValue("order", order);
        builder.addPropertyValue("proxyTargetClass", true);
        registry.registerBeanDefinition(Introspector.decapitalize(BeanNameAutoProxyCreator.class.getSimpleName()), builder.getBeanDefinition());
    }

    private void registerBeforeAdvice(BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(WebLogBeforeAdvice.class);
        registry.registerBeanDefinition(Introspector.decapitalize(WebLogBeforeAdvice.class.getSimpleName()), builder.getBeanDefinition());
    }


    private void registerAfterAdvice(BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(WebLogAfterAdvice.class);
        registry.registerBeanDefinition(Introspector.decapitalize(WebLogAfterAdvice.class.getSimpleName()), builder.getBeanDefinition());
    }

    private void registerAroundAdvice(BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(WebLogAroundAspect.class);
        registry.registerBeanDefinition(Introspector.decapitalize(WebLogAroundAspect.class.getSimpleName()), builder.getBeanDefinition());
    }
}
