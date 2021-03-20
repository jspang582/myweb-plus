package cn.sgst.mywebplus.core.validation;

import cn.hutool.core.collection.CollUtil;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 违反约束Required处理器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/19 23:43
 */
public interface RequiredViolationProcessor {

    /**
     * 判断违反约束中是否包含Required
     *
     * @param constraintViolation 违反约束
     * @return boolean
     */
    boolean containsRequired(@NonNull ConstraintViolation constraintViolation);

    /**
     * 判断违反约束中是否包含Required
     *
     * @param constraintViolations 违反约束集合
     * @return boolean
     */
    default boolean containsRequired(@NonNull Collection<ConstraintViolation> constraintViolations) {
        Assert.notNull(constraintViolations, "constraintViolations must not be null");
        return countRequired(constraintViolations) > 0;
    }


    /**
     * 从违反约束中过滤出包含Required的约束
     *
     * @param constraintViolations 违反约束集合
     * @param <T>                  泛型
     * @return boolean
     */
    default <T extends ConstraintViolation> Collection<T> filterRequired(@NonNull Collection<T> constraintViolations) {
        Assert.notNull(constraintViolations, "constraintViolations must not be null");
        return constraintViolations.stream().filter(this::containsRequired).collect(Collectors.toList());
    }

    /**
     * 从违反约束中过滤出不包含Required的约束
     *
     * @param constraintViolations 违反约束集合
     * @param <T>                  泛型
     * @return boolean
     */
    default <T extends ConstraintViolation> Collection<T> filterNotRequired(@NonNull Collection<T> constraintViolations) {
        Assert.notNull(constraintViolations, "constraintViolations must not be null");
        return CollUtil.subtract(constraintViolations, filterRequired(constraintViolations));
    }

    /**
     * 统计违反约束中包含Required的约束的个数
     *
     * @param constraintViolations 违反约束集合
     * @return boolean
     */
    default long countRequired(@NonNull Collection<ConstraintViolation> constraintViolations) {
        Assert.notNull(constraintViolations, "constraintViolations must not be null");
        return constraintViolations.stream().filter(this::containsRequired).count();
    }


    /**
     * 判断违反约束中是否都包含Required
     * 集合为空时也返回true
     *
     * @param constraintViolations 违反约束集合
     * @return boolean
     */
    default boolean allRequired(@NonNull Collection<ConstraintViolation> constraintViolations) {
        Assert.notNull(constraintViolations, "constraintViolations must not be null");
        return countRequired(constraintViolations) == constraintViolations.size();
    }

}
