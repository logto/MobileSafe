package com.logto.mobilesafe.view;

import com.logto.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingItemView extends RelativeLayout{
	private CheckBox cb_status;
	private TextView tv_desc;
	//需要设置样式时使用
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	//在布局文件实例化时使用
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		attrs.getAttributeValue("http://schemas.android.com/apk/res/com.logto.mobilesafe",
								"title");
		
	}
	
	//代码中实例化时使用
	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}
	
	/**
	 * 初始化布局文件
	 */
	private void initView(Context context) {
		//infalte方法的作用：把布局文件-->view
		//root:添加谁进来，谁就是R.layout.setting_item_view的父亲，也就是说，把此布局文件挂载在传来进来的这个控件上
		View settingView = View.inflate(context, R.layout.setting_item_view, this);
		cb_status = (CheckBox) findViewById(R.id.cb_status);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
	}
	
	/**
	 * 得到组合控件是否被勾选
	 * @return
	 */
	public boolean isChecked(){
		return  cb_status.isChecked();
 	}
	/**
	 * 设置控件的勾选状态
	 */
	public void setChecked(boolean isChecked){
		cb_status.setChecked(isChecked);
	}
	
	/**
	 * 设置组合控件的的状态信息
	 * @param description
	 */
	public void setDescription(String description){
		tv_desc.setText(description);
	}
	

	
}
