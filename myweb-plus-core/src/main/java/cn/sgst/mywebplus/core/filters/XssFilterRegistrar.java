package cn.sgst.mywebplus.core.filters;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.beans.Introspector;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Number order = attributes.getNumber("order");

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(FilterRegistrationFactoryBean.class);
        builder.addConstructorArgValue(createXssFilterInstance(attributes));
        builder.addPropertyValue("urlPatterns",includeUrlPatterns);
        builder.addPropertyValue("order",order.intValue());

        registry.registerBeanDefinition(Introspector.decapitalize(XssFilter.class.getSimpleName()),builder.getBeanDefinition());
    }


    private static XssFilter createXssFilterInstance(AnnotationAttributes attributes) {
        XssFilter filter = new XssFilter();
        final Map<String, Object> conf = new HashMap<>();
        filter.setConf(conf);

        String[] excludeUrlPatterns = attributes.getStringArray("excludeUrlPatterns");
        filter.setExcludeUrlPatterns(excludeUrlPatterns);

        AnnotationAttributes[] vAllows = attributes.getAnnotationArray("vAllows");
        Map<String,List<String>> vAllowed = new HashMap<>();

        for (AnnotationAttributes vAllow : vAllows) {
            vAllowed.put(vAllow.getString("tag"),Arrays.asList(vAllow.getStringArray("attrs")));
        }
        conf.put("vAllowed",vAllowed);

        attributes.remove("vAllows");
        conf.putAll(attributes);

        return filter;
    }
}
