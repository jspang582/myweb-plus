package cn.sgst.mywebplus.core.api;

import cn.sgst.mywebplus.core.exception.IExceptionEnum;
import cn.sgst.mywebplus.core.validation.BeanValidationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定义返回数据格式
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/6 11:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";
    public static final String DEFAULT_ERROR_MESSAGE = "请求失败";
    public static final Integer DEFAULT_SUCCESS_CODE = 200;
    public static final Integer DEFAULT_ERROR_CODE = 500;

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;


    /**
     * 错误详情
     */
    private String errorDesc;


    /**
     * Bean的校验结果
     */
    private BeanValidationResult validationResult;


    /**
     * 响应数据
     */
    private T data;


    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();


    /**
     * 提供方便静态方法实例化的构造方法
     * @param success 是否成功
     * @param code 状态码
     * @param message 描述
     * @param data 数据
     */
    public ApiResult(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 无数据返回值的成功静态方法
     * @param <T> 泛型
     * @return ApiResult
     */
    public static <T>  ApiResult<T> success() {
        return new ApiResult<>(true,DEFAULT_SUCCESS_CODE,DEFAULT_SUCCESS_MESSAGE,null);
    }

    /**
     * 有数据返回值的成功静态方法
     * @param object 数据对象
     * @param <T> 泛型
     * @return ApiResult
     */
    public static <T> ApiResult<T> success(T object) {
        return new ApiResult<>(true,DEFAULT_SUCCESS_CODE,DEFAULT_SUCCESS_MESSAGE,object);
    }

    /**
     * 有响应状态码、响应信息、数据的成功静态方法
     * @param code 响应状态码
     * @param message 响应信息
     * @param object 数据对象
     * @param <T> 泛型
     * @return ApiResult
     */
    public static <T> ApiResult<T> success(Integer code, String message, T object) {
        return new ApiResult<>(true,code,message,object);
    }


    /**
     * 有响应信息的错误静态方法
     * @param message 响应信息
     * @param <T> 泛型
     * @return ApiResult
     */
    public static <T> ApiResult<T> error(String message) {
        return new ApiResult<>(false,DEFAULT_ERROR_CODE,message,null);
    }


    /**
     * 有响应状态码和响应信息的错误静态方法
     * @param code 响应状态码
     * @param message 响应信息
     * @param <T> 泛型
     * @return ApiResult
     */
    public static <T> ApiResult<T> error(Integer code, String message) {
        return new ApiResult<>(false,code,message,null);
    }


    /**
     * 有响应状态码、响应信息和数据的错误静态方法
     * @param code 响应状态码
     * @param message 响应信息
     * @param object  数据对象
     * @param <T> 泛型
     * @return ApiResult
     */
    public static <T> ApiResult<T> error(Integer code, String message, T object) {
        return new ApiResult<>(false,code,message,object);
    }


    /**
     * 有错误枚举类的错误静态方法
     * @param exceptionEnum 异常枚举类
     * @param <T> 泛型
     * @return ApiResult
     */
    public static <T> ApiResult<T> error(IExceptionEnum exceptionEnum){
        return new ApiResult<>(false,exceptionEnum.getCode(),exceptionEnum.getMessage(),null);
    }

}
