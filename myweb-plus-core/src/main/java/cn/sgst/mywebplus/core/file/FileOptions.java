package cn.sgst.mywebplus.core.file;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 定义文件操作
 *
 * @author: fli
 * @email: fli@sstir.cn
 * @date: 2020/10/30 15:58
 */
public interface FileOptions {


    /**
     * 下载文件名称
     */
    String FILE_NAME = "fileName";

    /**
     * 下载文件路径
     */
    String FILE_PATH = "filePath";

    /**
     * 文件字节
     */
    String FILE_BYTES = "fileBytes";

    /**
     * 下载文件到输出流
     *
     * @param path     文件路径
     * @param fileName 文件名
     * @param out      输出位置
     */
    void download(String path, String fileName, OutputStream out);

    /**
     * 下载文件
     *
     * @param path    文件路径
     * @param outFile 输出文件或目录
     */
    void download(String path, File outFile);


    /**
     * 上传文件到指定目录
     *
     * @param path       服务端绝对路径
     * @param fileName   文件名
     * @param fileStream 文件流
     * @return 是否上传成功
     */
    boolean upload(String path, String fileName, InputStream fileStream);

    /**
     * 判断ftp服务器文件是否存在
     *
     * @param path 文件路径
     * @return 是否存在
     */
    boolean existFile(String path);


    /**
     * 重命名文件
     * @param path 原文件的绝对路径
     * @param newName 重命名文件,包括后缀
     * @return 是否重命名成功
     *
     */
    boolean rename(String path, String newName);
}
