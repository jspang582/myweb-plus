package cn.sgst.mywebplus.core.validation.constraintvalidators;


import cn.sgst.mywebplus.core.validation.ValidationRegex;
import cn.sgst.mywebplus.core.validation.constraints.Phone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *     手机号校验
 * </p>
 *
 * @author fli
 * @date 2020/4/8 11:38
 */
public class PhoneValidator extends AbstractEmptyBasedValidator<Phone,String> {

    @Override
    protected boolean isValid(String value) {
        Pattern regex = Pattern.compile(ValidationRegex.PHONE_REGEX);
        Matcher matcher = regex.matcher(value);
        return matcher.matches();
    }

}
