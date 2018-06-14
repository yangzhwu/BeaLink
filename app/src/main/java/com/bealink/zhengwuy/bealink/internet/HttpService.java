package com.bealink.zhengwuy.bealink.internet;

import com.bealink.zhengwuy.bealink.bean.request.LoginRequestBean;
import com.bealink.zhengwuy.bealink.bean.request.RegisterRequestBean;
import com.bealink.zhengwuy.bealink.bean.response.LoginResponseBean;
import com.bealink.zhengwuy.bealink.bean.response.RegisterResponseBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhengwuy on 2017/1/31.
 * Email: 963460692@qq.com
 * description: all http api in there, use retrofit
 */

public interface HttpService {
    @POST("user/login")
    Observable<LoginResponseBean> login(@Body LoginRequestBean loginRequestBean);

    @POST("user/signUp")
    Observable<RegisterResponseBean> register(@Body RegisterRequestBean registerRequestBean);

//    @GET("s6/weather/forecast")
//    Observable<WeatherBean> getWeatherInfo(@Query("location") String location, @Query("key") String key);
//
//
//    @GET("v5/suggestion")
//    Observable<SuggestionDataBean> getSuggestionInfo(@Query("city") String location, @Query("key") String key);
//
//    @GET("v5/now")
//    Observable<NowDataBean> getNowInfo(@Query("city") String location, @Query("key") String key);
}
