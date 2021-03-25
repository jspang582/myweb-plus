package cn.sgst.mywebplus.core.validation;

/**
 * 基于Class的违反约束过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/25 9:18
 */
public interface ConstraintViolationClassBasedFilter extends ConstraintViolationFilter {


    /**
     * 需要过滤的Class
     *
     * @return {@link Class}
     */
    Class<?> acceptClass();

}
