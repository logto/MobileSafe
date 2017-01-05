package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.logto.mobilesafe.R;

public class MobileSecurityGuideTwoActivity extends BaseSetupActivity {
	private Button btn_next;
	private Button btn_pre;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_security_guide_two);
		initView();
		setListeners();
	}

	private void setListeners() {
		//跳转到3页
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				enterNextPage();
			}
		});
		//回到2页
		btn_pre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				enterPrePage();
			}
		});
	}

	private void initView() {
		btn_next = (Button) findViewById(R.id.bt_next);
		btn_pre = (Button) findViewById(R.id.bt_pre);
	}

	@Override
	public void enterNextPage() {
		Intent intent = new Intent(MobileSecurityGuideTwoActivity.this, MobileSecurityGuideThreeActivity.class);
		startActivity(intent);
		finish();
		//设置下一个页面进入的动画(此方法必须放置在finish()或者startActivity的后面)
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void enterPrePage() {
		Intent intent = new Intent(MobileSecurityGuideTwoActivity.this, MobileSecurityGuideOneActivity.class);
		startActivity(intent);
		finish();
		//设置上一个页面进入的动画(此方法必须放置在finish()或者startActivity的后面)
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		
	}

}
