package com.logto.mobilesafe.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logto.mobilesafe.R;

public class MobileSecurityGuideThreeActivity extends BaseSetupActivity {
	private Button btn_next;
	private Button btn_pre;
	
	private EditText et_safe_number;
	private Button bt_selectContact;
	private String number_current;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_security_guide_three);
		initView();
		setListeners();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		et_safe_number.setText(sp.getString("SAFE_NUMBER", null));
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
		
		//������ϵ���б�
		bt_selectContact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.������ϵ���б�
				Intent intent = new Intent(MobileSecurityGuideThreeActivity.this,SelectorContactActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initView() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		btn_next = (Button) findViewById(R.id.bt_next);
		btn_pre = (Button) findViewById(R.id.bt_pre);
		bt_selectContact = (Button) findViewById(R.id.bt_selectContact);
		et_safe_number = (EditText) findViewById(R.id.et_guid_safe_number);
	}

	@Override
	public void enterNextPage() {
		//���û�����ð�ȫ������Ӧ�ý��뵽�¸�ҳ��
		//�ֻ�������ǰ�᣺��sim���������ð�ȫ���룬��������δʵ������ʵ���ֻ���������
		number_current = et_safe_number.getText().toString();
		if(TextUtils.isEmpty(number_current)){
			Toast.makeText(MobileSecurityGuideThreeActivity.this, "��ȫ����δ���ã������ð�ȫ����", 0).show();
			return;
		}
		//�жϺ����Ƿ���ȷ
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(number_current);  
		if(!m.matches()){
			Toast.makeText(MobileSecurityGuideThreeActivity.this, "��������ȷ�ĵ绰����", 0).show();
			return;
		}
		
		//���氲ȫ����
		Editor editor = sp.edit();
		editor.putString("SAFE_NUMBER",number_current);
		editor.commit();
		
		Intent intent = new Intent(MobileSecurityGuideThreeActivity.this,MobileSecurityGuideFourActivity.class);
		startActivity(intent);
		finish();
		//������һ��ҳ�����Ķ���(�˷������������finish()����startActivity�ĺ���)
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
	}

	@Override
	public void enterPrePage() {
		Intent intent = new Intent(MobileSecurityGuideThreeActivity.this,MobileSecurityGuideTwoActivity.class);
		startActivity(intent);
		finish();
		//������һ��ҳ�����Ķ���(�˷������������finish()����startActivity�ĺ���)
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

}
