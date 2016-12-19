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
	//保存设置的状态
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
		
		//初始化勾选状态
		boolean isAutoUpdate = sp.getBoolean("update", false);
		siv_update.setChecked(isAutoUpdate);
		
		if(isAutoUpdate){
			siv_update.setDescription(getResources().getString(R.string.auto_update_off));
		}else {
			siv_update.setDescription(getResources().getString(R.string.auto_update_on));
		}
			
	}

	private void setOnListeners() {
		//点击组合控件，变更组合控件的勾选状态
		siv_update.setOnClickListener(new OnClickListener() {
			Editor editor = sp.edit();
			@Override
			public void onClick(View v) {
				//得到勾选状态
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
