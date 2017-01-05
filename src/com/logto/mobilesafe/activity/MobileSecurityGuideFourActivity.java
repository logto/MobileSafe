package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.logto.mobilesafe.R;

public class MobileSecurityGuideFourActivity extends BaseSetupActivity {
	private Button btn_commplete;
	private Button btn_pre;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_security_guide_four);
		initView();
		setOnListeners();
	}

	private void setOnListeners() {
		btn_commplete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				enterNextPage();
			}
		});
		
		btn_pre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				enterPrePage();
			}
		});
	}

	private void initView() {
		btn_commplete = (Button) findViewById(R.id.bt_commplete);
		btn_pre = (Button) findViewById(R.id.bt_pre);
		sp = getSharedPreferences("config", MODE_PRIVATE);
	}

	@Override
	public void enterNextPage() {
		//修改sp  mobileSecurityGuide中的值
		Editor editor = sp.edit();
		editor.putBoolean("mobileSecurityGuide", true);
		editor.commit();
		//跳转到手机防盗主界面
		Intent intent = new Intent(MobileSecurityGuideFourActivity.this,MobileSecurityActivity.class);
		startActivity(intent);
		//关闭当前页面
		finish();
	}

	@Override
	public void enterPrePage() {
		Intent intent = new Intent(MobileSecurityGuideFourActivity.this,MobileSecurityGuideThreeActivity.class);
		startActivity(intent);
		finish();
		//设置上一个页面进入的动画(此方法必须放置在finish()或者startActivity的后面)
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}


}
