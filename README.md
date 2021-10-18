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