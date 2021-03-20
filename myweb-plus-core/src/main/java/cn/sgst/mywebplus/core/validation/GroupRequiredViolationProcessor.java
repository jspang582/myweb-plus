package cn.sgst.mywebplus.core.validation;

import cn.sgst.mywebplus.core.validation.group.Required;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Group Required 违反约束处理器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/19 23:50
 */
public class GroupRequiredViolationProcessor implements RequiredViolationProcessor {


    @SuppressWarnings("unchecked")
    @Override
    public boolean containsRequired(@NonNull ConstraintViolation constraintViolation) {
        Assert.notNull(constraintViolation, "constraintViolation must not be null");
        Set<Class<?>> groups = constraintViolation.getConstraintDescriptor().getGroups();
        for (Class<?> group : groups) {
            if (Required.class.isAssignableFrom(group)) {
                return true;
            }
        }
        return false;
    }


}
