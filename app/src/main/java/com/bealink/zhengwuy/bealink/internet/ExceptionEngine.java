package com.bealink.zhengwuy.bealink.internet;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONException;

import java.net.ConnectException;

/**
 * Created by zhengwuy on 2018/6/17.
 * Email: 963460692@qq.com
 * description:
 */
public class ExceptionEngine {
    private static final String ERROR_INTERNET_ERROR = "网络错误";
    private static final String ERROR_DATA_ERROR = "数据解析错误";
    private static final String ERROR_CONNECTION_ERROR = "网络连接失败";
    private static final String ERROR_UNKNOW_ERROR = "未知错误";

    public static String handleException(Throwable e) {
        if (e instanceof HttpException) {
            return ERROR_INTERNET_ERROR;
        } else if (e instanceof ServerExeception) {
            ServerExeception exeception = (ServerExeception) e;
            return exeception.getErrorCode() + " " + exeception.getErrorMessage();
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            return ERROR_DATA_ERROR;
        } else if(e instanceof ConnectException) {
            return ERROR_CONNECTION_ERROR;
        } else {
            return ERROR_UNKNOW_ERROR;
        }
    }
}
