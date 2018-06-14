package com.bealink.zhengwuy.bealink.bean.response;

/**
 * Created by zhengwuy on 2018/6/14.
 * email: 13802885114@139.com
 * des:
 */
public class LoginResponseBean {

    /**
     * resultCode : 200
     * resultMessage :
     * resultData : true
     */

    private int resultCode;
    private String resultMessage;
    private boolean resultData;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public boolean isResultData() {
        return resultData;
    }

    public void setResultData(boolean resultData) {
        this.resultData = resultData;
    }
}
