package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.logto.mobilesafe.R;

public class MobileSecurityGuideOneActivity extends BaseSetupActivity {
	private Button bt_next;
	private SharedPreferences sp;
	private GestureDetector detector;//手势识别器
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_security_guide_one);
		initView();
		setListeners();
	}

	private void setListeners() {
		//进入下一个设置页面
		bt_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				enterNextPage();
			}
		});
	}

	private void initView() {
		bt_next = (Button) findViewById(R.id.bt_next);
	}

	@Override
	public void onBackPressed() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		if(!sp.getBoolean("mobileSecurityGuide", false)){
			finish();
		}else {
			Intent intent = new Intent(MobileSecurityGuideOneActivity.this,MobileSecurityActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void enterNextPage() {
		//进入下一个设置页面
		Intent intent = new Intent(MobileSecurityGuideOneActivity.this, MobileSecurityGuideTwoActivity.class);
		startActivity(intent);
		//当前页面关闭
		finish();
		//设置下一个页面进入的动画(此方法必须放置在finish()或者startActivity的后面)
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void enterPrePage() {
		onBackPressed();
	}


}
