package com.las.enote.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper  extends SQLiteOpenHelper{
	
	private static final int DB_VERSION = 1;// 数据库版本号
	private static final String DB_NAME = "Enotes.db"; //数据库名称
	
	public static final String NOTE_NAME = "noteEntity"; //表名称
	public static final String ID = "ID";// ID
	public static final String NOTE_TITLE = "title"; // 标题
	public static final String NOTE_CONTENT = "content";// 内容
	public static final String DIR_ID = "dirId"; // 所属目录
	public static final String SUBJECT_ID = "subjectId"; //所属专题
	public static final String VERSION = "version"; //版本号
	public static final String MD5 = "md5"; //MD5
	public static final String TAG_ID = "tagId"; //标签 
	public static final String CREATE_USER_ID = "createUserId"; //创建者
	public static final String UPDATE_USER_ID = "updateUserId"; //修改者
	public static final String CREATE_TIME_STAMP = "createTimeStamp"; //创建时间毫秒
	public static final String UPDATE_TIME_STAMP = "updateTimeStamp"; //修改时间毫秒
	public static final String NOTE_ABSTRACT = "note_abstract";//摘要表名
	public static final String ABSTRACT_TEXT = "abstract_text";
	public static final String ABSTRACT_TITLE = "abstract_title";
	public static final String UUID = "UUID";
	public static final String ABSTRACT_IMAGE = "abstract_image";
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(" create table " + NOTE_NAME + " ( " + ID
				+ " integer primary key autoincrement , " + NOTE_TITLE
				+ " text, " + NOTE_CONTENT + " text," + DIR_ID
				+ " text," + SUBJECT_ID + " text," + VERSION + " text,"
				+ MD5 + " text,"+ TAG_ID + " text, " + CREATE_USER_ID
				+ " text, " + UPDATE_USER_ID + " text, "
				+ CREATE_TIME_STAMP + " text, " + UPDATE_TIME_STAMP + " text," +
				UUID + " varchar(32))");
		
		db.execSQL(" create table " + NOTE_ABSTRACT + " ( " + ID
				+ " integer primary key autoincrement , " + ABSTRACT_TITLE
				+ " varchar(50), " + ABSTRACT_TEXT + " varchar(3000)," + ABSTRACT_IMAGE
				+ " blob," + UUID + " varchar(32))");
		
		Log.v("wang ", "Create DB "+DB_NAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(" DROP TABLE IF EXISTS " + NOTE_NAME);
		onCreate(db);
	}

	public final String[] ENOTES_ALL_COLUMS =  {
		NOTE_TITLE, NOTE_CONTENT,
		DIR_ID, SUBJECT_ID, VERSION,
		MD5, TAG_ID, CREATE_USER_ID,
		UPDATE_USER_ID, CREATE_TIME_STAMP,
		UPDATE_TIME_STAMP,UUID};

}
