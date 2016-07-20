package com.las.enote.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.las.enote.NoteDisplayActivity;
import com.las.enote.R;
import com.las.enote.data.DBHelper;
import com.las.enote.data.DataBaseService;

public class AllNotesFragment extends Fragment{
	
	private ListView listView;
	private DataBaseService dataBaseService;
	private SimpleAdapter mySimpleAdapter;
	private ArrayList<HashMap<String, Object>> listItem;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View view = inflater.inflate(R.layout.fragment_all_notes, container, false);
        listView = (ListView) view.findViewById(R.id.list1);        
        init();
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView textView = (TextView) view.findViewById(R.id.item_UUID);
				String UUID = textView.getText().toString();
				Intent intent=new Intent();  
                intent.setClass(getActivity(), NoteDisplayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("uuid", UUID);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
				
			}
        	
        });
        
        return view;
    }
	
	private void init(){				
		setData();		
		mySimpleAdapter = new SimpleAdapter(getActivity(), listItem,
				R.layout.note_item, 
				new String[] { "item_image", "item_title","item_content", "item_catalogue","item_time","item_UUID" }, 
			    new int[] { R.id.item_image,R.id.item_title, R.id.item_content, R.id.item_catalogue,R.id.item_time,R.id.item_UUID });
		mySimpleAdapter.setViewBinder(new ViewBinder() {  
              
            public boolean setViewValue(View view, Object data,  
                    String textRepresentation) {  
                //判断是否为我们要处理的对象  
                if(view instanceof ImageView  && data instanceof Bitmap){  
                    ImageView iv = (ImageView) view;  
                  
                    iv.setImageBitmap((Bitmap) data);  
                    return true;  
                }else  
                return false;  
            }  
        }); 			 
		 
		listView.setAdapter(mySimpleAdapter);
		
		
	}

	private void setData(){
		listItem = new ArrayList<HashMap<String, Object>>();
		dataBaseService = new DataBaseService(this.getActivity());
		Cursor cursor = dataBaseService.queryNote();
		cursor.moveToFirst();
		int titleColumn = cursor.getColumnIndex(DBHelper.NOTE_TITLE);
		int contentColumn = cursor.getColumnIndex(DBHelper.NOTE_CONTENT); 
		int timeColumn = cursor.getColumnIndex(DBHelper.UPDATE_TIME_STAMP);
		int dirIdColumn = cursor.getColumnIndex(DBHelper.DIR_ID);
		int subjectIdColumn = cursor.getColumnIndex(DBHelper.SUBJECT_ID);
		int UUIDColumn = cursor.getColumnIndex(DBHelper.UUID);
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
		
			String titleString = cursor.getString(titleColumn);
			String contentString = cursor.getString(contentColumn);
			String timeString = cursor.getString(timeColumn);
			String dirIdString = cursor.getString(dirIdColumn);
			String subjectIdString = cursor.getString(subjectIdColumn);
			String UUIDString = cursor.getString(UUIDColumn);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item_image", R.drawable.bayun);
			map.put("item_title",  titleString);
			map.put("item_content",  contentString);
			map.put("item_catalogue",  dirIdString+"\\"+subjectIdString);
			map.put("item_time", timeString);
			map.put("item_UUID", UUIDString);
			listItem.add(map);
		}				
		
		cursor.close();
	}
			
}
