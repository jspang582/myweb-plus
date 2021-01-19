package cn.sgst.mywebplus.core.error;

/**
 *
 *<p>
 *  描述http错误状态码,注意:只描述系统错误,业务异常ServiceException排除在外
 *  例如:
 *   500: 亲,服务器出错了
 *   404: 路径未找到
 *</p>
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/26 9:25
 */
public interface ErrorMessageHandler {

    /**
     * 自定义错误状态码描述
     *
     * @param errorStatus 错误状态码
     * @return 错误描述
     */
    String describeErrorStatus(Integer errorStatus);
}
