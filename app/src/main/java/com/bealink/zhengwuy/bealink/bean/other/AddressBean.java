package com.bealink.zhengwuy.bealink.bean.other;

/**
 * Created by zhengwuy on 2019/8/26.
 * email: 13802885114@139.com
 * des:
 */
public class AddressBean {
    //位置信息纬度
    private double mLatitude;
    //位置信息经度
    private double mLongitude;

    public AddressBean(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
}
