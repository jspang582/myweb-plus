package cn.sgst.mywebplus.core.validation;

/**
 * 校验正则
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 16:49
 */
public interface ValidationRegex {

    /**
     * 手机号正则
     */
    String PHONE_REGEX = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$";

    /**
     * 邮箱正则
     */
    String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
}
