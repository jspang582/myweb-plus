package cn.sgst.mywebplus.core.validation.constraintvalidators;


import cn.sgst.mywebplus.core.validation.constraints.StartWith;

/**
 * {@link StartWith} 校验器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/30 16:20
 */
public class StartWithValidator extends AbstractEmptyBasedValidator<StartWith,String> {

    private String prefix;

    @Override
    public void initialize(StartWith constraintAnnotation) {
        prefix = constraintAnnotation.prefix();
    }

    @Override
    protected boolean isValid(String value) {
       return value.startsWith(prefix);
    }
}
