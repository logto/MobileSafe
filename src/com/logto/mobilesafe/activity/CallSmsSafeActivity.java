package com.logto.mobilesafe.activity;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.entitiy.BlackContactInfo;
import com.logto.mobilesafe.utils.BlackContactDao;

public class CallSmsSafeActivity extends Activity {
	private Button btn_add;
	private ListView lv_blackContact;
	private List<BlackContactInfo> blackContactInfos;
	
	private BlackContactDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_sms_safe);
		
		iniView();
		setListView();
	}

	private void setListView() {
		dao = new BlackContactDao(this); 
		blackContactInfos = dao.queryAll();
		lv_blackContact.setAdapter(new BlackContactAdater());
	}

	private void iniView() {
		btn_add = (Button) findViewById(R.id.btn_addContacts);
		lv_blackContact = (ListView) findViewById(R.id.lv_blackContact);
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
			TextView tv = new TextView(CallSmsSafeActivity.this);
			tv.setTextSize(12);
			tv.setTextColor(Color.RED);
			tv.setText(blackContactInfos.get(position).toString());
			return tv;
		}
		
	}

}
