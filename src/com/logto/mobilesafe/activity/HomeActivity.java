package com.logto.mobilesafe.activity;


import com.logto.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 主页面
 * @author logto
 */
public class HomeActivity extends Activity {
	private GridView gv_home;
	private static final String [] names = {"手机卫士","通讯卫士","应用管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};
	private static final int [] images = {R.drawable.mobile_security,R.drawable.communication_guard,R.drawable.app_manager,R.drawable.process_manager,
		R.drawable.data_center,R.drawable.mobile_antivirus,R.drawable.advance_tool,R.drawable.cache_clean,R.drawable.advance_tool,R.drawable.set_center};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		setGridView();
		setListeners();
	}
	private void setListeners() {
		//GridView的点击事件
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 8:
					//跳转到设置中心
					Intent intent = new Intent(HomeActivity.this,SettingActivity.class); 
					startActivity(intent);
					break;

				default:
					break;
				}
			}
		});
		
	}
	private void setGridView() {
		gv_home.setAdapter(new HomeAdapter());
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
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity.this, R.layout.home_gv_item, null);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			
			iv_icon.setImageResource(images[position]);
			tv_name.setText(names[position]);
			
			return view;
		}
		
	}
}
