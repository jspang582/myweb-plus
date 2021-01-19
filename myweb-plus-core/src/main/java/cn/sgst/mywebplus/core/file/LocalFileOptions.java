package cn.sgst.mywebplus.core.file;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import java.io.*;

/**
 * 本地文件操作
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/19 10:25
 */
public class LocalFileOptions implements FileOptions {
    @Override
    public void download(String path, String fileName, OutputStream out) {
        File file = new File(path, fileName);
        InputStream in = FileUtil.getInputStream(file);
        IoUtil.copy(in, out);
    }

    @Override
    public void download(String path, File outFile) {
        final String fileName = FileUtil.getName(path);
        final String dir = StrUtil.removeSuffix(path, fileName);
        if (outFile.isDirectory()) {
            outFile = new File(outFile, fileName);
        }
        if (!outFile.exists()) {
            FileUtil.touch(outFile);
        }
        try (OutputStream out = FileUtil.getOutputStream(outFile)) {
            download(dir, fileName, out);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    @Override
    public boolean upload(String path, String fileName, InputStream fileStream) {
        File file = new File(path, fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            IoUtil.copy(fileStream, out);
            return true;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    @Override
    public boolean existFile(String path) {
        return FileUtil.exist(new File(path));
    }

    @Override
    public boolean rename(String path, String newName) {
        File file = FileUtil.rename(new File(path), newName, false, true);
        return file.exists();
    }
}
