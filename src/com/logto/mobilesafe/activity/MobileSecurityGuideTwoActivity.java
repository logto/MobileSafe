package com.logto.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.view.SettingItemView;

public class MobileSecurityGuideTwoActivity extends BaseSetupActivity {
	private Button btn_next;
	private Button btn_pre;
	private SettingItemView siv_bind_sim;//复选框
	private TelephonyManager tm;//电话服务 -- 读取SIM卡信息  坚挺来电和挂断
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

		//切换是否记住SIM卡
		siv_bind_sim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//点击后保存SIM卡  同时改变复选框的状态
				
				//1.读取SIM卡的串号
				//tm.getLine1Number();//得到SIM卡的电话--一般拿不到
				String serialNumber = tm.getSimSerialNumber();//拿到和电话卡唯一绑定的串号
				
				
				
				if(siv_bind_sim.isChecked()){
					siv_bind_sim.setChecked(false);
				}else {
					siv_bind_sim.setChecked(true);
				}
			}
		});
	}

	private void initView() {
		btn_next = (Button) findViewById(R.id.bt_next);
		btn_pre = (Button) findViewById(R.id.bt_pre);
		siv_bind_sim = (SettingItemView) findViewById(R.id.siv_bind_sim);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//实力化系统服务
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
