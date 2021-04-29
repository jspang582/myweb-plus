package cn.sgst.mywebplus.extensions.thymeleaf.dialog;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * 自定义方言
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/11/3 17:48
 */
public class MyDialect extends AbstractDialect implements IExpressionObjectDialect {


    private final IExpressionObjectFactory MY_EXPRESSION_OBJECTS_FACTORY = new MyDialectExpressionFactory();

    private static final String NAME = "mydialect";

    public MyDialect() {
        super(NAME);
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return MY_EXPRESSION_OBJECTS_FACTORY;
    }
}
