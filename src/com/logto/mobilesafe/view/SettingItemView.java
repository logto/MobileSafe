package com.logto.mobilesafe.view;

import com.logto.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class SettingItemView extends RelativeLayout{
	//需要设置样式时使用
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	//在布局文件实例化时使用
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
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
		View.inflate(context, R.layout.setting_item_view, SettingItemView.this);
		
	}
	

}
