package cn.sgst.mywebplus.samples.validation.controller;

import cn.sgst.mywebplus.core.api.ApiResult;
import cn.sgst.mywebplus.core.validation.ConstraintViolationFilterStat;
import cn.sgst.mywebplus.core.validation.RequiredPayloadConstraintViolationFilter;
import cn.sgst.mywebplus.core.validation.SpringViolationErrorReflectFactory;
import cn.sgst.mywebplus.core.validation.ValidationUtils;
import cn.sgst.mywebplus.core.validation.group.Add;
import cn.sgst.mywebplus.samples.validation.Inf;
import cn.sgst.mywebplus.samples.validation.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户-前端控制器
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/21 9:46
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    /**
     * 新增用户
     */
    @PostMapping(value = "/save")
    public ApiResult<Void> saveUser(@RequestBody @Validated({Add.class,Default.class}) User user) {
        log.info("user",user);
        return ApiResult.success();
    }

    @PostMapping(value = "/validate")
    public ApiResult<Void> validate(@Validated Inf inf, BindingResult bindingResult) {
        System.out.println(inf);
        List<ObjectError> errors = bindingResult.getAllErrors();
        Set<ConstraintViolation<?>> violations = errors.stream().map(SpringViolationErrorReflectFactory::getViolation).collect(Collectors.toSet());
        ConstraintViolationFilterStat stat = ValidationUtils.filter(violations);
        if(stat.hasExcludes()) {
            Set<ConstraintViolation<?>> excludes = stat.getExcludes();
            List<ObjectError> excludeErrors = errors.stream().filter(error -> {
                ConstraintViolation<?> violation = SpringViolationErrorReflectFactory.getViolation(error);
                return excludes.contains(violation);
            }).collect(Collectors.toList());
            ApiResult<Void> apiResult = ApiResult.success();
            apiResult.setValidationResult(ValidationUtils.warpBeanValidationResult(excludeErrors));
            return apiResult;
        }
        System.out.println("信息填写完整？:" + !stat.hasIncludes());
        return ApiResult.success();
    }
}
