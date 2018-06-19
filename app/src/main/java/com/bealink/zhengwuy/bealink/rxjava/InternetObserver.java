package com.bealink.zhengwuy.bealink.rxjava;

import android.text.TextUtils;

import com.bealink.zhengwuy.bealink.internet.ExceptionEngine;
import com.bealink.zhengwuy.bealink.internet.ServerExeception;
import com.bealink.zhengwuy.bealink.utils.LogUtil;
import com.bealink.zhengwuy.bealink.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhengwuy on 2018/6/14.
 * email: 13802885114@139.com
 * des:
 */
public abstract class InternetObserver<T> implements Observer<T> {
    private static final String TAG = "InternetObserver";

    @Override
    public void onComplete() {
        LogUtil.d(TAG, "onComplete");
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ServerExeception) {
            ServerExeception serverExeception = (ServerExeception) e;
            LogUtil.e(TAG, serverExeception.getErrorCode() + " " + serverExeception.getErrorMessage());
        } else {
            if (e != null && TextUtils.isEmpty(e.getMessage())) {
                LogUtil.e(TAG, e.getMessage());
            }
        }
        String errorMessage = ExceptionEngine.handleException(e);
        ToastUtils.show(errorMessage);
        handlerError();
    }

    public abstract void handlerError();
}
