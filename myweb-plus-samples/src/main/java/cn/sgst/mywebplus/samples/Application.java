package cn.sgst.mywebplus.samples;

import cn.sgst.mywebplus.core.EnableWebLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 17:07
 */
@SpringBootApplication
@EnableWebLog
public class Application {

    public static void main(String[] args) {
       SpringApplication.run(Application.class, args);
    }
}
