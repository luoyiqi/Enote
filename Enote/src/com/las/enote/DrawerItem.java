package com.las.enote;

public class DrawerItem {
	

	private String title;
	private int icon;
	// boolean to set visibility of the count
	private boolean isCountVisible = false;
	private int count;
	
	public DrawerItem(String title, int icon, boolean isCountVisible, int count) {
		super();
		this.title = title;
		this.icon = icon;
		this.isCountVisible = isCountVisible;
		this.count = count;
	}

	public DrawerItem(String title, int icon) {
		super();
		this.title = title;
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public boolean isCountVisible() {
		return isCountVisible;
	}
	public void setCountVisible(boolean isCountVisible) {
		this.isCountVisible = isCountVisible;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
