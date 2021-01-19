package cn.sgst.mywebplus.extensions.ftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpConfig;
import cn.hutool.extra.ftp.FtpException;
import cn.hutool.extra.ftp.FtpMode;
import cn.sgst.mywebplus.core.file.FileOptions;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Ftp文件操作
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2021/1/7 16:13
 */
public class FtpFileOptions implements FileOptions {

    private final FtpMode ftpMode;
    private final FtpConfig ftpConfig;

    public FtpFileOptions(FtpMode ftpMode, FtpConfig ftpConfig) {
        Assert.notNull(ftpMode,"ftpMode must not be null");
        Assert.notNull(ftpConfig,"ftpConfig must not be null");
        this.ftpMode = ftpMode;
        this.ftpConfig = ftpConfig;
    }

    /**
     * 下载文件到输出流
     *
     * @param path     文件路径
     * @param fileName 文件名
     * @param out      输出位置
     */
    @Override
    public void download(String path, String fileName, OutputStream out) {
        Ftp ftp = getFtp();
        try {
            ftp.download(path, fileName, out);
        } finally {
            closeFtp(ftp);
        }
    }

    @Override
    public void download(String path, File outFile) {
        Ftp ftp = getFtp();
        try {
            ftp.download(path, outFile);
        } finally {
            closeFtp(ftp);
        }
    }

    /**
     * 上传文件到指定目录，可选：
     *
     * @param path       服务端绝对路径
     * @param fileName   文件名
     * @param fileStream 文件流
     * @return 是否上传成功
     */
    @Override
    public boolean upload(String path, String fileName, InputStream fileStream) {
        Ftp ftp = getFtp();
        try {
            return ftp.upload(path, fileName, fileStream);
        } finally {
            closeFtp(ftp);
        }
    }

    @Override
    public boolean existFile(String path) {
        Ftp ftp = getFtp();
        try {
            return ftp.existFile(path);
        } finally {
            closeFtp(ftp);
        }
    }

    @Override
    public boolean rename(String path, String newName) {
        Ftp ftp = getFtp();
        try {
            final String fileName = FileUtil.getName(path);
            final String dir = StrUtil.removeSuffix(path, fileName);
            String to = dir.endsWith("/") ? dir.concat(newName) : dir.concat("/").concat(newName);
            return ftp.getClient().rename(path,to);
        } catch (IOException e) {
            throw new FtpException(e);
        } finally {
            closeFtp(ftp);
        }
    }


    private Ftp getFtp() {
        return new Ftp(ftpConfig,ftpMode);
    }

    private void closeFtp(Ftp ftp) {
        if (ftp != null) {
            try {
                ftp.close();
            } catch (IOException e) {
                throw new FtpException(e);
            }

        }
    }
}
