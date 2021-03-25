package cn.sgst.mywebplus.core.validation;


import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * 基于Group组的违反约束过滤器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/24 9:57
 */
public abstract class AbstractGroupConstraintViolationFilter implements ConstraintViolationClassBasedFilter  {


    /**
     * 如果过滤Class是groups中的某一个类的超类或超接口,则满足过滤
     *
     * @param constraintViolation 违反约束
     * @return true/false
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean accept(ConstraintViolation constraintViolation) {
        Set<Class<?>> groups = constraintViolation.getConstraintDescriptor().getGroups();
        for (Class<?> group : groups) {
            if (acceptClass().isAssignableFrom(group)) {
                return true;
            }
        }
        return false;
    }
}
