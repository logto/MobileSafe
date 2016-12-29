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
		//�ж��Ƿ������ֻ������������򵼣�û������������ý���
		//ͨ�������Ƿ����������������򵼣�һ��ͨ��SharedPreferences���б�ʶ
		isSetMobileSecurityGuide();
		setContentView(R.layout.activity_mobile_security);
		initView();
		setListeners();
	}
	
	/**
	 *����¼�
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
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		btn_reEnter = (Button) findViewById(R.id.btn_reEnterSettingGuide);
	}
	
	/**
	 * �ж��Ƿ��Ѿ����ù��ֻ�����
	 */
	private void isSetMobileSecurityGuide() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		if(!sp.getBoolean("mobileSecurityGuide", false)){
			EnterSetteing();
		}
	}
	/**
	 * �����ֻ��������ý���
	 */
	private void EnterSetteing() {
		//û��������ת���ֻ����������򵼵Ľ���
		Intent intent = new Intent(MobileSecurityActivity.this,MobileSecurityGuideOneActivity.class);
		startActivity(intent);
		//�رյ�ǰ���� 
		finish();
	}

}
