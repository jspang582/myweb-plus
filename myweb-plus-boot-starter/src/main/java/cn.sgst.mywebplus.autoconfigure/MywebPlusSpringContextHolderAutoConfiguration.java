package cn.sgst.mywebplus.autoconfigure;

import cn.sgst.mywebplus.core.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring容器持有者自动装配
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 15:55
 */
@Configuration
public class MywebPlusSpringContextHolderAutoConfiguration {

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
