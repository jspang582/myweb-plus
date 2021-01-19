package cn.sgst.mywebplus.extensions.feign;

import cn.sgst.mywebplus.core.validation.BeanValidationResult;
import lombok.Data;

/**
 * 接收调用数据的实体类
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 15:34
 */
@Data
public class MywebPlusFeignResponse<T> {
    private Boolean success;
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
    private String errorDesc;
    private BeanValidationResult validationResult;
}
