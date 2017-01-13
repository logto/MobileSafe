package com.logto.mobilesafe.utils;

import com.logto.mobilesafe.R;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 自定义吐司工具类
 * Created by Pinger on 2016/9/7.
 */
public class CustomToastUtil {
	private Context mContext;
	private View mView;
	private WindowManager mWm;
	private WindowManager.LayoutParams mParams;

	/**
	 * 定义点击次数的数组，数组的数字是几就可以定义几击事件
	 */
	final long[] mHits1 = new long[2];
	final long[] mHits2 = new long[3];


	/**
	 * 构造
	 * @param context
	 */
	public CustomToastUtil(Context context) {
		this.mContext = context;

		initParams();
	}

	/**
	 * 初始化窗体属性
	 */
	private void initParams() {
		mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

		mParams = new WindowManager.LayoutParams();

		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		// 类型
		mParams.type = WindowManager.LayoutParams.TYPE_PHONE;

		// 透明，不透明会出现重叠效果
		mParams.format = PixelFormat.TRANSLUCENT;

		// 位置属性
		mParams.gravity = Gravity.TOP + Gravity.LEFT;  // 左上

		// 进来的时候把存储的位置读取显示出来
		mParams.x = PreferenceUtil.getInt(mContext, "lastX");
		mParams.y = PreferenceUtil.getInt(mContext, "lastY");

		// 初始化吐司窗口布局
		mView = View.inflate(mContext, R.layout.view_toast, null);
	}


	/**
	 * 弹出自定义吐司
	 */
	public void popToast(String text) {
		TextView tvName = (TextView) mView.findViewById(R.id.tv_toast_name);
		// 设置显示的文字
		tvName.setText(text);

		// 吐司窗体的背景可以在布局文件之中指定也可以在代码中设置


		// 设置吐司的双击事件，点击之后会到中心点
		mView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 双击事件处理逻辑
				System.arraycopy(mHits1, 1, mHits1, 0, mHits1.length - 1);
				mHits1[mHits1.length - 1] = SystemClock.uptimeMillis();
				if (mHits1[0] >= (SystemClock.uptimeMillis() - 500)) {
					// 双击之后执行
					// 让吐司移动到x中心，y不需要对中
					// 更新窗体的坐标
					mParams.x = (mWm.getDefaultDisplay().getWidth() - mView.getWidth()) / 2;
					mWm.updateViewLayout(mView, mParams);

					// 点击完退出的时候也把位置信息存储起来
					PreferenceUtil.putInt(mContext, "lastX", mParams.x);
					PreferenceUtil.putInt(mContext, "lastY", mParams.y);
				}


				// 三击事件处理逻辑
				System.arraycopy(mHits2, 1, mHits2, 0, mHits2.length - 1);
				mHits2[mHits2.length - 1] = SystemClock.uptimeMillis();
				if (mHits2[0] >= (SystemClock.uptimeMillis() - 600)) {
					// 点击之后将吐司移除掉
					if (mView != null) {
						if (mView.getParent() != null) {
							mWm.removeView(mView);
						}
					}
				}
			}
		});


		// 设置吐司的触摸滑动事件
		mView.setOnTouchListener(new View.OnTouchListener() {

			int startX;
			int startY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: // 按下

				// 手指按下时的坐标位置
				startX = (int) event.getRawX();
				startY = (int) event.getRawY();

				break;
				case MotionEvent.ACTION_MOVE: // 移动

					// 移动后的坐标位置
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();

					// 偏移量
					int dx = newX - startX;
					int dy = newY - startY;

					// 给偏移量设置边距
					// 小于x轴
					if (mParams.x < 0) {
						mParams.x = 0;
					}
					// 小于y轴
					if (mParams.y < 0) {
						mParams.y = 0;
					}

					// 超出x轴
					if (mParams.x > mWm.getDefaultDisplay().getWidth() - mView.getWidth()) {
						mParams.x = mWm.getDefaultDisplay().getWidth() - mView.getWidth();
					}
					// 超出y轴
					if (mParams.y > mWm.getDefaultDisplay().getHeight() - mView.getHeight()) {
						mParams.y = mWm.getDefaultDisplay().getHeight() - mView.getHeight();
					}

					// 更新窗体的坐标
					mParams.x += dx;
					mParams.y += dy;
					mWm.updateViewLayout(mView, mParams);

					// 重新赋值起始坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP: // 抬起
					// 抬起来的时候保存最后一次的位置，下次进来时直接显示出来
					PreferenceUtil.putInt(mContext, "lastX", mParams.x);
					PreferenceUtil.putInt(mContext, "lastX", mParams.y);
					break;
				default:
					break;
				}

				return false;  // 当有父控件有点击事件时，这里要返回false，不然父控件就拿不到点击事件了
			}
		});

		if (mView != null) {
			if (mView.getParent() != null) {
				mWm.removeView(mView);
			}
		}
		// 添加到窗体管理器中才能显示出来
		mWm.addView(mView, mParams);
	}

	/**
	 * 从父窗体中移除吐司
	 */
	public void hideToast() {
		if (mView != null) {
			if (mView.getParent() != null) {
				mWm.removeView(mView);
			}
		}
	}

}
