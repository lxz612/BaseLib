package com.lxz.basesdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Description: Preference工具类
 * Created by xianzhen.li on 2018/4/12.
 */

public class PreferenceUtil {

    private static final String TAG = "PreferenceUtils";

    private static SharedPreferences preferences;

    public static SharedPreferences getPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        }
        return preferences;
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences pref = getPreferences(context);
        SharedPreferences.Editor edit = pref.edit();

        edit.putString(key, value);
        edit.apply();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences pref = getPreferences(context);
        return pref.getString(key, defaultValue);
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences pref = getPreferences(context);
        SharedPreferences.Editor edit = pref.edit();

        edit.putInt(key, value);
        edit.apply();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static int getInt(Context context, String key, Integer defaultValue) {
        SharedPreferences pref = getPreferences(context);
        return pref.getInt(key, defaultValue);
    }

    public static void setLong(Context context, String key, long value) {
        SharedPreferences pref = getPreferences(context);
        SharedPreferences.Editor edit = pref.edit();

        edit.putLong(key, value);
        edit.apply();
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, 0);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences pref = getPreferences(context);
        return pref.getLong(key, defaultValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences pref = getPreferences(context);
        SharedPreferences.Editor edit = pref.edit();

        edit.putBoolean(key, value);
        edit.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean value) {
        SharedPreferences pref = getPreferences(context);
        return pref.getBoolean(key, value);
    }

    public static void setFloat(Context context, String key, float value) {
        SharedPreferences pref = getPreferences(context);
        SharedPreferences.Editor editor = pref.edit();

        editor.putFloat(key, value);
        editor.apply();
    }

    public static float getFloat(Context context, String key) {
        return getFloat(context, key, 0);
    }

    public static float getFloat(Context context, String key, float value) {
        SharedPreferences pref = getPreferences(context);
        return pref.getFloat(key, value);
    }

    public static void removeData(Context context, String key) {
        SharedPreferences.Editor e = getPreferences(context).edit();
        e.remove(key).apply();
    }

    public static void removeData(Context context) {
        SharedPreferences.Editor e = getPreferences(context).edit();
        e.clear().apply();
    }

    // 监听器
    public static void registerOnPreferenceChange(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = getPreferences(context);
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterOnPreferenceChange(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = getPreferences(context);
        sp.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
