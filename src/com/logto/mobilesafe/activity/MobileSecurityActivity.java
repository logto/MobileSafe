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
		//�ж��Ƿ������ֻ������������򵼣�û������������ý���
		//ͨ�������Ƿ����������������򵼣�һ��ͨ��SharedPreferences���б�ʶ
		isSetMobileSecurityGuide();
		setContentView(R.layout.activity_mobile_security);
	}

	private void isSetMobileSecurityGuide() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		if(!sp.getBoolean("mobileSecurityGuide", false)){
			//û��������ת���ֻ����������򵼵Ľ���
			Intent intent = new Intent(this,MobileSecurityGuideOneActivity.class);
			startActivity(intent);
			//�رյ�ǰ���� 
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
