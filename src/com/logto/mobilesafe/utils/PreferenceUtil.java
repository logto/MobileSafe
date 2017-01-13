package com.logto.mobilesafe.utils;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * ����SharedPreference�Ĺ�����
 *
 * @author Pinger
 */
public class PreferenceUtil {

    /**
     * ����˽�л�
     */
    private PreferenceUtil() {
    }

    /**
     * ��ȡSharedPreferences����
     */
    private static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences("config",Context.MODE_PRIVATE);

    }

    /**
     * ��ȡBoolean���͵����ݣ�Ĭ�Ϸ���true
     *
     * @param context
     */
    public static Boolean getBoolean(Context context, String key) {
        // Ĭ�Ϸ���true
        return getSp(context).getBoolean(key, true);
    }

    /**
     * ��ȡBoolean���͵�����
     *
     * @param context
     */
    public static Boolean getBoolean(Context context, String key,
                                     boolean defaultValue) {
        return getSp(context).getBoolean(key, defaultValue);
    }

    /**
     * ��boolean���͵�ֵ�浽˽���ļ�
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        getSp(context).edit().putBoolean(key, value).commit();
    }

    /**
     * ��ȡString�������ݣ�Ĭ�Ϸ��ؿ��ַ���
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        return getSp(context).getString(key, "");
    }

    /**
     * ��ȡString��������
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key,
                                   String defaultValue) {
        return getSp(context).getString(key, defaultValue);
    }

    /**
     * �洢String���͵�����
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        getSp(context).edit().putString(key, value).commit();
    }

    /**
     * ��ȡInt�������ݣ�Ĭ��0
     *
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context, String key) {
        return getSp(context).getInt(key, 0);
    }

    /**
     * ��ȡInt��������
     *
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context, String key, int defaultValue) {
        return getSp(context).getInt(key, defaultValue);
    }

    /**
     * �洢Int���͵�����
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putInt(Context context, String key, int value) {
        getSp(context).edit().putInt(key, value).commit();
    }
}
