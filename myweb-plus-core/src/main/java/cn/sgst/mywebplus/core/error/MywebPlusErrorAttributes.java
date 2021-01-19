package cn.sgst.mywebplus.core.error;

import cn.hutool.core.util.StrUtil;
import cn.sgst.mywebplus.core.exception.ServiceException;
import cn.sgst.mywebplus.core.validation.ValidationUtils;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义返回错误信息以适配项目,替换SpringBoot的默认配置
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/26 9:45
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MywebPlusErrorAttributes extends DefaultErrorAttributes {

    private final ErrorMessageHandler errorMessageHandler;

    public MywebPlusErrorAttributes(ErrorMessageHandler errorMessageHandler) {
        Assert.notNull(errorMessageHandler, "ErrorMessageHandler must not be null");
        this.errorMessageHandler = errorMessageHandler;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                  boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        // 失败响应
        errorAttributes.put("success", false);
        addCode(errorAttributes, webRequest);
        addMessage(errorAttributes, webRequest);
        addErrorDetails(errorAttributes, webRequest);
        // 当前时间戳
        errorAttributes.put("timestamp", System.currentTimeMillis());
        return errorAttributes;
    }

    /**
     * 添加错误详情信息
     */
    private void addErrorDetails(Map<String, Object> errorAttributes, WebRequest webRequest) {
        Throwable error = getError(webRequest);
        if (error != null) {
            while (error instanceof ServletException && error.getCause() != null) {
                error = error.getCause();
            }
        }
        addErrorMessage(errorAttributes, webRequest, error);
    }

    private void addErrorMessage(Map<String, Object> errorAttributes, WebRequest webRequest, Throwable error) {
        BindingResult result = extractBindingResult(error);
        if (result == null) {
            addExceptionErrorMessage(errorAttributes, webRequest, error);
        }
        else {
            addBindingResultErrorMessage(errorAttributes, result);
        }
    }

    private void addExceptionErrorMessage(Map<String, Object> errorAttributes, WebRequest webRequest, Throwable error) {
        Object message = getAttribute(webRequest, RequestDispatcher.ERROR_MESSAGE);
        if (StringUtils.isEmpty(message) && error != null) {
            message = error.getMessage();
        }
        if (StringUtils.isEmpty(message)) {
            message = "No errorDesc available";
        }
        errorAttributes.put("errorDesc", message);
    }

    private void addBindingResultErrorMessage(Map<String, Object> errorAttributes, BindingResult result) {
        errorAttributes.put("errorDesc", "Validation failed for object='" + result.getObjectName() + "'. "
                + "Error count: " + result.getErrorCount());
        errorAttributes.put("validationResult",ValidationUtils.warpBeanValidationResult(result));
    }


    /**
     * 添加错误状态码 code
     */
    private void addCode(Map<String, Object> errorAttributes, WebRequest webRequest) {
        // 判断是否是业务异常
        if (isServiceException(webRequest)) {
            Throwable error = getError(webRequest);
            errorAttributes.put("code", ((ServiceException) error).getCode());
        } else {
            Integer status = getAttribute(webRequest, RequestDispatcher.ERROR_STATUS_CODE);
            if (status == null) {
                errorAttributes.put("code", 999);
            } else {
                errorAttributes.put("code", status);
            }
        }
    }


    /**
     * 添加错误描述 message
     */
    private void addMessage(Map<String, Object> errorAttributes, WebRequest webRequest) {
        // 判断是否是业务异常
        if (isServiceException(webRequest)) {
            Throwable error = getError(webRequest);
            errorAttributes.put("message", ((ServiceException) error).getErrorMessage());
        } else {
            Integer status = getAttribute(webRequest, RequestDispatcher.ERROR_STATUS_CODE);
            if (status == null) {
                errorAttributes.put("message", "None");
            } else {
                try {
                    String describeMessage = errorMessageHandler.describeErrorStatus(status);
                    if (StrUtil.isNotBlank(describeMessage)) {
                        errorAttributes.put("message", describeMessage);
                    } else {
                        errorAttributes.put("message", HttpStatus.valueOf(status).getReasonPhrase());
                    }
                } catch (Exception ex) {
                    // 获取错误原因失败
                    errorAttributes.put("message", "Http Status " + status);
                }
            }
        }

    }


    /**
     * 判断是不是业务异常
     */
    private boolean isServiceException(WebRequest webRequest) {
        Throwable error = getError(webRequest);
        return error instanceof ServiceException;
    }

    private BindingResult extractBindingResult(Throwable error) {
        if (error instanceof BindingResult) {
            return (BindingResult) error;
        }
        if (error instanceof MethodArgumentNotValidException) {
            return ((MethodArgumentNotValidException) error).getBindingResult();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
        return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

}
