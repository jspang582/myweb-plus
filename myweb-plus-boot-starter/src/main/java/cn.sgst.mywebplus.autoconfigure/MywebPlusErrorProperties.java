package cn.sgst.mywebplus.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 异常错误属性
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/6 16:57
 */
@Data
@ConfigurationProperties(prefix = "myweb-plus.error")
public class MywebPlusErrorProperties {

    /**
     * 路径
     */
    private String path = "/error";
    /**
     * 返回json格式时是否永远返回200状态码
     */
    private boolean httpStatusAlways200;
    /**
     * 返回json格式时是否显示错误详情
     */
    private boolean showErrorDesc;
}
