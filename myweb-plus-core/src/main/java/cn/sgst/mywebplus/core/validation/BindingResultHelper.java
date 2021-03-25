package cn.sgst.mywebplus.core.validation;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * BindingResult帮助类
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/25 16:03
 * @see BindingResult
 * @see ConstraintViolationFilter
 * @see ValidationUtils
 */
public class BindingResultHelper {


    /**
     * spi filter
     */
    private static final ConstraintViolationFilter defaultFilter;

    static {
        defaultFilter = putSpiFilter();
    }

    /**
     * 过滤违反约束
     *
     * @param bindingResult 校验结果
     * @return {@link FilterViolationResult}
     */
    public static FilterViolationResult filterViolation(BindingResult bindingResult) {
        return filterViolation(bindingResult, defaultFilter);
    }

    /**
     * 过滤违反约束
     *
     * @param bindingResult   校验结果
     * @param violationFilter 过滤器
     * @return {@link FilterViolationResult}
     */
    public static FilterViolationResult filterViolation(BindingResult bindingResult, ConstraintViolationFilter violationFilter) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        Set<ConstraintViolation<?>> violations = errors.stream().map(SpringViolationErrorReflectFactory::getViolation).collect(Collectors.toSet());
        ConstraintViolationFilterStat stat = ValidationUtils.filter(violations, violationFilter);
        Set<ConstraintViolation<?>> excludes = stat.getExcludes();
        List<ObjectError> excludeErrors = errors.stream().filter(error -> {
            ConstraintViolation<?> violation = SpringViolationErrorReflectFactory.getViolation(error);
            return excludes.contains(violation);
        }).collect(Collectors.toList());
        List<ObjectError> includeErrors = CollUtil.subtractToList(errors, excludeErrors);
        BeanValidationResult excludeResult = ValidationUtils.warpBeanValidationResult(excludeErrors);
        BeanValidationResult includeResult = ValidationUtils.warpBeanValidationResult(includeErrors);
        return new FilterViolationResult(includeResult, excludeResult);
    }

    /**
     * 加载spi过滤器
     *
     * @return @return {@link ConstraintViolationFilter}
     */
    private static ConstraintViolationFilter putSpiFilter() {
        ServiceLoader<ConstraintViolationFilter> serviceLoader = ServiceLoader.load(ConstraintViolationFilter.class);
        return serviceLoader.iterator().next();
    }


    /**
     * 过滤违反结果包装
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilterViolationResult {

        /**
         * 过滤约束校验结果包装
         */
        private BeanValidationResult includeResult;

        /**
         * 未过滤约束校验结果包装
         */
        private BeanValidationResult excludeResult;


        /**
         * 过滤约束校验结果是否正确
         *
         * @return true/false
         */
        public boolean includeSuccess() {
            return includeResult.isSuccess();
        }

        /**
         * 未过滤约束校验结果是否正确
         *
         * @return true/false
         */
        public boolean excludeSuccess() {
            return excludeResult.isSuccess();
        }
    }

}
