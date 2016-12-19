package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.view.SettingItemView;

public class SettingActivity extends Activity {
	private SettingItemView siv_update;
	//�������õ�״̬
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
		setOnListeners();
		
	}
	
	private void initView() {
		siv_update = (SettingItemView) findViewById(R.id.siv_update);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		//��ʼ����ѡ״̬
		boolean isAutoUpdate = sp.getBoolean("update", false);
		siv_update.setChecked(isAutoUpdate);
		
		if(isAutoUpdate){
			siv_update.setDescription(getResources().getString(R.string.auto_update_off));
		}else {
			siv_update.setDescription(getResources().getString(R.string.auto_update_on));
		}
			
	}

	private void setOnListeners() {
		//�����Ͽؼ��������Ͽؼ��Ĺ�ѡ״̬
		siv_update.setOnClickListener(new OnClickListener() {
			Editor editor = sp.edit();
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
		
		
		
		
	}

}
