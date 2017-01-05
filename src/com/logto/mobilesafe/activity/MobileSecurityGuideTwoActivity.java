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
		//��ת��3ҳ
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				enterNextPage();
			}
		});
		//�ص�2ҳ
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
		//������һ��ҳ�����Ķ���(�˷������������finish()����startActivity�ĺ���)
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void enterPrePage() {
		Intent intent = new Intent(MobileSecurityGuideTwoActivity.this, MobileSecurityGuideOneActivity.class);
		startActivity(intent);
		finish();
		//������һ��ҳ�����Ķ���(�˷������������finish()����startActivity�ĺ���)
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		
	}

}
