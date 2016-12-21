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
 * 主页面
 * @author logto
 */
public class HomeActivity extends Activity {
	private static final String TAG = "HomeActivity";
	private SharedPreferences sp;
	private GridView gv_home;
	private static final String [] names = {"手机卫士","通讯卫士","应用管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};
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
		//GridView的点击事件
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0://进入手机卫士
					enterMobileGuard();
					break;
				case 8:
					//跳转到设置中心
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
	 * 根据当前情况，弹出不同的对话框
	 */
	private void enterMobileGuard() {
		//判断是否设置了密码，如果没有设置则弹出设置对凰框，否则就弹出输入框
		if(isSetupPassword()){
			//已经设置有密码
			showInputDialog();
		}else {
			//没有设置密码
			showSetPasswordDialog();
		}

	}
	/**
	 * 输入密码的对话框
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
				//取消对话框
				dialog.dismiss();
			}
		});
		
		bt_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.拿到输入框中的密码（两个框中的密码）
				String password =  et_password.getText().toString().trim();
				//2.判断密码是否为空
				if(TextUtils.isEmpty(password)){
					Toast.makeText(HomeActivity.this, "密码不能为空", 0).show();
					return;
				}
				//3.判断密码是否已相同
				if(!password.equals(sp_password)){
					Toast.makeText(HomeActivity.this, "密码输入错误，请重新输入", 0).show();
					return;
				}
				//4.取消对话框
				dialog.dismiss();
				//5.进入到手机卫士页面
				
			}
		});

	}
	/**
	 * 设置密码的对话框 
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
				//取消对话框
				dialog.dismiss();
			}
		});
		
		bt_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.拿到输入框中的密码（两个框中的密码）
				String password =  et_password.getText().toString().trim();
				String confirm_password =  et_confirm.getText().toString().trim();
				//2.判断密码是否为空
				if(TextUtils.isEmpty(password)||TextUtils.isEmpty(confirm_password)){
					Toast.makeText(HomeActivity.this, "密码不能为空", 0).show();
					return;
				}
				//3.判断密码是否已相同
				if(!password.equals(confirm_password)){
					Toast.makeText(HomeActivity.this, "两次输入的密码不一致，请重新输入", 0).show();
					return;
				}
				//4.相同则保存密码，并进入到手机卫士页面
				Editor editor = sp.edit();
				editor.putString("password", password);
				editor.commit();
				//取消对话框
				dialog.dismiss();
				Log.e(TAG, password);
			}
		});
	}
	/**
	 * 判断是否设置有密码
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
	 * 初始化控件
	 */
	private void initView() {
		gv_home = (GridView) findViewById(R.id.gv_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);
	}


	/**
	 * 适配器
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
