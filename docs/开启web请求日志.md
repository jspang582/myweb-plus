## 用来查看web应用中请求的参数、返回值、执行时间等

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WebLogRegistrar.class)
public @interface EnableWebLog {

    /**
     * 需要记录web日志的beanName,支持通配符
     */
    String[] beanNames() default {"*Controller"};


    /**
     * 是否显示执行时间
     */
    boolean showExecutionTime() default true;

    /**
     * 是否显示返回值
     */
    boolean showReturnVal() default true;

    /**
     * 执行顺序,数值越小越先执行
     */
    int order() default Ordered.LOWEST_PRECEDENCE;
}
```

## 使用
```java
@EnableWebLog(showReturnVal = false)
@Configuration
public class AppConfig {
}
```


