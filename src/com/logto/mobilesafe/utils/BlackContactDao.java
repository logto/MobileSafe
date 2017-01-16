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
	 * ���һ������������
	 * @param number ����������
	 * @param mode ����ģʽ�� 0--�绰���� 1--�������� 2--ȫ������
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
	 * ɾ��һ��������
	 * @param number Ҫɾ���ĺ���������
	 */
	public void delete(String number){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		db.delete("blacecontact", "number=?", new String[]{number});
		db.close();
	}

	/**
	 * ���ݵ绰���룬�޸�����ģʽ
	 * @param number Ҫ�޸ĵĵ绰����
	 * @param newMode �µ�����ģʽ
	 */
	public void update(String number,String newMode){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mode", newMode);
		db.update("blacecontact", values, "number=?", new String[]{number});
		db.close();
	}
	
	/**
	 * ��ѯĳ�������Ƿ�����ں�������
	 * @param number ����ѯ�ĺ���
	 * @return ��Ҫ���ص�ֵ
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
	 * ��ѯĳ�����������ģʽ
	 * @param number ����ѯ�ĺ���
	 * @return ��������ģʽ  0--�绰���� 1--�������� 2--ȫ������ 3--δ����
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
