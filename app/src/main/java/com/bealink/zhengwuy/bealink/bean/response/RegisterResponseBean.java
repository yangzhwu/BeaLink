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

    public boolean isResultData() {
        return resultData;
    }

    public void setResultData(boolean resultData) {
        this.resultData = resultData;
    }
}
