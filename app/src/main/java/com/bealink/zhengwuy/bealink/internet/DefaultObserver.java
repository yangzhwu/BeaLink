package com.bealink.zhengwuy.bealink.internet;


import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhengwuy on 2017/2/18.
 * Email: 963460692@qq.com
 * description: 默认的rxjava回调，实现部分方法
 */

public abstract class DefaultObserver<T> implements Observer<T> {
    private static final String TAG = "DefaultObserver";


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "sucess");
    }
}
