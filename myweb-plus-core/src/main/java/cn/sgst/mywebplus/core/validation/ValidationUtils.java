package cn.sgst.mywebplus.core.validation;


import cn.hutool.core.collection.CollUtil;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * java bean 校验工具类，此工具类基于validation-api（jakarta.validation-api）封装
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/5 16:45
 */
public class ValidationUtils {
    /**
     * 默认{@link Validator} 对象
     */
    private static final Validator validator;

    /**
     * spi filter
     */
    private static final ConstraintViolationFilter defaultFilter;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        defaultFilter = putSpiFilter();
    }



    /**
     * 获取原生{@link Validator} 对象
     *
     * @return {@link Validator} 对象
     */
    public static Validator getValidator() {
        return validator;
    }

    /**
     * 校验对象
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     * @return {@link Set}
     */
    public static <T> Set<ConstraintViolation<T>> validate(T bean, Class<?>... groups) {
        return validator.validate(bean, groups);
    }

    /**
     * 校验bean的某一个属性
     *
     * @param <T>          Bean类型
     * @param bean         bean
     * @param propertyName 属性名称
     * @param groups       验证分组
     * @return {@link Set}
     */
    public static <T> Set<ConstraintViolation<T>> validateProperty(T bean, String propertyName, Class<?>... groups) {
        return validator.validateProperty(bean, propertyName, groups);
    }

    /**
     * 校验对象并过滤
     *
     * @param bean            bean
     * @param violationFilter 过滤规则
     * @param groups          验证分组
     * @return {@link ConstraintViolationFilterStat}
     */
    public static <T> ConstraintViolationFilterStat validateFilter(T bean, ConstraintViolationFilter violationFilter, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean, groups);
        return filter(new HashSet<>(violations), violationFilter);
    }

    /**
     * 校验对象并过滤
     *
     * @param bean            bean
     * @param groups          验证分组
     * @return {@link ConstraintViolationFilterStat}
     */
    public static <T> ConstraintViolationFilterStat validateFilter(T bean, Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean, groups);
        return filter(new HashSet<>(violations));
    }


    /**
     * 过滤违反约束
     *
     * @param violations      违反约束
     * @param violationFilter 过滤规则
     * @return {@link ConstraintViolationFilterStat}
     */
    public static ConstraintViolationFilterStat filter(Set<ConstraintViolation<?>> violations, ConstraintViolationFilter violationFilter) {
        Set<ConstraintViolation<?>> includeViolations = violations.stream().filter(violationFilter::accept).collect(Collectors.toSet());
        Set<ConstraintViolation<?>> excludeViolations = violations.stream().filter(violation -> !violationFilter.accept(violation)).collect(Collectors.toSet());
        ConstraintViolationFilterStat stat = new ConstraintViolationFilterStat();
        stat.setIncludes(includeViolations);
        stat.setExcludes(excludeViolations);
        return stat;
    }

    /**
     * 过滤违反约束
     *
     * @param violations      违反约束
     * @return {@link ConstraintViolationFilterStat}
     */
    public static ConstraintViolationFilterStat filter(Set<ConstraintViolation<?>> violations) {
        return filter(violations,defaultFilter);
    }

    /**
     * 校验对象
     *
     * @param <T>    Bean类型
     * @param bean   bean
     * @param groups 校验组
     * @return {@link BeanValidationResult}
     */
    public static <T> BeanValidationResult warpValidate(T bean, Class<?>... groups) {
        return warpBeanValidationResult(new HashSet<>(validate(bean, groups)));
    }

    /**
     * 校验bean的某一个属性
     *
     * @param <T>          bean类型
     * @param bean         bean
     * @param propertyName 属性名称
     * @param groups       验证分组
     * @return {@link BeanValidationResult}
     */
    public static <T> BeanValidationResult warpValidateProperty(T bean, String propertyName, Class<?>... groups) {
        return warpBeanValidationResult(new HashSet<>(validateProperty(bean, propertyName, groups)));
    }

    /**
     * 包装校验结果
     *
     * @param constraintViolations 校验结果集
     * @return {@link BeanValidationResult}
     */
    public static BeanValidationResult warpBeanValidationResult(Set<ConstraintViolation<?>> constraintViolations) {
        BeanValidationResult result = new BeanValidationResult(constraintViolations.isEmpty());
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            BeanValidationResult.ErrorMessage errorMessage = new BeanValidationResult.ErrorMessage();
            errorMessage.setPropertyName(constraintViolation.getPropertyPath().toString());
            errorMessage.setMessage(constraintViolation.getMessage());
            errorMessage.setValue(constraintViolation.getInvalidValue());
            result.addErrorMessage(errorMessage);
        }
        return result;
    }


    /**
     * 包装校验结果
     *
     * @param bindingResult 校验结果
     * @return {@link BeanValidationResult}
     */
    public static BeanValidationResult warpBeanValidationResult(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        return warpBeanValidationResult(errors);
    }


    /**
     * 包装校验结果
     *
     * @param errors 错误
     * @return {@link BeanValidationResult}
     */
    public static BeanValidationResult warpBeanValidationResult(List<ObjectError> errors) {
        BeanValidationResult result = new BeanValidationResult(CollUtil.isEmpty(errors));
        for (ObjectError error : errors) {
            BeanValidationResult.ErrorMessage errorMessage = new BeanValidationResult.ErrorMessage();
            errorMessage.setMessage(error.getDefaultMessage());
            if (error instanceof FieldError) {
                errorMessage.setPropertyName(((FieldError) error).getField());
                errorMessage.setValue(((FieldError) error).getRejectedValue());
            }
            result.addErrorMessage(errorMessage);
        }
        return result;
    }

    /**
     * 加载spi过滤器
     * @return @return {@link ConstraintViolationFilter}
     */
    private static ConstraintViolationFilter putSpiFilter() {
        ServiceLoader<ConstraintViolationFilter> serviceLoader = ServiceLoader.load(ConstraintViolationFilter.class);
        return serviceLoader.iterator().next();
    }
}
