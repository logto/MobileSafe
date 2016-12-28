package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.logto.mobilesafe.R;

public class MobileSecurityGuideOneActivity extends Activity {
	private Button bt_next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_security_guide_one);
		initView();
		setListeners();
	}
	
	private void setListeners() {
		bt_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MobileSecurityGuideOneActivity.this, MobileSecurityGuideTwoActivity.class);
				startActivity(intent);
				//当前页面关闭
				finish();
			}
		});
	}

	private void initView() {
		bt_next = (Button) findViewById(R.id.bt_next);
	}



}
