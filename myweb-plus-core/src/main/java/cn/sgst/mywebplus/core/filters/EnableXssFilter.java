package cn.sgst.mywebplus.core.filters;

import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * 开启xss过滤
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/9/16 15:22
 * @see cn.hutool.http.HTMLFilter
 * @see XssFilterRegistrar
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(XssFilterRegistrar.class)
public @interface EnableXssFilter {

    /**
     * 需要拦截的请求,支持通配符
     */
    String[] includeUrlPatterns() default {"/*"};

    /**
     * 不需要拦截的请求,支持通配符
     */
    String[] excludeUrlPatterns() default {};

    /**
     * 排序
     */
    int order() default Ordered.HIGHEST_PRECEDENCE + 10;

    /**
     * set of allowed html elements, along with allowed attributes for each element
     **/
    VAllow[] vAllows() default {
            @VAllow(tag = "a", attrs = {"href", "target"}),
            @VAllow(tag = "img", attrs = {"src", "width", "height", "alt"}),
            @VAllow(tag = "b"),
            @VAllow(tag = "strong"),
            @VAllow(tag = "i"),
            @VAllow(tag = "em")
    };

    /**
     * html elements which must always be self-closing (e.g. "&lt;img /&gt;")
     **/
    String[] vSelfClosingTags() default {"img"};

    /**
     * html elements which must always have separate opening and closing tags (e.g. "&lt;b&gt;&lt;/b&gt;")
     **/
    String[] vNeedClosingTags() default {"a", "b", "strong", "i", "em"};

    /**
     * set of disallowed html elements
     **/
    String[] vDisallowed() default {};

    /**
     * attributes which should be checked for valid protocols
     **/
    String[] vProtocolAtts() default {"src", "href"};

    /**
     * allowed protocols
     **/
    String[] vAllowedProtocols() default {"http", "mailto", "https"};

    /**
     * tags which should be removed if they contain no content (e.g. "&lt;b&gt;&lt;/b&gt;" or "&lt;b /&gt;")
     **/
    String[] vRemoveBlanks() default {"a", "b", "strong", "i", "em"};

    /**
     * entities allowed within html markup
     **/
    String[] vAllowedEntities() default {"amp", "gt", "lt", "quot"};

    /**
     * flag determining whether comments are allowed in input String.
     */
    boolean stripComment() default true;

    boolean encodeQuotes() default true;

    /**
     * flag determining whether to try to make tags when presented with "unbalanced" angle brackets (e.g. "&lt;b text &lt;/b&gt;" becomes "&lt;b&gt; text &lt;/g&gt;").
     * If set to false, unbalanced angle brackets will be
     * html escaped.
     */
    boolean alwaysMakeTags() default true;

    /**
     * set of allowed html elements, along with allowed attributes for each element
     **/
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface VAllow {
        String tag();

        String[] attrs() default {};
    }
}
