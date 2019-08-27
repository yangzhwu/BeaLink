package com.bealink.zhengwuy.bealink.bean.response;

/**
 * Created by zhengwuy on 2018/6/14.
 * email: 13802885114@139.com
 * des:
 */
public class RegisterResponseBean extends BaseResponseBean {
    /**
     * resultCode : 200
     * resultMessage :
     * resultData : true
     */
    private boolean resultData;
    private String mUserName;
    private String mPassWord;

    public void setPassWord(String passWord) {
        mPassWord = passWord;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPassWord() {
        return mPassWord;
    }

    public boolean isResultData() {
        return resultData;
    }

    public void setResultData(boolean resultData) {
        this.resultData = resultData;
    }
}
