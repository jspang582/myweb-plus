package cn.sgst.mywebplus.core.dict;

/**
 * 加载数据字典异常
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/3/22 12:52
 */
public class LoadDictDetailsException extends RuntimeException {


    /**
     * 提供带错误信息的构造函数
     * @param message 信息
     */
    public LoadDictDetailsException(String message) {
        super(message);
    }

    /**
     * 提供带错误信息和栈信息的构造函数
     * @param message 信息
     * @param cause 栈信息
     */
    public LoadDictDetailsException(String message, Throwable cause) {
        super(message, cause);
    }
}
