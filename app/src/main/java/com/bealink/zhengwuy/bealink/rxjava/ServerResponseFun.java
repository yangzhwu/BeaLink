package com.bealink.zhengwuy.bealink.rxjava;


import com.bealink.zhengwuy.bealink.bean.response.BaseResponseBean;
import com.bealink.zhengwuy.bealink.constant.Constants;
import com.bealink.zhengwuy.bealink.internet.ServerExeception;

import io.reactivex.functions.Function;


/**
 * Created by zhengwuy on 2018/6/17.
 * Email: 963460692@qq.com
 * description:
 */
public class ServerResponseFun<T extends BaseResponseBean> implements Function<T, T> {

    @Override
    public T apply(T baseResponseBean) throws Exception {
        if (baseResponseBean.getResultCode() == Constants.ApiConstant.CODE_SERVER_RESPONSE_OK) {
            return baseResponseBean;
        } else {
            throw new ServerExeception(baseResponseBean.getResultCode(), baseResponseBean.getResultMessage());
        }
    }
}
