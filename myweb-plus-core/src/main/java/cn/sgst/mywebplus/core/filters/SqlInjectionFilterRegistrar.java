package cn.sgst.mywebplus.core.filters;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.beans.Introspector;

/**
 * sql注入拦截器注册
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 16:56
 */
public class SqlInjectionFilterRegistrar implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EnableSqlInjectionFilter.class.getName()));

        String[] methods = attributes.getStringArray("methods");
        Number order = attributes.getNumber("order");

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(FilterRegistrationFactoryBean.class);
        SqlInjectionFilter filter = new SqlInjectionFilter();
        filter.setMethods(methods);
        builder.addConstructorArgValue(filter);
        builder.addPropertyValue("urlPatterns","/*");
        builder.addPropertyValue("order",order.intValue());

        registry.registerBeanDefinition(Introspector.decapitalize(SqlInjectionFilter.class.getSimpleName()),builder.getBeanDefinition());
    }
}
