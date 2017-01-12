package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.service.AddressService;
import com.logto.mobilesafe.service.GPSService;
import com.logto.mobilesafe.utils.MobileSafeUtil;
import com.logto.mobilesafe.view.SettingItemView;

public class SettingActivity extends Activity {
	private SettingItemView siv_update;
	private SettingItemView siv_call_address;
	//�������õ�״̬
	private SharedPreferences sp;
	//����������ʾ�ķ���
	Intent addressIntent;
	private boolean isRunning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
		setOnListeners();

	}

	@Override
	protected void onResume() {
		super.onResume();
		//��ʼ����ѡ״̬
		boolean isAutoUpdate = sp.getBoolean("update", false);
		siv_update.setChecked(isAutoUpdate);

		if(isAutoUpdate){
			siv_update.setDescription(getResources().getString(R.string.auto_update_on));
		}else {
			siv_update.setDescription(getResources().getString(R.string.auto_update_off));
		}

		//��ʼ����ѡ״̬
		boolean isRunning = MobileSafeUtil.isServiceRunning(getApplication(), "com.logto.mobilesafe.service.AddressService");
		siv_call_address.setChecked(isRunning);

		if(isRunning){
			siv_call_address.setDescription(getResources().getString(R.string.call_address_on));
		}else {
			siv_call_address.setDescription(getResources().getString(R.string.call_address_off));
		}
	}

	private void initView() {
		//��������
		addressIntent = new Intent(SettingActivity.this,AddressService.class);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		siv_update = (SettingItemView) findViewById(R.id.siv_update);
		siv_call_address = (SettingItemView) findViewById(R.id.siv_call_address);
	}

	private void setOnListeners() {
		final Editor editor = sp.edit();
		//�����Ͽؼ��������Ͽؼ��Ĺ�ѡ״̬
		siv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//�õ���ѡ״̬
				if (siv_update.isChecked()) {
					siv_update.setChecked(false);
					siv_update.setDescription(getResources().getString(R.string.auto_update_off));
					editor.putBoolean("update", false);
				}else {
					siv_update.setChecked(true);
					siv_update.setDescription(getResources().getString(R.string.auto_update_on));
					editor.putBoolean("update", true);	
				}
				editor.commit();
			}
		});

		siv_call_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//�õ���ѡ״̬
				isRunning = MobileSafeUtil.isServiceRunning(getApplication(), "com.logto.mobilesafe.service.AddressService");
				if (isRunning) {
					siv_call_address.setChecked(false);
					siv_call_address.setDescription(getResources().getString(R.string.call_address_off));
					stopService(addressIntent);
				}else {
					siv_call_address.setChecked(true);
					siv_call_address.setDescription(getResources().getString(R.string.call_address_on));
					startService(addressIntent);
				}
			}
		});
	}
}
