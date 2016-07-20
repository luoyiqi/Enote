/**
 * 
 */
package com.las.enote;

import java.io.File;

import com.las.util.ZipUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

/**
 * @package_name com.las.enote
 * @file_name NoteDisplayActivity.java
 * @author 王子璇
 * @time 下午4:29:23
 * @description 
 */
@SuppressLint("SetJavaScriptEnabled")
public class NoteDisplayActivity extends Activity{
	
	 public void onCreate(Bundle savedInstanceState) {  
	        
		 super.onCreate(savedInstanceState);  
	     setContentView(R.layout.note_display);
	     
	     String UUID = this.getIntent().getStringExtra("uuid");
	     Log.v("uuid", UUID);
	     String UUIDPath = this.getExternalFilesDir(null).getPath() + "/data/"+UUID+".zix";
	     Log.v("tag", UUIDPath);
	     String cachePath = this.getExternalCacheDir().getPath() + "/data";
	     File dir = new File(cachePath+"/"+UUID);
			if (!dir.exists()) {
				dir.mkdirs();
				Log.v("dir", "文件夹");
			}
	     try {
			ZipUtil.UnZipFolder(UUIDPath, cachePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     	WebView webView = (WebView) this.findViewById(R.id.webview);  
	        //允许JavaScript执行  
	     	WebSettings webSettings = webView.getSettings();
	        webSettings.setJavaScriptEnabled(true);
	        webSettings.setUseWideViewPort(true); 
	        webSettings.setLoadWithOverviewMode(true); 
	        //webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//宽度自适应
	        //找到Html文件，也可以用网络上的文件  
	        webView.loadUrl("file://"+cachePath+"/"+UUID+"/index.html");  
	        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
	     
	 }

}
