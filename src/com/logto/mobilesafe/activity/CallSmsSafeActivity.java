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
		//��ѯ���ݿ⣬��������ʱ�Ĳ���
		new Thread(){
			public void run() {
				blackContactInfos = dao.querySegment("20");
				SystemClock.sleep(2000);
				handler.sendEmptyMessage(0);
			};
		}.start();
		
		lv_blackContact.setOnScrollListener(new OnScrollListener() {
			
			//listview״̬�����ı�ʱ������
			//��ֹ-->����  ����-->��ֹ  ����-->����  
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE://��ֹ
					break;
				case OnScrollListener.SCROLL_STATE_FLING://����״̬
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://��������״̬
					break;
				default :
					break;
				}
			}
			//������ʱ�򣬻ص��˷���
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
			// ͨ������ if (convertView != null) ����ж���������ʡ�����ظ��������˷���Դ�����
			if (convertView == null) {
				convertView = View.inflate (CallSmsSafeActivity.this, R.layout.item_lv_callsmssafe, null);
				holder = new ViewHolder();

				holder. tv_item_mode = (TextView) convertView.findViewById(R.id. tv_lv_mode);
				holder. tv_item_number = (TextView) convertView.findViewById(R.id. tv_lv_number);
				//���Խ� setTag ����Ϊһ�������������Ƚ���ʼ���õ� holder �洢�� convertView ���
				convertView.setTag(holder);
			} else {
				//�õ��� holder ���Ѿ� findviewbyId ���˵ġ��Ͳ�����ȥ findviewbyid �ˡ�Ҳ��һ���Ż�
				holder = (ViewHolder) convertView.getTag();
				/*
		       �������ݵķ�������д������ߡ���Ϊ�տ�ʼ convertVie==null ��ʱ���ǲ�ִ�еġ�Ҳ���ǽ�
		       ԭ���Լ������е����ݼ��ص�����Ļ��
		       holder.tv_item_word.setText(data.get(position).getPinyin().substring(0, 1));
		        holder.tv_item_name.setText(data.get(position).getName());*/
			}
			// װ������
			holder.tv_item_number .setText(  blackContactInfos .get(position).getNumber());
			String mode = blackContactInfos .get(position).getMode();
			if(mode.equals("0")){
				holder.tv_item_mode.setText("�绰����");
			}else if(mode.equals("1")){
				holder.tv_item_mode.setText("��������");
			}else {
				holder.tv_item_mode.setText("ȫ������");
			}
			return convertView;
		}
	}


	static class ViewHolder {
		private TextView tv_item_mode;
		private TextView tv_item_number;
	}

}
