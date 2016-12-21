package com.logto.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.logto.mobilesafe.R;

public class SettingItemView extends RelativeLayout{
	private CheckBox cb_status;
	private TextView tv_desc;
	private TextView tv_title;
	
	private String title;
	private String update_off;
	private String update_on;
	//需要设置样式时使用
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	//在布局文件实例化时使用
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.logto.mobilesafe","title");
		update_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.logto.mobilesafe", "update_off");
		update_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.logto.mobilesafe", "update_on");
		
		tv_title.setText(title);
		tv_desc.setText(update_off);
		
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
		tv_title = (TextView) findViewById(R.id.tv_title);
		
		
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
		if(isChecked){
			setDescription(update_on);
		}else {
			setDescription(update_off);
		}
	}
	
	/**
	 * 设置组合控件的的状态信息
	 * @param description
	 */
	public void setDescription(String description){
		tv_desc.setText(description);
	}
	

	
}
