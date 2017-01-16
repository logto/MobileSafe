package com.logto.mobilesafe.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.logto.mobilesafe.entitiy.BlackContactInfo;

public class BlackContactDao {
	private BlackContactDBOpenHelper helper;
	public BlackContactDao(Context context) {
		this.helper = new BlackContactDBOpenHelper(context);
	}

	/**
	 * 添加一条黑名单数据
	 * @param number 黑名单号码
	 * @param mode 拦截模式： 0--电话拦截 1--短信拦截 2--全部拦截
	 */
	public void add(String number,String mode){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("mode", mode);
		db.insert("blacecontact", null, values);
		db.close();
	}

	/**
	 * 删除一条黑名单
	 * @param number 要删除的黑名单数据
	 */
	public void delete(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		db.delete("blacecontact", "number=?", new String[]{number});
		db.close();
	}

	/**
	 * 根据电话号码，修改拦截模式
	 * @param number 要修改的电话号码
	 * @param newMode 新的拦截模式
	 */
	public void update(String number,String newMode){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mode", newMode);
		db.update("blacecontact", values, "number=?", new String[]{number});
		db.close();
	}
	
	/**
	 * 查询某个号码是否存在于黑名单中
	 * @param number 被查询的号码
	 * @return 需要返回的值
	 */
	public boolean query(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		boolean result = false;
		Cursor cursor = db.query("blacecontact", null, "number=?", new String[]{number}, null, null, null);
		if(cursor!=null&&cursor.moveToNext()){
			result = true;
		}
		db.close();
		return result;
	}
	
	public List<BlackContactInfo> queryAll(){
		SQLiteDatabase db = helper.getWritableDatabase();
		List<BlackContactInfo> blackContactInfos  = new ArrayList<BlackContactInfo>();
		Cursor cursor = db.query("blacecontact", null, null, null, null, null, null);
		if(cursor==null){
			return blackContactInfos;
		}
		while (cursor.moveToNext()) {
			BlackContactInfo blackContact = new BlackContactInfo();
			blackContact.setMode(cursor.getString(cursor.getColumnIndex("mode")));
			blackContact.setNumber(cursor.getString(cursor.getColumnIndex("number")));
			blackContactInfos.add(blackContact);
		}
		db.close();
		return blackContactInfos;
	}

	/**
	 * 查询某个号码的拦截模式
	 * @param number 被查询的号码
	 * @return 返回拦截模式  0--电话拦截 1--短信拦截 2--全部拦截 3--未拦截
	 */
	public String queryMode(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		String result = "3";
		Cursor cursor = db.query("blacecontact", new String []{"mode"}, "number=?", new String[]{number}, null, null, null);
		if(cursor!=null&&cursor.moveToNext()){
			result = cursor.getString(0);
		}
		db.close();
		return result;
	}
}
