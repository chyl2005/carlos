package com.github.carlos.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具类
 * @author chyl2005
 * @since 2012-09-18
 */
public class ZipUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtil.class);

	//缓冲大小
	static final int BUFFER = 2048;
	
	/**
	 * 压缩
	 * @param sourcePath
	 * @param desPath
	 */
	public static void zip(String sourcePath, String desPath) {
		try{  
			BufferedInputStream bis = null;  

			byte[] array = new byte[BUFFER];  
			File f = new File(sourcePath);
			File file[] = f.listFiles();  

			FileOutputStream fos = new FileOutputStream(desPath);  
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));  
			
			for(int i=0;i<file.length;i++){  
				FileInputStream fis = new FileInputStream(file[i]);  
				bis = new BufferedInputStream(fis,BUFFER);  
				ZipEntry zipEntry = new ZipEntry(file[i].getName());  
				zos.putNextEntry(zipEntry);
                 int len=0;
				while((len=bis.read(array))!=-1){  
					zos.write(array, 0, len);  
				}  
				bis.close();  
			}  
			zos.close();  
		}catch(Exception e){
			LOGGER.error(e.toString());
		}  
	}
	
	/**
	 * 解压
	 * @param sourcePath
	 * @param desPath
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void unzip(String sourcePath, String desPath) throws IOException {
            ZipFile zipFile = new ZipFile(sourcePath);  
            Enumeration enu = zipFile.entries();  
            while(enu.hasMoreElements()){  
                ZipEntry zipEntry = (ZipEntry)enu.nextElement();  
                if(zipEntry.isDirectory()){  
                    new File(desPath+zipEntry.getName()).mkdirs();  
                    continue;  
                }
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(zipEntry));  
                File file = new File(desPath+zipEntry.getName());  
                File parent = file.getParentFile();  
                if(parent != null && !parent.exists()){  
                    parent.mkdirs();  
                }  
                FileOutputStream fos = new FileOutputStream(file);  
                BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);  
                  
                byte[] array = new byte[BUFFER];  
                int len=0;
				while((len=bis.read(array))!=-1){  
					bos.write(array, 0, len);  
				}  
                  
                bos.flush();  
                bos.close();  
                bis.close();  
            }  
    }
	
	public static void main(String[] args) {
		ZipUtil.zip("D:/index/meitu20150518/", "D:/index/meitu201505202025.zip");
		try {
			ZipUtil.unzip("D:/index/meitu201505202025.zip", "D:/index/meitu201505202025/");
		} catch (IOException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
	}
}
