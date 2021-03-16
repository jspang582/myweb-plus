package cn.sgst.mywebplus.core.validation;

import cn.hutool.core.collection.CollUtil;
import cn.sgst.mywebplus.core.validation.group.Required;
import org.springframework.util.Assert;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 基于Required组的校验工具类
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/10/18 10:19
 */
public class ValidationRequiredUtils {


    /**
     * 校验ObjectError集合中是否有Required错误
     */
    public static <T extends ObjectError> boolean hasRequiredError(@NotNull Collection<T> errors) {
        Assert.notNull(errors,"errors must not be null");
        return countRequiredErrors(errors) > 0;
    }


    /**
     * 是否都是Required错误
     * 集合为空时 return true
     */
    public static <T extends ObjectError>  boolean isAllRequiredErrors(@NotNull Collection<T> errors) {
        Assert.notNull(errors,"errors must not be null");
        return countRequiredErrors(errors) == errors.size();
    }

    /**
     * Required错误的个数
     */

    public static <T extends ObjectError>  long countRequiredErrors(@NotNull Collection<T> errors) {
        Assert.notNull(errors,"errors must not be null");
       return filterRequiredErrors(errors).size();
    }

    /**
     * 从ObjectError集合中筛选出Required的Error
     */
    @SuppressWarnings("all")
    public static <T extends ObjectError> Collection<T> filterRequiredErrors(@NotNull Collection<T> errors) {
        Assert.notNull(errors,"errors must not be null");
        return errors.stream().filter(error -> {
                ConstraintViolation violation = ValidationViolationUtils.getViolationFieldValue(error);
                Set<Class> groups = violation.getConstraintDescriptor().getGroups();
                for (Class group : groups) {
                    if(Required.class.isAssignableFrom(group)) {
                        return true;
                    }
                }
                return false;
        }).collect(Collectors.toList());
    }

    /**
     * 从ObjectError集合中移出Required的Error
     */
    public static <T extends ObjectError> Collection<T> removeRequiredErrors(@NotNull Collection<T> errors) {
        Assert.notNull(errors,"errors must not be null");
        return CollUtil.disjunction(errors,filterRequiredErrors(errors));
    }
}
