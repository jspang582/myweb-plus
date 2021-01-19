package cn.sgst.mywebplus.core.validation.constraintvalidators;


import cn.hutool.core.util.IdcardUtil;
import cn.sgst.mywebplus.core.validation.constraints.Idcard;


/**
 * <p>
 *     身份证校验验证
 * </p>
 *
 * @author fli
 * @date 2020/4/8 11:37
 */
public class IdCardValidator extends AbstractEmptyBasedValidator<Idcard,String> {
    private Idcard.IdcardType[] types;

    @Override
    public void initialize(Idcard idcard) {
        this.types = idcard.types();
    }

    @Override
    protected boolean isValid(String idcard) {
        boolean result = false;
        int length = idcard.trim().length();
        if ((length != 10) && (length != 15) && (length != 18)) {
            return false;
        }
        for (Idcard.IdcardType idcardType : types) {
            if (idcardType == Idcard.IdcardType.ID15) {
                boolean flag = IdcardUtil.isValidCard15(idcard);
                if (flag) {
                    result = true;
                    break;
                }
            }
            if (idcardType == Idcard.IdcardType.ID18) {
                boolean flag = IdcardUtil.isValidCard18(idcard);
                if (flag) {
                    result = true;
                    break;
                }
            }
            if (idcardType == Idcard.IdcardType.ID10) {
                String[] cardVal = IdcardUtil.isValidCard10(idcard);
                boolean flag = null != cardVal && "true".equals(cardVal[2]);
                if (flag) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
