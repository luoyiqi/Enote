/**
 * 
 */
package com.las.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.util.Log;

/**
 * @package_name com.las.util
 * @file_name FileCopy.java
 * @author 王子璇
 * @time 下午12:05:40
 * @description
 */
public class FileUtil {

	public static void copyFile(String oldPath, String newPath) {
		try {
			// 新建文件输入流并对它进行缓冲
			FileInputStream input = new FileInputStream(oldPath);
			BufferedInputStream inBuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲
			FileOutputStream output = new FileOutputStream(newPath);
			BufferedOutputStream outBuff = new BufferedOutputStream(output);

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();

			// 关闭流
			inBuff.close();
			outBuff.close();
			output.close();
			input.close();
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}

	public void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			Log.i("fileDelete", "文件不存在");
		}
	}

}
