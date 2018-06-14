package com.bealink.zhengwuy.bealink;

import android.app.Application;

import com.bealink.zhengwuy.bealink.utils.FileUtil;
import com.bealink.zhengwuy.bealink.utils.LogUtil;
import com.bealink.zhengwuy.bealink.utils.SharedPreferenceHelper;
import com.bealink.zhengwuy.bealink.utils.ToastUtils;

/**
 * Created by zhengwuy on 2018/5/30.
 * email: 13802885114@139.com
 * des:
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            LogUtil.setLogLeve(LogUtil.LOG_LEVEL_D);
        }
        SharedPreferenceHelper.init(this);
        FileUtil.init(this);
        ToastUtils.init(this);
    }
}
