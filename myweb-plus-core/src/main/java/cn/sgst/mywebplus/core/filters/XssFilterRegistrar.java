package cn.sgst.mywebplus.core.filters;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.beans.Introspector;

/**
 * xssFilter注册器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 15:23
 */
public class XssFilterRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EnableXssFilter.class.getName()));

        String[] includeUrlPatterns = attributes.getStringArray("includeUrlPatterns");
        String[] excludeUrlPatterns = attributes.getStringArray("excludeUrlPatterns");
        Number order = attributes.getNumber("order");

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(FilterRegistrationFactoryBean.class);
        XssFilter xssFilter = new XssFilter();
        xssFilter.setExcludeUrlPatterns(excludeUrlPatterns);
        builder.addConstructorArgValue(xssFilter);
        builder.addPropertyValue("urlPatterns",includeUrlPatterns);
        builder.addPropertyValue("order",order.intValue());

        registry.registerBeanDefinition(Introspector.decapitalize(XssFilter.class.getSimpleName()),builder.getBeanDefinition());
    }
}
