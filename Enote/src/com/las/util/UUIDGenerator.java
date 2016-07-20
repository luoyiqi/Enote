
package com.las.util;

import android.annotation.SuppressLint;
import java.util.UUID;

/**
 * @package_name com.las.util
 * @file_name UUIDGenerator.java
 * @author 王子璇
 * @time 下午12:00:40
 * @description 
 */
public class UUIDGenerator {
	 public UUIDGenerator() {  
	    }  
	  
	    @SuppressLint("DefaultLocale")
		public static String getUUID() {  
	        UUID uuid = UUID.randomUUID();  
	        String str = uuid.toString().toUpperCase();  
	        // 去掉"-"符号  
	        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
	        return temp;  
	    }  
	    //获得指定数量的UUID  
	    public static String[] getUUID(int number) {  
	        if (number < 1) {  
	            return null;  
	        }  
	        String[] ss = new String[number];  
	        for (int i = 0; i < number; i++) {  
	            ss[i] = getUUID();  
	        }  
	        return ss;  
	    }  
}
