package com.logto.mobilesafe.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.entitiy.BlackContactInfo;
import com.logto.mobilesafe.utils.BlackContactDao;

public class CallSmsSafeActivity extends Activity {
	private LinearLayout ll_layout;
	private Button btn_add;
	private ListView lv_blackContact;
	private List<BlackContactInfo> blackContactInfos;
	private Handler handler;
	
	private BlackContactDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_sms_safe);

		iniView();
		setListView();
	}
	@Override
	protected void onResume() {
		super.onResume();
		handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				lv_blackContact.setAdapter(new BlackContactAdater());
				ll_layout.setVisibility(View.INVISIBLE);
				lv_blackContact.setVisibility(View.VISIBLE);
			};
		};
	}

	private void setListView() {
		dao = new BlackContactDao(this); 
		//查询数据库，是联网耗时的操作
		new Thread(){
			public void run() {
				blackContactInfos = dao.querySegment("20");
				SystemClock.sleep(2000);
				handler.sendEmptyMessage(0);
			};
		}.start();
		
		lv_blackContact.setOnScrollListener(new OnScrollListener() {
			
			//listview状态发生改变时被调用
			//静止-->滑动  滑动-->静止  滑动-->滚动  
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE://静止
					break;
				case OnScrollListener.SCROLL_STATE_FLING://滚动状态
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸滑动状态
					break;
				default :
					break;
				}
			}
			//滚动的时候，回调此方法
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void iniView() {
		btn_add = (Button) findViewById(R.id.btn_addContacts);
		lv_blackContact = (ListView) findViewById(R.id.lv_blackContact);
		ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
		if(blackContactInfos == null){
			lv_blackContact.setVisibility(View.INVISIBLE);
			ll_layout.setVisibility(View.VISIBLE);
		}
	}

	private class BlackContactAdater extends BaseAdapter {

		@Override
		public int getCount() {
			return blackContactInfos.size();
		}

		@Override
		public BlackContactInfo getItem(int position) {
			return blackContactInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			// 通过加了 if (convertView != null) 这个判断条件。就省略了重复创建的浪费资源的情况
			if (convertView == null) {
				convertView = View.inflate (CallSmsSafeActivity.this, R.layout.item_lv_callsmssafe, null);
				holder = new ViewHolder();

				holder. tv_item_mode = (TextView) convertView.findViewById(R.id. tv_lv_mode);
				holder. tv_item_number = (TextView) convertView.findViewById(R.id. tv_lv_number);
				//可以将 setTag 理解成为一种容器方法。先将初始化好的 holder 存储在 convertView 里边
				convertView.setTag(holder);
			} else {
				//得到的 holder 是已经 findviewbyId 好了的。就不用再去 findviewbyid 了。也是一种优化
				holder = (ViewHolder) convertView.getTag();
				/*
		       加载数据的方法不能写在这里边。因为刚开始 convertVie==null 的时候是不执行的。也就是将
		       原来自己布局中的数据记载到了屏幕上
		       holder.tv_item_word.setText(data.get(position).getPinyin().substring(0, 1));
		        holder.tv_item_name.setText(data.get(position).getName());*/
			}
			// 装配数据
			holder.tv_item_number .setText(  blackContactInfos .get(position).getNumber());
			String mode = blackContactInfos .get(position).getMode();
			if(mode.equals("0")){
				holder.tv_item_mode.setText("电话拦截");
			}else if(mode.equals("1")){
				holder.tv_item_mode.setText("短信拦截");
			}else {
				holder.tv_item_mode.setText("全部拦截");
			}
			return convertView;
		}
	}


	static class ViewHolder {
		private TextView tv_item_mode;
		private TextView tv_item_number;
	}

}
