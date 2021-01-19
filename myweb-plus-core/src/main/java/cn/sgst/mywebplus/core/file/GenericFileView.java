package cn.sgst.mywebplus.core.file;


import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 一般的文件下载
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/12/13 15:06
 */
public class GenericFileView extends AbstractView {


    @Override
    @SuppressWarnings("all")
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!model.containsKey(FileOptions.FILE_NAME)) {
            throw new IllegalArgumentException("未注入文件名称,请检查");
        }
        if (!model.containsKey(FileOptions.FILE_BYTES)) {
            throw new IllegalArgumentException("未注入文件字节名称,请检查");
        }
        String fileName = (String) model.get(FileOptions.FILE_NAME);
        byte[] bytes = (byte[])model.get(FileOptions.FILE_BYTES);
        OutputStream out = response.getOutputStream();
        // 设置一个响应头，无论是否被浏览器解析，都下载
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        out.write(bytes);
        // 关闭流
        out.flush();
        out.close();
    }
}
