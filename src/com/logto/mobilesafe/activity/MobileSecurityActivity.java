package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import com.logto.mobilesafe.R;

public class MobileSecurityActivity extends Activity {
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//判断是否做过手机防盗的设置向导；没有则进入向导设置界面
		//通常配置是否做过防盗的设置向导，一般通过SharedPreferences进行标识
		isSetMobileSecurityGuide();
		setContentView(R.layout.activity_mobile_security);
	}

	private void isSetMobileSecurityGuide() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		if(!sp.getBoolean("mobileSecurityGuide", false)){
			//没有设置则转到手机防盗设置向导的界面
			Intent intent = new Intent(this,MobileSecurityGuideOneActivity.class);
			startActivity(intent);
			//关闭当前界面 
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mobile_security, menu);
		return true;
	}

}
