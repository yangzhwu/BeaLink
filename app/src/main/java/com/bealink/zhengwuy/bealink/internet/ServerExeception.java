package com.bealink.zhengwuy.bealink.internet;

/**
 * Created by zhengwuy on 2018/6/17.
 * Email: 963460692@qq.com
 * description:
 */
public class ServerExeception extends Exception {
    private int errorCode;
    private String errorMessage;

    public ServerExeception(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}