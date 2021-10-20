package cn.sgst.mywebplus.samples.enumsync;

import cn.sgst.mywebplus.core.dict.DictDetailsProvider;
import cn.sgst.mywebplus.core.dict.DictEnumSyncCommand;
import cn.sgst.mywebplus.core.dict.DictEnumSyncFramework;
import cn.sgst.mywebplus.core.dict.GenericSyncProcessor;
import cn.sgst.mywebplus.core.enums.EnumUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 整合spring测试
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/10/20 23:22
 */
public class SpringMain {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        applicationContext.getBean(DictEnumSyncCommand.class).sync("sex_type");
        System.out.println(EnumUtils.getValues(EnumFactory.SexEnum.class));
    }


    @Configuration
    public static class AppConfig {

        @Bean
        public DictDetailsProvider provider() {
            return new DefaultDictDetailsProvider();
        }

        @Bean
        public DictEnumSyncFramework framework() {
            return DictEnumSyncFramework
                    .builder()
                    .provider(provider())
                    .register("sex_type", EnumFactory.SexEnum.class, new GenericSyncProcessor())
                    .build();
        }
    }
}
