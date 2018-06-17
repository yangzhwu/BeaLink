package com.bealink.zhengwuy.bealink.utils;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import com.bealink.zhengwuy.bealink.AppApplication;

/**
 * Created by zhengwuy on 2018/6/14.
 * email: 13802885114@139.com
 * des:
 */
public class ToastUtils {
    private static Application sApplication;

    public static void init(AppApplication appApplication) {
        sApplication = appApplication;
    }

    public static void show(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(sApplication, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
