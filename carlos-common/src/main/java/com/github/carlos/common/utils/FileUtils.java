package com.github.carlos.common.utils;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;


/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/21 15:42
 * @description: TODO
 */
public class FileUtils {


    /**
     * 获取文件名
     * @param name
     * @return
     */
    public static String getSuffixName(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }


    /**
     * 获取文件扩展名
     * @param name
     * @return
     */
    public static String getSufExtName(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    private static void createDir(String path) {
        File dir = new File(path);
        if (dir.exists() == false) {
            dir.mkdir();
        }
    }

    public static void unzip(String zipFilePath, String unzipDirectory)
            throws Exception {

        // 创建zip文件对象
        ZipFile zipFile = new ZipFile(zipFilePath, "gbk");
        // 创建本zip文件解压目录
        // File unzipFile = new File(unzipDirectory + "/" +
        // getSuffixName(file.getName()));
        File unzipFile = new File(unzipDirectory);
        if (unzipFile.exists()) {
            unzipFile.delete();
        }
        unzipFile.mkdir();
        // 得到zip文件条目枚举对象
        Enumeration<?> zipEnum = zipFile.getEntries();
        // 定义输入输出流对象
        InputStream input = null;
        OutputStream output = null;
        // 定义对象
        ZipEntry entry = null;
        // 循环读取条目
        while (zipEnum.hasMoreElements()) {
            // 得到当前条目
            entry = (ZipEntry) zipEnum.nextElement();
            String entryName = new String(entry.getName());
            // 用/分隔条目名称
            String names[] = entryName.split("\\/");
            int length = names.length;
            String path = unzipFile.getAbsolutePath();
            for (int v = 0; v < length; v++) {
                if (v < length - 1) {
                    // 最后一个目录之前的目录
                    path += "/" + names[v] + "/";
                    createDir(path);
                } else {
                    // 最后一个
                    if (entryName.endsWith("/")) // 为目录,则创建文件夹
                    {
                        createDir(unzipFile.getAbsolutePath() + "/" + entryName);
                    } else {
                        // 为文件,则输出到文件
                        input = zipFile.getInputStream(entry);
                        output = new FileOutputStream(new File(
                                unzipFile.getAbsolutePath() + "/" + entryName));
                        byte[] buffer = new byte[1024 * 8];
                        int readLen = 0;
                        while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
                            output.write(buffer, 0, readLen);
                        }
                        // 关闭流
                        input.close();
                        output.flush();
                        output.close();
                    }
                }
            }
        }
        if (zipFile != null) {
            zipFile.close();
        }

    }

    public static void deleteFiles(String filePath) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                /*
                 * 递归调用
                 */
                deleteFiles(file.getAbsolutePath());
                file.delete();

            } else {
                file.delete();
            }
        }
        root.delete();
    }

    public static File createNewFile(String filePath) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
