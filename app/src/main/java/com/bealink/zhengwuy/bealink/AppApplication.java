package com.bealink.zhengwuy.bealink;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bealink.zhengwuy.bealink.im.ImHelper;
import com.bealink.zhengwuy.bealink.location.LocationManager;
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
        //初始化环信sdk
        ImHelper.getInstance().init(this);

        LocationManager.getInstance().init(this);


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
