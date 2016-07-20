package com.las.enote;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;

import com.las.enote.data.DBHelper;
import com.las.enote.data.DataBaseService;
import com.las.util.FileUtil;
import com.las.util.UUIDGenerator;
import com.las.util.ZipUtil;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressLint("NewApi")
public class NoteAddActivity extends Activity implements OnClickListener {

	public final static String EXTRA_FILE_CHOOSER = "file_chooser";

	private DataBaseService dataBaseService;
	private EditText title, content;
	private RelativeLayout finishLayout;
	private String titleString, contentString;
	private ActionBar actionBar;
	private int screenHeight;
	private int screenWidth;
	private ContentResolver contentresolver;
	// private Intent fileChooserIntent;
	private final int AttachmentCode = 501;
	private final int INSERTIMG_CODE = 502;
	private final int PictureButtonCode = 504;
	private final int RecordButtonCode = 503;
	private String contents;
	private static String UUID = UUIDGenerator.getUUID();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_add);
		setOverflowShowingAlways();
		initActionBar();
		dataBaseService = new DataBaseService(this);
		dataBaseService.getDbHelper();

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		/** DisplayMetrics获取屏幕信息 */
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		screenWidth = displayMetrics.widthPixels;
		screenHeight = displayMetrics.heightPixels;

		contentresolver = NoteAddActivity.this.getContentResolver();

		title = (EditText) this.findViewById(R.id.title);
		content = (EditText) this.findViewById(R.id.content);
		finishLayout = (RelativeLayout) this.findViewById(R.id.finishLayout);
		finishLayout.setOnClickListener(this);
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	/**
	 * @功能： 自定义actionBar
	 */
	@SuppressLint("InflateParams")
	private void initActionBar() {
		// TODO Auto-generated method stub
		actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(getResources().getDrawable(
				R.color.skyblue));
		View actionbarLayout = LayoutInflater.from(this).inflate(
				R.layout.add_actionbar, null);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionbarLayout);
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == finishLayout) {
			saveNote();
			titleString = title.getText().toString();
			if (titleString.length() != 0 || contents.length() != 0) {
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String dateString = df.format(new Date()).toString();
				String[] strings = { titleString, contentString, "个人专题",
						"数据挖掘", "11", "md5", "das", "chuangjianzhe", "udada",
						dateString, dateString, UUID };
				dataBaseService.insert(strings);
				Cursor cursor = dataBaseService.queryNote();
				cursor.moveToFirst();
				int columnIndex = cursor
						.getColumnIndex(DBHelper.UPDATE_TIME_STAMP);
				Log.v("dasd", cursor.getString(columnIndex));
				cursor.close();
				Toast.makeText(this, "笔记保存成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "空白笔记被舍弃", Toast.LENGTH_SHORT).show();
			}
			NoteAddActivity.this.finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_note_menu, menu);
		MyActionProvider myActionProvider = (MyActionProvider) menu.findItem(
				R.id.action_plus).getActionProvider();
		myActionProvider.setActivity(NoteAddActivity.this);
		return true;
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED) {
			return;
		}
		// 附件
		if (resultCode == RESULT_OK && requestCode == AttachmentCode) {
			// 获取路径名
			String filePath = data.getStringExtra(EXTRA_FILE_CHOOSER);
			if (filePath != null) {
				toast("选择文件为: " + filePath);
				// filepathes.add(filePath);
				Uri uri = getDrawableURI(R.drawable.add_file);
				Editable eb = content.getEditableText();
				// 获得光标所在位置
				int startPosition = content.getSelectionStart();
				eb.insert(
						startPosition,
						Html.fromHtml("<br/><a href='" + uri.toString()
								+ "'>附件<img src='" + uri.toString()
								+ "'/></a><br/>", imageGetter, null));
			} else {
				toast(getText(R.string.open_file_failed));
			}
		}
		// 插图
		if (resultCode == RESULT_OK && requestCode == INSERTIMG_CODE) {
			Uri uri = data.getData();
			Editable eb = content.getEditableText();
			// 获得光标所在位置
			int startPosition = content.getSelectionStart();
			eb.insert(startPosition, Html.fromHtml("<br/><img src='"
					+ "file://" + getRealPathFromURI(uri) + "'/><br/>",
					imageGetter, null));

		}
		// 拍照
		if (resultCode == RESULT_OK && requestCode == PictureButtonCode) {
			String imageFile = "file://" + MyActionProvider.getFile();
			Editable eb = content.getEditableText();
			// 获得光标所在位置
			int startPosition = content.getSelectionStart();
			eb.insert(startPosition, Html.fromHtml("<br/><img src='"
					+ imageFile + "'/><br/>", imageGetter, null));
		}
	}

	/** 获取项目资源的URI */
	private Uri getDrawableURI(int resourcesid) {
		Resources r = getResources();
		Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
				+ r.getResourcePackageName(resourcesid) + "/"
				+ r.getResourceTypeName(resourcesid) + "/"
				+ r.getResourceEntryName(resourcesid));
		return uri;
	}

	private ImageGetter imageGetter = new ImageGetter() {
		@Override
		public Drawable getDrawable(String source) {
			try {
				Uri uri = Uri.parse(source);
				Bitmap bitmap = getImage(contentresolver, uri);
				return getMyDrawable(bitmap);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	};

	private static Drawable getMyDrawable(Bitmap bitmap) {
		@SuppressWarnings("deprecation")
		Drawable drawable = new BitmapDrawable(bitmap);

		int imgHeight = drawable.getIntrinsicHeight();
		int imgWidth = drawable.getIntrinsicWidth();
		drawable.setBounds(0, 0, imgWidth, imgHeight);
		return drawable;
	}

	private Bitmap getImage(ContentResolver cr, Uri uri) {
		try {
			Bitmap bitmap = null;
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// options.inJustDecodeBounds=true,图片不加载到内存中
			newOpts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, newOpts);
			float flag = 1;
			int imgWidth = bitmap.getWidth();
			Log.e("width", imgWidth+"");
			int imgHeight = bitmap.getHeight();
			Log.e("height", imgHeight+"");
				if (bitmap.getWidth()*3 <3000 && bitmap.getHeight()*3 < 3000) {
					flag = 3;					
				}else if (bitmap.getWidth()*2 <3000 && bitmap.getHeight()*2 < 3000) {
					flag = 2;
				}
				Matrix matrix = new Matrix();
				matrix.postScale(flag, flag); // 长和宽放大缩小的比例
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
			return bitmap;
		} catch (Exception e) {
			// System.out.println("文件不存在");
			e.printStackTrace();
			return null;
		}
	}

	private void toast(CharSequence hint) {
		Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
	}

	/** 从uri获取文件路径,uri以content开始 */
	private String getRealPathFromURI(Uri contentUri) {
		String filePath = null;
		if (DocumentsContract.isDocumentUri(this, contentUri)) {
			String wholeID = DocumentsContract.getDocumentId(contentUri);
			String id = wholeID.split(":")[1];
			String[] column = { MediaStore.Images.Media.DATA };
			String sel = MediaStore.Images.Media._ID + "=?";
			Cursor cursor = this.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
					new String[] { id }, null);
			int columnIndex = cursor.getColumnIndex(column[0]);
			if (cursor.moveToFirst()) {
				filePath = cursor.getString(columnIndex);
			}
			cursor.close();
		} else {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = this.getContentResolver().query(contentUri,
					projection, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			filePath = cursor.getString(column_index);
		}

		return filePath;
	}

	private void saveNote() {
		hideSoftInputWin();
		String path = this.getExternalCacheDir().getPath() + "/data/" + UUID;
		File outDir = new File(path);
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
		Editable eb = content.getEditableText();
		contents = Html.toHtml(eb);
		Pattern p = Pattern
				.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
		Matcher m = p.matcher(contents);
		while (m.find()) {
			// System.out.println(getFileName(m.group(1)));
			FileUtil.copyFile(m.group(1).substring(8), path + "/"
					+ getFileName(m.group(1)));
			contents = contents.replaceAll(m.group(1), getFileName(m.group(1)));
		}
		
		Log.v("html", contents);

		File htmlFile = new File(path, "index.html");
		try {
			FileWriter fw = new FileWriter(htmlFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contents);
			bw.flush();
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 打包成zip
		String pathZip = this.getExternalFilesDir(null).getPath() + "/data/";
		File dir = new File(pathZip);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			Log.i("dasd", path);
			ZipUtil.ZipFolder(path, pathZip + UUID + ".zix");
			Log.v("Zip位置", pathZip + UUID + ".zix");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 删除cache
		FileUtil fileUtil = new FileUtil();
		fileUtil.deleteFile(outDir);

		// 提取纯文本
		try {
			Parser parser = new Parser();
			parser.setInputHTML(contents);

			StringBean sb = new StringBean();
			// 设置不需要得到页面所包含的链接信息
			sb.setLinks(false);
			// 设置将不间断空格由正规空格所替代
			sb.setReplaceNonBreakingSpaces(true);
			// 设置将一序列空格由一个单一空格所代替
			sb.setCollapse(true);
			parser.visitAllNodesWith(sb);
			contentString = sb.getStrings();
		} catch (ParserException e) {
			e.printStackTrace();
		}

	}

	public String getFileName(String pathandname) {

		int start = pathandname.lastIndexOf("/");
		int end = pathandname.lastIndexOf(".");
		if (start != -1 && end != -1) {
			return pathandname.substring(start + 1);
		} else {
			return null;
		}

	}

	private void hideSoftInputWin() {
		/** 隐藏虚拟键盘 */
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(content.getWindowToken(), 0);
	}
}
