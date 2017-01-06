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
		
		//进入联系人列表
		bt_selectContact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.启动联系人列表
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
		//如果没有设置安全号码则不应该进入到下个页面
		//手机防盗的前提：绑定sim卡，并设置安全号码，以上两部未实现则不能实现手机防盗功能
		number_current = et_safe_number.getText().toString();
		if(TextUtils.isEmpty(number_current)){
			Toast.makeText(MobileSecurityGuideThreeActivity.this, "安全号码未设置，请设置安全号码", 0).show();
			return;
		}
		//判断号码是否正确
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher m = p.matcher(number_current);  
		if(!m.matches()){
			Toast.makeText(MobileSecurityGuideThreeActivity.this, "请输入正确的电话号码", 0).show();
			return;
		}
		
		//保存安全号码
		Editor editor = sp.edit();
		editor.putString("SAFE_NUMBER",number_current);
		editor.commit();
		
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
