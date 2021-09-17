package cn.sgst.mywebplus.core.filters;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.beans.Introspector;

/**
 * 注册去除参数空格过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 17:43
 */
public class TrimFilterRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EnableTrimFilter.class.getName()));

        String[] includeUrlPatterns = attributes.getStringArray("includeUrlPatterns");
        String[] excludeUrlPatterns = attributes.getStringArray("excludeUrlPatterns");

        Number order = attributes.getNumber("order");

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(FilterRegistrationFactoryBean.class);
        ParameterFilter filter = new ParameterFilter();
        filter.setExcludeUrlPatterns(excludeUrlPatterns);
        builder.addConstructorArgValue(filter);
        builder.addPropertyValue("urlPatterns", includeUrlPatterns);
        builder.addPropertyValue("order", order.intValue());

        registry.registerBeanDefinition(Introspector.decapitalize(ParameterFilter.class.getSimpleName()), builder.getBeanDefinition());
    }
}
