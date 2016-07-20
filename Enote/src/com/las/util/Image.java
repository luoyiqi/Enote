
package com.las.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @package_name com.las.util
 * @file_name Image.java
 * @author 王子璇
 * @time 下午7:33:19
 * @description 
 */
public class Image {
	//bitmap to bytearray
    static  public byte[] Bitmap2Bytes(Bitmap bm){  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();    
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);   
          
            return baos.toByteArray();  
           }       
    	//bytearray to bitmap
    static  public Bitmap Bytes2Bimap(byte[] b){  
            
     if(b.length!=0){  
             
         return BitmapFactory.decodeByteArray(b, 0, b.length);  
        
     }  
     else {  
         return null;  
     }  
    } 
}
