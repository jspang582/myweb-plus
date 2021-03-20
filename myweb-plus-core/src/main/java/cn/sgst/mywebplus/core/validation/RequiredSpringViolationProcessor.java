package cn.sgst.mywebplus.core.validation;

import cn.hutool.core.collection.CollUtil;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.stream.Collectors;


/**
 * Spring违反约束Required处理器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/20 0:42
 */
public interface RequiredSpringViolationProcessor extends RequiredViolationProcessor {


    /**
     * 判断校验错误信息是否包含Required
     *
     * @param objectError 校验错误信息
     * @return boolean
     */
    default boolean containsRequiredError(@NonNull ObjectError objectError) {
        Assert.notNull(objectError, "objectError must not be null");
        ConstraintViolation constraintViolation = SpringViolationErrorReflectFactory.getViolation(objectError);
        return containsRequired(constraintViolation);
    }

    /**
     * 判断校验错误信息是否包含Required
     *
     * @param objectErrors 校验错误信息集合
     * @return boolean
     */
    default boolean containsRequiredError(@NonNull Collection<ObjectError> objectErrors) {
        Assert.notNull(objectErrors, "objectErrors must not be null");
        return countRequiredError(objectErrors) > 0;
    }


    /**
     * 从校验错误信息中过滤出包含Required的信息
     *
     * @param objectErrors 校验错误信息集合
     * @param <T>          泛型
     * @return boolean
     */
    default <T extends ObjectError> Collection<T> filterRequiredError(@NonNull Collection<T> objectErrors) {
        Assert.notNull(objectErrors, "objectErrors must not be null");
        return objectErrors.stream().filter(this::containsRequiredError).collect(Collectors.toList());
    }

    /**
     * 从校验错误信息中过滤出不包含Required的信息
     *
     * @param objectErrors 校验错误信息集合
     * @param <T>          泛型
     * @return boolean
     */
    default <T extends ObjectError> Collection<T> filterNotRequiredError(@NonNull Collection<T> objectErrors) {
        Assert.notNull(objectErrors, "objectErrors must not be null");
        return CollUtil.subtract(objectErrors, filterRequiredError(objectErrors));
    }

    /**
     * 统计校验错误信息中包含Required的信息的个数
     *
     * @param objectErrors 校验错误信息集合
     * @return boolean
     */
    default long countRequiredError(@NonNull Collection<ObjectError> objectErrors) {
        Assert.notNull(objectErrors, "objectErrors must not be null");
        return objectErrors.stream().filter(this::containsRequiredError).count();
    }


    /**
     * 判断校验错误信息中是否都包含Required
     * 集合为空时也返回true
     *
     * @param objectErrors 校验错误信息集合
     * @return boolean
     */
    default boolean allRequiredError(@NonNull Collection<ObjectError> objectErrors) {
        Assert.notNull(objectErrors, "objectErrors must not be null");
        return countRequiredError(objectErrors) == objectErrors.size();
    }
}
