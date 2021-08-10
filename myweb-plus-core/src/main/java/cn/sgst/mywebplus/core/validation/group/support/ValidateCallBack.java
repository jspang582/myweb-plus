package cn.sgst.mywebplus.core.validation.group.support;

/**
 * 校验回调
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/8/10 13:07
 */
public interface ValidateCallBack {

    /**
     * callback for success
     *
     * @param fillDone whether the form is complete
     */
    void onSuccess(boolean fillDone);
}
