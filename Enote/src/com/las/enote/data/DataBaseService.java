package com.las.enote.data;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseService {

	public DBHelper dbHelper;
	public Context context;
	public SQLiteDatabase db;
	
	public DataBaseService(Context context){
		this.context = context;
	}
	
	public DBHelper getDbHelper() {
		if (dbHelper == null) {
			dbHelper = new DBHelper(this.context);
		}
		return dbHelper;
	}
	
	public void insert(String[] strings){
		db = getDbHelper().getWritableDatabase();
		String string = "INSERT INTO noteEntity(" ;
		for (int i = 0; i < strings.length; i++) {
			if (i == strings.length-1) {
				string = string + getDbHelper().ENOTES_ALL_COLUMS[i];
			}
			else string = string + getDbHelper().ENOTES_ALL_COLUMS[i]+",";
		}
		string = string +") values(?,?,?,?,?,?,?,?,?,?,?,?)";
		db.execSQL(string, new Object[]{strings[0],strings[1],strings[2],
				strings[3],strings[4],strings[5],strings[6],strings[7],
				strings[8],strings[9],strings[10],strings[11]});
	}
	
	public Cursor queryNote(){
		db = getDbHelper().getReadableDatabase();
		return db.rawQuery("select * from noteEntity order by ID desc", null);
	}
	
	public void insertAbstract(String UUID, String title, String text, byte[] b){
		db = getDbHelper().getWritableDatabase();
		db.execSQL("INSERT INTO note_abstract(abstract_text,abstract_title," +
				"UUID,abstract_image) values(?)",new Object[]{text,title,UUID,b});
	}
	
	public Cursor queryAbstract(){
		db = getDbHelper().getReadableDatabase();
		return db.rawQuery("select * from note_abstract where UUID = ", null);
	}
	
	public void delete(int id){
		String sqlString = "delete * from noteEntity where ID="+id;
		db = getDbHelper().getWritableDatabase();
		db.execSQL(sqlString);
	}
}
