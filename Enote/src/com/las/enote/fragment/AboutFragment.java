package com.las.enote.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.las.enote.R;
import com.las.util.ZipUtil;

@SuppressLint("SetJavaScriptEnabled")
public class AboutFragment extends Fragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
         
        WebView webView = (WebView) rootView.findViewById(R.id.webview);  
        //允许JavaScript执行  
        webView.getSettings().setJavaScriptEnabled(true);  
        //找到Html文件，也可以用网络上的文件  
        webView.loadUrl("file:///android_asset/index.html");  
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法  
        
        try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rootView;
        
    }
	
	private void init() throws Exception {
		InputStream abpath = getClass().getResourceAsStream("/assets/library");
		String path = new String(InputStreamToByte(abpath));
		String string =  getActivity().getApplicationContext().getExternalFilesDir(null).getPath();
		ZipUtil.ZipFolder(path, string+"/wang.wzx");
		
		   /*File file = new File(getActivity().getApplicationContext().getExternalFilesDir(null), "DemoFile.jpg");

		    try {
		        // Very simple code to copy a picture from the application's
		        // resource into the external file.  Note that this code does
		        // no error checking, and assumes the picture is small (does not
		        // try to copy it in chunks).  Note that if external storage is
		        // not currently mounted this will silently fail.
		        InputStream is = getResources().openRawResource(R.drawable.ic_action_search);
		        OutputStream os = new FileOutputStream(file);
		        byte[] data = new byte[is.available()];
		        is.read(data);
		        os.write(data);
		        is.close();
		        os.close();
		    } catch (IOException e) {
		        // Unable to create file, likely because external storage is
		        // not currently mounted.
		        Log.w("ExternalStorage", "Error writing " + file, e);
		    }*/
	}
	
	private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

}
