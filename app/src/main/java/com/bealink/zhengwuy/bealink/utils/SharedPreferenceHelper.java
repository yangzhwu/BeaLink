package com.bealink.zhengwuy.bealink.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhengwuy on 2017/2/19.
 * Email: 963460692@qq.com
 * description:
 */

public class SharedPreferenceHelper {
    private static final String SHAREDPREFERENCE_NAME = "weather_preference";

    private static SharedPreferenceHelper mInstance;
    private SharedPreferences mSharedPreferences;

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (SharedPreferenceHelper.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferenceHelper();
                }
            }
        }
        mInstance.mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceHelper getInstance() {
        return mInstance;
    }

    public void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, Boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public long getLong(String key, long defaultvalue) {
        return mSharedPreferences.getLong(key, defaultvalue);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }
}
