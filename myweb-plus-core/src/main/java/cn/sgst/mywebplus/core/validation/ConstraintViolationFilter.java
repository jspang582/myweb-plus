package cn.sgst.mywebplus.core.validation;

import javax.validation.ConstraintViolation;

/**
 * 违反约束过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/24 9:38
 */
public interface ConstraintViolationFilter {


    /**
     * 是否允许通过过滤
     *
     * @param constraintViolation 违反约束
     * @return true/false
     */
    boolean accept(ConstraintViolation constraintViolation);
}
