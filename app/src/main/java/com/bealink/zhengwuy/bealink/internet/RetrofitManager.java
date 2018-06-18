package com.bealink.zhengwuy.bealink.internet;

import android.text.TextUtils;
import android.util.Log;

import com.bealink.zhengwuy.bealink.BuildConfig;
import com.bealink.zhengwuy.bealink.bean.request.LoginRequestBean;
import com.bealink.zhengwuy.bealink.bean.request.RegisterRequestBean;
import com.bealink.zhengwuy.bealink.bean.response.BaseResponseBean;
import com.bealink.zhengwuy.bealink.bean.response.LoginResponseBean;
import com.bealink.zhengwuy.bealink.bean.response.RegisterResponseBean;
import com.bealink.zhengwuy.bealink.constant.Constants;
import com.bealink.zhengwuy.bealink.rxjava.ServerResponseFun;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhengwuy on 2017/2/17.
 * Email: 963460692@qq.com
 * description: manager the retrofit
 */

public class RetrofitManager {
    private static final int CONNECT_TIME_OUT = 5;
    private static final int READ_TIME_OUT = 5;
    private static RetrofitManager mRetrofitManager;
    private static final int RETRY_TIMES = 3;
    private HttpService mHttpService;

    private RetrofitManager() {
        initRetrofit();
    }

    private void initRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //debug模式下打印网络日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
                if (!TextUtils.isEmpty(message)) {
                    Log.d("response", message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
            builder.addInterceptor(chain -> {
                Request request = chain.request();
                Request.Builder builder1 = request.newBuilder().header("Content-Type", "application/json")
                        .header("Accept", "application/json");
                Request requestNew = builder1.build();
                return chain.proceed(requestNew);
            });
        }
        //设置超时
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
//        //https验证
//        builder.hostnameVerifier((hostname, session) -> true);
//        MyTrustManager myTrustManager = new MyTrustManager();
//        builder.sslSocketFactory(myTrustManager.getSSLContext().getSocketFactory(), myTrustManager.getX509TrustManager());

        //配置retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.ApiConstant.URL).build();
        mHttpService = retrofit.create(HttpService.class);
    }

    public static RetrofitManager getIntance() {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }
        return mRetrofitManager;
    }

//    /**
//     * 获取天气信息
//     * @param location location
//     */
//    public Observable<WeatherBean> getWeather(String location) {
//        return mHttpService.getWeatherInfo(location, Constants.ApiConstant.WEATHER_KRY).retry(RETRY_TIMES).subscribeOn(Schedulers.io());
//    }
//
//    /**
//     * 获取生活建议
//     * @param location 位置信息
//     */
//    public Observable<SuggestionDataBean> getSuggestion(String location) {
//        return mHttpService.getSuggestionInfo(location, Constants.ApiConstant.WEATHER_KRY).retry(RETRY_TIMES).subscribeOn(Schedulers.io());
//    }
//
//    /**
//     * 获取实况天气
//     * @param location 位置信息
//     */
//    public Observable<NowDataBean> getNow(String location) {
//        return mHttpService.getNowInfo(location, Constants.ApiConstant.WEATHER_KRY).retry(RETRY_TIMES).subscribeOn(Schedulers.io());
//    }

    public void login(LoginRequestBean loginRequestBean, Observer<LoginResponseBean> observer) {
        defaultSchedule(mHttpService.login(loginRequestBean).retry(RETRY_TIMES), observer);
    }

    public void register(RegisterRequestBean registerRequestBean, Observer<RegisterResponseBean> observer) {
        defaultSchedule(mHttpService.register(registerRequestBean).retry(RETRY_TIMES), observer);
    }

    /**
     * 封装返回后的结果在主线程中调用
     * @param observable 网络线程
     * @param observer 网络返回的回调
     * @param <T> 泛型
     */
    private <T extends BaseResponseBean> void defaultSchedule(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new ServerResponseFun<>())
                .subscribe(observer);
    }
}
