package com.las.enote;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerListAdapter extends BaseAdapter{
	
	private Context context;
	private List<DrawerItem> drawerItems;
	
	public DrawerListAdapter(Context context, List<DrawerItem> drawerItems) {
		super();
		this.context = context;
		this.drawerItems = drawerItems;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return drawerItems.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return drawerItems.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
         
        ImageView drawerIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView drawerTitle = (TextView) convertView.findViewById(R.id.title);
        TextView drawerCount = (TextView) convertView.findViewById(R.id.count);
         
        drawerIcon.setImageResource(drawerItems.get(position).getIcon());        
        drawerTitle.setText(drawerItems.get(position).getTitle());
        
        
        // displaying count
        // check whether it set visible or not
        if(drawerItems.get(position).isCountVisible()){
        	drawerCount.setText(drawerItems.get(position).getCount()+"");
        }else{
        	// hide the count view
        	drawerCount.setVisibility(View.GONE);
        }
        
        return convertView;
	}
}
