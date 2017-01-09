package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.logto.mobilesafe.R;

public class MobileSecurityActivity extends Activity {
	private SharedPreferences sp;
	private Button btn_reEnter;
	private TextView tv_safeNumber;
	private ImageView iv_protected;
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
	
	@Override
	protected void onResume() {
		super.onResume();
		tv_safeNumber.setText(sp.getString("SAFE_NUMBER","120"));
		if(sp.getBoolean("PROTECTED", false)){
			iv_protected.setImageResource(R.drawable.selected);
		}else {
			iv_protected.setImageResource(R.drawable.not_selected);
		}
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
		tv_safeNumber = (TextView) findViewById(R.id.tv_safeNumber);
		iv_protected = (ImageView) findViewById(R.id.iv_protected);
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
