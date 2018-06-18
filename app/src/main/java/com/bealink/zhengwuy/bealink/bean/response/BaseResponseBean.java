package com.bealink.zhengwuy.bealink.bean.response;

/**
 * Created by zhengwuy on 2018/6/17.
 * Email: 963460692@qq.com
 * description:
 */
public class BaseResponseBean {
    private int resultCode;
    private String resultMessage;

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
