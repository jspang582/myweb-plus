package cn.sgst.mywebplus.core.validation.group.support;

import cn.sgst.mywebplus.core.api.ApiResult;
import cn.sgst.mywebplus.core.validation.BeanValidationResult;
import cn.sgst.mywebplus.core.validation.BindingResultHelper;
import cn.sgst.mywebplus.core.validation.ValidationUtils;
import org.springframework.validation.BindingResult;

/**
 * 校验响应结果
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/8/10 13:08
 */
public class ValidateResponse {


    /**
     * 保存时校验,如果有数据格式异常则返回,没有则执行回调
     */
    public static ApiResult<Void> onSaveValidate(BindingResult bindingResult, ValidateCallBack callBack) {
        BindingResultHelper.FilterViolationResult filterResult = BindingResultHelper.filterViolation(bindingResult);
        if (!filterResult.excludeSuccess()) {
            ApiResult<Void> apiResult = ApiResult.error(400,"参数校验失败");
            apiResult.setValidationResult(filterResult.getExcludeResult());
            return apiResult;
        }
        boolean fillDone = filterResult.includeSuccess();
        callBack.onSuccess(fillDone);
        return ApiResult.success();
    }


    /**
     * 校验格式和必填项
     */
    public static ApiResult<Void> onValidateAll(BindingResult bindingResult) {
        BeanValidationResult validationResult = ValidationUtils.warpBeanValidationResult(bindingResult);
        if (validationResult.isSuccess()) {
            return ApiResult.success();
        }
        ApiResult<Void> apiResult = ApiResult.error(400,"参数校验失败");
        apiResult.setValidationResult(validationResult);
        return apiResult;
    }

}
