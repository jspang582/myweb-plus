package cn.sgst.mywebplus.core.validation.constraintvalidators;


import cn.sgst.mywebplus.core.validation.constraints.EndWith;

/**
 * {@link EndWith} 校验器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/30 16:24
 */
public class EndWithValidator extends AbstractEmptyBasedValidator<EndWith,String> {

    private String suffix;

    @Override
    public void initialize(EndWith constraintAnnotation) {
        suffix = constraintAnnotation.suffix();
    }

    @Override
    protected boolean isValid(String value) {
        return value.endsWith(suffix);
    }
}
