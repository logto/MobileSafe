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
	private SettingItemView siv_bind_sim;//��ѡ��
	private TelephonyManager tm;//�绰���� -- ��ȡSIM����Ϣ  ��ͦ����͹Ҷ�
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

		//�л��Ƿ��סSIM��
		siv_bind_sim.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//����󱣴�SIM��  ͬʱ�ı临ѡ���״̬
				//1.��ȡSIM���Ĵ���
				//tm.getLine1Number();//�õ�SIM���ĵ绰--һ���ò���
				String serialNumber = tm.getSimSerialNumber();//�õ��͵绰��Ψһ�󶨵Ĵ���
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
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//ʵ����ϵͳ����
		//��ʼ����ѡ��Ĺ�ѡ״̬
 		if(!TextUtils.isEmpty(sp.getString("SIM", null))){
			siv_bind_sim.setChecked(true);
		}
	}

	@Override
	public void enterNextPage() {
		//���û�а�SIM����Ӧ�ý��뵽��һ���ı��sim��ҳ��
		//�ֻ�������ǰ�᣺��sim���������ð�ȫ���룬��������δʵ������ʵ���ֻ���������
		if(TextUtils.isEmpty(sp.getString("SIM", null))){
			Toast.makeText(MobileSecurityGuideTwoActivity.this, "SIM��δ��,���SIM��", 0).show();
			return;
		}
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
