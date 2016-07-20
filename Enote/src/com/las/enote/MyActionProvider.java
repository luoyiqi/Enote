package com.las.enote;


import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

/**
 * @package_name com.las.enote
 * @file_name MyActionProvider.java
 * @author 王子璇
 * @time 下午4:02:53
 * @description 
 */
@SuppressLint("SimpleDateFormat")
public class MyActionProvider extends ActionProvider {
	private Context context;
	private Activity activity;
	private final int INSERTIMG_CODE = 502;
	private final int PictureButtonCode = 504;
	private static String file;

	public MyActionProvider(Context context) {
		super(context);
		this.context = context;
	}
	
	public void setActivity(Activity activity){
		this.activity = activity;
	}
	
	public static String getFile(){
		return  file;
	}

	@Override
	public View onCreateActionView() {
		return null;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		subMenu.clear();
		subMenu.add(context.getString(R.string.add_photo))
				.setIcon(R.drawable.add_photo)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						activity.startActivityForResult(intent, INSERTIMG_CODE);
						return false;
					}
				});
		subMenu.add(context.getString(R.string.add_camera))
				.setIcon(R.drawable.add_camera)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						String state = Environment.getExternalStorageState();  
				        if (state.equals(Environment.MEDIA_MOUNTED)) {  
				            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				            String path = context.getExternalFilesDir(null).getPath()+"/imageFiles";
				            File outDir = new File(path);
				            if (!outDir.exists()) {  
				            	outDir.mkdirs();  
				            }  
				            File outFile =  new File(outDir, System.currentTimeMillis() + ".jpg");
				            file = outFile.getAbsolutePath();
				            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));  
				            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
				            activity.startActivityForResult(intent, PictureButtonCode);
				        }
				        else {
				        	Toast.makeText(context, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
						}
						return false;
				        }
				});	
		subMenu.add(context.getString(R.string.add_paint))
				.setIcon(R.drawable.add_paint)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return false;
					}
				});
		subMenu.add(context.getString(R.string.add_audio))
				.setIcon(R.drawable.add_audio)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return false;
					}
				});
		subMenu.add(context.getString(R.string.add_file))
				.setIcon(R.drawable.add_file)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return false;
					}
				});
		subMenu.add(context.getString(R.string.add_scan))
		.setIcon(R.drawable.add_scan)
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return false;
			}
		});
		subMenu.add(context.getString(R.string.add_audio_to_text))
		.setIcon(R.drawable.add_audio_to_text)
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return false;
			}
		});
	}

	@Override
	public boolean hasSubMenu() {
		return true;
	}

	
	
}
