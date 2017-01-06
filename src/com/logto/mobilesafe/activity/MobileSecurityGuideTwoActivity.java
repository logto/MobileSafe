package com.logto.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

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
				Editor editor = sp.edit();
				if(siv_bind_sim.isChecked()){
					siv_bind_sim.setChecked(false);
					editor.putString("SIM", null);
				}else {
					siv_bind_sim.setChecked(true);
					editor.putString("SIM", serialNumber);
				}
				editor.commit();
			}
		});
	}

	private void initView() {
		btn_next = (Button) findViewById(R.id.bt_next);
		btn_pre = (Button) findViewById(R.id.bt_pre);
		siv_bind_sim = (SettingItemView) findViewById(R.id.siv_bind_sim);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//实力化系统服务
		//初始化复选框的勾选状态
 		if(!TextUtils.isEmpty(sp.getString("SIM", null))){
			siv_bind_sim.setChecked(true);
		}
	}

	@Override
	public void enterNextPage() {
		//如果没有绑定SIM卡则不应该进入到下一步的变更sim的页面
		//手机防盗的前提：绑定sim卡，并设置安全号码，以上两部未实现则不能实现手机防盗功能
		if(TextUtils.isEmpty(sp.getString("SIM", null))){
			Toast.makeText(MobileSecurityGuideTwoActivity.this, "SIM卡未绑定,请绑定SIM卡", 0).show();
			return;
		}
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
