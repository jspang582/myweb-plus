# myweb-plus
基于SpringBoot快速开发的web框架

## 快速上手
pom文件中引入依赖
```xml
<dependency>
    <groupId>cn.sgst</groupId>
    <artifactId>myweb-plus-boot-starter</artifactId>
    <version>myweb-plus-latest-version</version>
</dependency>
```

## 示例
### 开启xss过滤
```java
/**
 * xss配置
 */
@Configuration
@EnableXssFilter(excludeUrlPatterns = {"/assets/**"})
public static class XssConfig {
}
```

### 数据字典同步枚举
```java
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
```