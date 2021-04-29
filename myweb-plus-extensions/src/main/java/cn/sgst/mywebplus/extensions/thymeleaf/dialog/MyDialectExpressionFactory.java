package cn.sgst.mywebplus.extensions.thymeleaf.dialog;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义方言实现工厂
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/11/3 17:50
 */
public class MyDialectExpressionFactory implements IExpressionObjectFactory {

    /**
     * 字符串工具类
     */
    private static final String STR_UTIL_EVALUATION_VARIABLE_NAME = "strUtil";

    /**
     * 集合工具类
     */
    private static final String COLL_UTIL_EVALUATION_VARIABLE_NAME = "collUtil";

    /**
     * 数组工具类
     */
    private static final String ARRAY_UTIL_EVALUATION_VARIABLE_NAME = "arrayUtil";

    /**
     * 时间工具类
     */
    private static final String DATE_UTIL_EVALUATION_VARIABLE_NAME = "dateUtil";

    /**
     * 数字工具类
     */
    private static final String NUMBER_UTIL_EVALUATION_VARIABLE_NAME = "numberUtil";


    private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(
            new HashSet<>(Arrays.asList(
                    STR_UTIL_EVALUATION_VARIABLE_NAME,
                    COLL_UTIL_EVALUATION_VARIABLE_NAME,
                    ARRAY_UTIL_EVALUATION_VARIABLE_NAME,
                    DATE_UTIL_EVALUATION_VARIABLE_NAME,
                    NUMBER_UTIL_EVALUATION_VARIABLE_NAME
            )));

    /**
     * 返回该工厂类能创建的工具类对象的集合
     */
    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    /**
     * 根据表达式的名称,创建工具类对象
     */
    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {
        if (STR_UTIL_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new StrUtil();
        }
        if (COLL_UTIL_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new CollUtil();
        }
        if (ARRAY_UTIL_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new ArrayUtil();
        }
        if (DATE_UTIL_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new DateUtil();
        }
        if(NUMBER_UTIL_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
            return new NumberUtil();
        }
        return null;
    }

    /**
     * 返回该工具对象是否可缓存
     */
    @Override
    public boolean isCacheable(String expressionObjectName) {
        return true;
    }
}
