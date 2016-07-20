package com.las.enote;

import java.util.ArrayList;
import java.util.List;



import com.las.enote.fragment.AboutFragment;
import com.las.enote.fragment.AllNotesFragment;
import com.las.enote.fragment.MessageFragment;
import com.las.enote.fragment.PeopleFragment;
import com.las.enote.fragment.PersonFragment;
import com.las.enote.fragment.RecycleBinFragment;
import com.las.enote.fragment.UserFragment;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends FragmentActivity implements OnItemClickListener {

	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private String[] mDrawerMenuTitles;
	private TypedArray mNavMenuIconsTypeArray;
	private List<DrawerItem> drawerItems;
	private DrawerListAdapter drawerListAdapter;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private Fragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initView();
		
		if (savedInstanceState == null) {
			
			selectItem(1);
		}
	}

	public void initView(){
		
		mTitle = mDrawerTitle = getTitle();
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        // nav drawer icons from resources
     	mNavMenuIconsTypeArray = getResources()
     				.obtainTypedArray(R.array.nav_drawer_icons);
		
     	drawerItems = new ArrayList<DrawerItem>();
     	//adding navigation drawer items to array
     	for (int i = 0; i < mDrawerMenuTitles.length; i++) {
     		if (i != 5) {
     			drawerItems.add(new DrawerItem(mDrawerMenuTitles[i], mNavMenuIconsTypeArray
        				.getResourceId(i, -1)));
			}
     		else {
     			drawerItems.add(new DrawerItem(mDrawerMenuTitles[i], mNavMenuIconsTypeArray
     					.getResourceId(i, -1),true,2));
			}
		}
     	
     	mNavMenuIconsTypeArray.recycle();
     	
     	//set the drawer list adapter
     	drawerListAdapter = new DrawerListAdapter(getApplicationContext(),
				drawerItems);
     	mDrawerList.setAdapter(drawerListAdapter);
     	mDrawerList.setOnItemClickListener(this);
     	
     	// enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.skyblue));
        
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
                
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.		
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	

	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_add).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		 if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	        // Handle action buttons
	        switch(item.getItemId()) {
	        case R.id.action_add:
	        	
	        	Intent intent = new Intent();
				intent.setClass(MainActivity.this, NoteAddActivity.class);
				startActivity(intent);
	        	return true;
	        
	        case R.id.action_search:
	            
	             Toast.makeText(this, R.string.action_search, Toast.LENGTH_SHORT).show();
	            
	            return true;
	        case R.id.settings:
	        	
	        	Toast.makeText(this, R.string.settings, Toast.LENGTH_SHORT).show();
	        	
	        	return true;
	        	
	        case R.id.synchronization:
	        	
	        	Toast.makeText(this, R.string.synchronization, Toast.LENGTH_SHORT).show();
	        	
	        	return true;
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	}
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		selectItem(position);
	}
	
	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void selectItem(int position) {
		// update the main content by replacing fragments
		switch (position) {
		case 0:
			fragment = new UserFragment();
			break;
		case 1:
			fragment = new AllNotesFragment();
			break;
		case 2:
			fragment = new PersonFragment();
			break;
		case 3:
			fragment = new PeopleFragment();
			break;
		case 4:
			fragment = new RecycleBinFragment();
			break;
		case 5:
			fragment = new MessageFragment();
			break;
		case 6:
			fragment = new AboutFragment();
			break;
			
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setItemsCanFocus(true);
			mDrawerList.setSelection(position);
			setTitle(mDrawerMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	
	public void onResume(){
		super.onResume();
		selectItem(1);
	}
	
	
}
