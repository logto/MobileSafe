package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.logto.mobilesafe.R;

public class MobileSecurityActivity extends Activity {
	private SharedPreferences sp;
	private Button btn_reEnter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//判断是否做过手机防盗的设置向导；没有则进入向导设置界面
		//通常配置是否做过防盗的设置向导，一般通过SharedPreferences进行标识
		isSetMobileSecurityGuide();
		setContentView(R.layout.activity_mobile_security);
		initView();
		setListeners();
	}
	
	/**
	 *点击事件
	 */
	private void setListeners() {
		btn_reEnter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EnterSetteing();
			}
		});
	}
	
	/**
	 * 初始化控件
	 */
	private void initView() {
		btn_reEnter = (Button) findViewById(R.id.btn_reEnterSettingGuide);
	}
	
	/**
	 * 判断是否已经设置过手机防盗
	 */
	private void isSetMobileSecurityGuide() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		if(!sp.getBoolean("mobileSecurityGuide", false)){
			EnterSetteing();
		}
	}
	/**
	 * 进入手机防盗设置界面
	 */
	private void EnterSetteing() {
		//没有设置则转到手机防盗设置向导的界面
		Intent intent = new Intent(MobileSecurityActivity.this,MobileSecurityGuideOneActivity.class);
		startActivity(intent);
		//关闭当前界面 
		finish();
	}

}
