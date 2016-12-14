package com.logto.mobilesafe.activity;


import com.logto.mobilesafe.R;

import android.app.Activity;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
/**
 * 主页面
 * @author logto
 */
public class HomeActivity extends Activity {
	private GridView gv_home;
	private static final String [] names = {"手机卫士","通讯卫士","应用管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
	}
	/**
	 * 初始化控件
	 */
	private void initView() {
		gv_home = (GridView) findViewById(R.id.gv_home);
	}

	
	/**
	 * 适配器
	 */
	private class HomeAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
