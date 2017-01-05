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
	private GestureDetector detector;//����ʶ����
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_security_guide_one);
		initView();
		setListeners();
	}

	private void setListeners() {
		//������һ������ҳ��
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
		//������һ������ҳ��
		Intent intent = new Intent(MobileSecurityGuideOneActivity.this, MobileSecurityGuideTwoActivity.class);
		startActivity(intent);
		//��ǰҳ��ر�
		finish();
		//������һ��ҳ�����Ķ���(�˷������������finish()����startActivity�ĺ���)
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void enterPrePage() {
		onBackPressed();
	}


}
