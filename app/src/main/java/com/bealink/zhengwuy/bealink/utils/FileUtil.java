package com.bealink.zhengwuy.bealink.utils;

import android.app.Application;

/**
 * Created by zhengwuy on 2018/1/2.
 * email: 13802885114@139.com
 * des: 文件存储类
 */

public class FileUtil {
    private static final String TAG = "FileUtil";
    private static Application sApplication;

    public static void init(Application application) {
        sApplication = application;
    }
}
