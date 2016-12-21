package com.logto.mobilesafe.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.logto.mobilesafe.R;
/**
 * ��ҳ��
 * @author logto
 */
public class HomeActivity extends Activity {
	private static final String TAG = "HomeActivity";
	private SharedPreferences sp;
	private GridView gv_home;
	private static final String [] names = {"�ֻ���ʿ","ͨѶ��ʿ","Ӧ�ù���","���̹���","����ͳ��","�ֻ�ɱ��","��������","�߼�����","��������"};
	private static final int [] images = {R.drawable.mobile_security,R.drawable.communication_guard,R.drawable.app_manager,R.drawable.process_manager,
		R.drawable.data_center,R.drawable.mobile_antivirus,R.drawable.advance_tool,R.drawable.cache_clean,R.drawable.advance_tool,R.drawable.set_center};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		setGridView();
		setListeners();
	}
	private void setListeners() {
		//GridView�ĵ���¼�
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0://�����ֻ���ʿ
					enterMobileGuard();
					break;
				case 8:
					//��ת����������
					Intent intent = new Intent(HomeActivity.this,SettingActivity.class); 
					startActivity(intent);
					break;

				default:
					break;
				}
			}


		});

	}

	/**
	 * ���ݵ�ǰ�����������ͬ�ĶԻ���
	 */
	private void enterMobileGuard() {
		//�ж��Ƿ����������룬���û�������򵯳����öԻ˿򣬷���͵��������
		if(isSetupPassword()){
			//�Ѿ�����������
			showInputDialog();
		}else {
			//û����������
			showSetPasswordDialog();
		}

	}
	/**
	 * ��������ĶԻ���
	 */
	private void showInputDialog() {
		View view_dialog = View.inflate(this, R.layout.dialog_setup_password, null);
		final EditText et_password = (EditText) view_dialog.findViewById(R.id.et_password);
		final String sp_password = sp.getString("password","666");
		Button bt_confirm = (Button) view_dialog.findViewById(R.id.bt_confirm);
		Button bt_cancel = (Button) view_dialog.findViewById(R.id.bt_cancel);

		dialog = new AlertDialog.Builder(this).
				setView(view_dialog).create();
		dialog.show();

		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//ȡ���Ի���
				dialog.dismiss();
			}
		});
		
		bt_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.�õ�������е����루�������е����룩
				String password =  et_password.getText().toString().trim();
				//2.�ж������Ƿ�Ϊ��
				if(TextUtils.isEmpty(password)){
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", 0).show();
					return;
				}
				//3.�ж������Ƿ�����ͬ
				if(!password.equals(sp_password)){
					Toast.makeText(HomeActivity.this, "���������������������", 0).show();
					return;
				}
				//4.ȡ���Ի���
				dialog.dismiss();
				//5.���뵽�ֻ���ʿҳ��
				
			}
		});

	}
	/**
	 * ��������ĶԻ��� 
	 */
	private AlertDialog dialog;
	private void showSetPasswordDialog() {
		View view_dialog = View.inflate(this, R.layout.dialog_setup_password, null);
		  final EditText et_password = (EditText) view_dialog.findViewById(R.id.et_password);
		final EditText et_confirm = (EditText) view_dialog.findViewById(R.id.et_confirm);
		Button bt_confirm = (Button) view_dialog.findViewById(R.id.bt_confirm);
		Button bt_cancel = (Button) view_dialog.findViewById(R.id.bt_cancel);

		dialog = new AlertDialog.Builder(this).
				setView(view_dialog).create();
		dialog.show();

		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//ȡ���Ի���
				dialog.dismiss();
			}
		});
		
		bt_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.�õ�������е����루�������е����룩
				String password =  et_password.getText().toString().trim();
				String confirm_password =  et_confirm.getText().toString().trim();
				//2.�ж������Ƿ�Ϊ��
				if(TextUtils.isEmpty(password)||TextUtils.isEmpty(confirm_password)){
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", 0).show();
					return;
				}
				//3.�ж������Ƿ�����ͬ
				if(!password.equals(confirm_password)){
					Toast.makeText(HomeActivity.this, "������������벻һ�£�����������", 0).show();
					return;
				}
				//4.��ͬ�򱣴����룬�����뵽�ֻ���ʿҳ��
				Editor editor = sp.edit();
				editor.putString("password", password);
				editor.commit();
				//ȡ���Ի���
				dialog.dismiss();
				Log.e(TAG, password);
			}
		});
	}
	/**
	 * �ж��Ƿ�����������
	 * @return
	 */
	private boolean isSetupPassword(){
		String password = sp.getString("password", null);
		return !TextUtils.isEmpty(password);
	}

	private void setGridView() {
		gv_home.setAdapter(new HomeAdapter());
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		gv_home = (GridView) findViewById(R.id.gv_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);
	}


	/**
	 * ������
	 */
	private class HomeAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(HomeActivity.this, R.layout.home_gv_item, null);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

			iv_icon.setImageResource(images[position]);
			tv_name.setText(names[position]);

			return view;
		}

	}
}
