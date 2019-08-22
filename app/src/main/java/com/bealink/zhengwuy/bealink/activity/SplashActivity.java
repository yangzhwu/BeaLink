package com.bealink.zhengwuy.bealink.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.constant.Constants;
import com.bealink.zhengwuy.bealink.utils.SharedPreferenceHelper;
import com.bealink.zhengwuy.bealink.utils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {

//    private static final String TAG = "SplashActivity";
    private BGABanner mBackgroundBanner;
    private BGABanner mForegroundBanner;
    private RxPermissions mRxPermissions;
    private SweetAlertDialog mSweetAlertDialog;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColorId(R.color.transparent);
        super.onCreate(savedInstanceState);

        mRxPermissions = new RxPermissions(this);
        initView();

        //第一次使用app，开屏页轮播
        if (SharedPreferenceHelper.getInstance().getBoolean(Constants.Key.KEY_IS_FIRST_USE_APP, true)) {
            setListener();
            setBannerImages();
            doRequestPermission();
        } else {
            BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
            // 设置数据源
            mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                    R.drawable.uoko_guide_background_1);
            //非第一次使用app，延时3s后调至
            Observable.timer(3, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    if (EMClient.getInstance().isLoggedInBefore()) {
                        MainActivity.start(SplashActivity.this);
                    } else {
                        LoginActivity.start(SplashActivity.this);
                    }
                    finish();
                }
            });
        }
    }

    private void initView() {
        setContentView(R.layout.activity_splash);
        mBackgroundBanner = findViewById(R.id.banner_guide_background);
        mForegroundBanner = findViewById(R.id.banner_guide_foreground);
    }

    private void setListener() {
        /*
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, 0, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                SharedPreferenceHelper.getInstance().putBoolean(Constants.Key.KEY_IS_FIRST_USE_APP, false);
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void setBannerImages() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.uoko_guide_background_1,
                R.drawable.uoko_guide_background_2,
                R.drawable.uoko_guide_background_3);

        mForegroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.uoko_guide_foreground_1,
                R.drawable.uoko_guide_foreground_2,
                R.drawable.uoko_guide_foreground_3);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();

        if (!EMClient.getInstance().isLoggedInBefore()) {
            // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
            mBackgroundBanner.setBackgroundResource(android.R.color.white);
        }
    }

    @SuppressLint("CheckResult")
    private void doRequestPermission() {
        mRxPermissions.request(Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        ).subscribe(granted -> {
            if (granted) {
                ToastUtils.show(getString(R.string.request_permission_sucess));
            } else {
                //有权限尚未申请，弹框提示用户权限申请
                showAgainPermissionDialog();
            }
        });
    }

    private void showAgainPermissionDialog() {
        if (mSweetAlertDialog == null) {
            mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.warn))
                    .setContentText(getResources().getString(R.string.warn_with_defind_permission))
                    .setCancelText(getResources().getString(R.string.cancel))
                    .showCancelButton(true)
                    .setCancelClickListener(sweetAlertDialog -> mSweetAlertDialog.dismiss())
                    .setConfirmText(getResources().getString(R.string.confirm))
                    .setConfirmClickListener(sweetAlertDialog -> {
                        mSweetAlertDialog.dismiss();
                        doRequestPermission();
                    });
        }
        mSweetAlertDialog.show();
    }
}
