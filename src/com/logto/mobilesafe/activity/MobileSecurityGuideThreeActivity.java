package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.logto.mobilesafe.R;

public class MobileSecurityGuideThreeActivity extends BaseSetupActivity {
	private Button btn_next;
	private Button btn_pre;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_security_guide_three);
		initView();
		setListeners();
	}

	private void setListeners() {
		btn_next.setOnClickListener(new OnClickListener() {
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
		btn_next = (Button) findViewById(R.id.bt_next);
		btn_pre = (Button) findViewById(R.id.bt_pre);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mobile_security_guide_three, menu);
		return true;
	}

	@Override
	public void enterNextPage() {
		Intent intent = new Intent(MobileSecurityGuideThreeActivity.this,MobileSecurityGuideFourActivity.class);
		startActivity(intent);
		finish();
		//设置下一个页面进入的动画(此方法必须放置在finish()或者startActivity的后面)
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void enterPrePage() {
		Intent intent = new Intent(MobileSecurityGuideThreeActivity.this,MobileSecurityGuideTwoActivity.class);
		startActivity(intent);
		finish();
		//设置上一个页面进入的动画(此方法必须放置在finish()或者startActivity的后面)
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

}
