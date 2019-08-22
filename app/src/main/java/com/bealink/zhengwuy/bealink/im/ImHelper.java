package com.bealink.zhengwuy.bealink.im;

import android.content.Context;

import com.bealink.zhengwuy.bealink.bean.request.LoginRequestBean;
import com.bealink.zhengwuy.bealink.bean.request.RegisterRequestBean;
import com.bealink.zhengwuy.bealink.bean.response.LoginResponseBean;
import com.bealink.zhengwuy.bealink.bean.response.RegisterResponseBean;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.exceptions.HyphenateException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhengwuy on 2019/8/20.
 * email: 13802885114@139.com
 * des:
 */
public class ImHelper {
    private static ImHelper sImHelper = new ImHelper();
    private ImListenerManager mImListenerManager;

    private ImHelper() {}

    public static ImHelper getInstance() {
        return sImHelper;
    }

    public void init(Context context) {
        //环信sdk初始化
        EMOptions emOptions = new EMOptions();
        //是否自动接受好友申请
        emOptions.setAcceptInvitationAlways(true);
        emOptions.setAutoLogin(true);
        emOptions.setRequireAck(true);
        emOptions.setRequireDeliveryAck(true);
        EaseUI.getInstance().init(context, emOptions);

        mImListenerManager = ImListenerManager.getInstance();

    }

    public void login(LoginRequestBean loginRequestBean, Observer<LoginResponseBean> observer) {
        Observable.create((ObservableOnSubscribe<LoginResponseBean>) emitter -> EMClient.getInstance()
                .login(loginRequestBean.getAccount(), loginRequestBean.getPassword(), new EMCallBack() {
            @Override
            public void onSuccess() {
                emitter.onNext(new LoginResponseBean());
            }

            @Override
            public void onError(int code, String error) {
                emitter.onError(new Exception(error));
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        })).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void register(RegisterRequestBean registerRequestBean, Observer<RegisterResponseBean> observer) {
        Observable.create((ObservableOnSubscribe<RegisterResponseBean>) emitter -> {
            try {
                EMClient.getInstance().createAccount(registerRequestBean.getAccount(), registerRequestBean.getPassword());
                emitter.onNext(new RegisterResponseBean());
            } catch (HyphenateException e) {
                e.printStackTrace();
                emitter.onError(new Exception(e.getMessage()));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void logout(Observer<Object> observer) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                       emitter.onNext(new Object());
                    }

                    @Override
                    public void onError(int code, String error) {
                        emitter.onError(new Exception(error));

                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void addFrined(String userName, Observer<Object> observer) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                EMClient.getInstance().contactManager().addContact(userName, "");
                emitter.onNext(new Object());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
