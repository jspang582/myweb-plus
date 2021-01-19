package cn.sgst.mywebplus.core.file;


import cn.hutool.core.util.StrUtil;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * 文件下载 ModelAndView
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/8/19 17:59
 */
public class DefaultFileView extends GenericFileView {

    private final FileOptions fileOptions;


    public DefaultFileView(FileOptions fileOptions) {
        Assert.notNull(fileOptions,"fileOptions must not be null");
        this.fileOptions = fileOptions;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!model.containsKey(FileOptions.FILE_PATH)) {
            throw new IllegalArgumentException("未注入文件路径,请检查");
        }
        String filePath = (String) model.get(FileOptions.FILE_PATH);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        fileOptions.download(StrUtil.subBefore(filePath, '/', true), StrUtil.subAfter(filePath, '/', true), out);
        model.put(FileOptions.FILE_BYTES, out.toByteArray());
        super.renderMergedOutputModel(model, request, response);
    }
}
