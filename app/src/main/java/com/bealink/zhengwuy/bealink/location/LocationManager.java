package com.bealink.zhengwuy.bealink.location;

import android.annotation.SuppressLint;
import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.bean.other.AddressBean;
import com.bealink.zhengwuy.bealink.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by zhengwuy on 2019/8/26.
 * email: 13802885114@139.com
 * des: 集成高德定位sdk功能
 */
public class LocationManager {
    private static final String TAG = "LocationManager";
    private Context mContext;

    @SuppressLint("StaticFieldLeak")
    private static LocationManager sLocationManager = new LocationManager();

    private LocationManager() {

    }

    public static LocationManager getInstance() {
        return sLocationManager;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public void startLocation(Observer<AddressBean> observer) {
        AMapLocationClient aMapLocationClient = new AMapLocationClient(mContext);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setOnceLocation(true);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setLocationCacheEnable(false);
        aMapLocationClient.setLocationOption(option);

        aMapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Observable.create(new ObservableOnSubscribe<AddressBean>() {

                    @Override
                    public void subscribe(ObservableEmitter<AddressBean> emitter) throws Exception {
                        if (aMapLocation.getErrorCode() == 0) {
                            AddressBean addressBean = new AddressBean(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                            emitter.onNext(addressBean);
                        } else {
                            LogUtil.e(TAG, "定位失败 " + aMapLocation.getErrorInfo());
                            emitter.onError(new Exception(mContext.getResources().getString(R.string.location_failed)));
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(observer);

            }
        });

    }
}
