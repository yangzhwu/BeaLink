package com.bealink.zhengwuy.bealink.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.bean.request.LoginRequestBean;
import com.bealink.zhengwuy.bealink.bean.request.RegisterRequestBean;
import com.bealink.zhengwuy.bealink.bean.response.LoginResponseBean;
import com.bealink.zhengwuy.bealink.bean.response.RegisterResponseBean;
import com.bealink.zhengwuy.bealink.constant.Constants;
import com.bealink.zhengwuy.bealink.im.ContactManager;
import com.bealink.zhengwuy.bealink.im.ImHelper;
import com.bealink.zhengwuy.bealink.rxjava.InternetObserver;
import com.bealink.zhengwuy.bealink.utils.DialogUtil;
import com.bealink.zhengwuy.bealink.utils.ToastUtils;
import com.bealink.zhengwuy.bealink.view.CustomVideoView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_CODE = 1000;
    private TextView mBtnLogin, mBtnRegister;
    private TextView mAccountTv, mPswTv;
    private String mAccount, mPsw;
    private CustomVideoView mCustomVideoView;
    private SweetAlertDialog mSweetAlertDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarColorId(R.color.transparent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initView() {
        mSweetAlertDialog = DialogUtil.creatCommonLoadingDialog(this, "正在登陆中...");
        mBtnLogin = findViewById(R.id.main_btn_login);
        mBtnRegister = findViewById(R.id.main_btn_register);
        mAccountTv = findViewById(R.id.account_tv);
        mPswTv = findViewById(R.id.psw_tv);
        mCustomVideoView = findViewById(R.id.custom_video_view);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sport);
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(this, uri);
        mCustomVideoView.setBackground(new BitmapDrawable(getResources(), media.getFrameAtTime(10)));
        mCustomVideoView.setVideoURI(uri);
    }

    private void initListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mCustomVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                            mCustomVideoView.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    }
                });
            }
        });
//        mCustomVideoView.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_login:
                login();
                break;
            case R.id.main_btn_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                mAccountTv.setText(data.getStringExtra(Constants.Key.USER_NAME));
                mPswTv.setText(data.getStringExtra(Constants.Key.PASSWORD));
                login();
            }
        }
    }

    private void login() {
        mAccount = mAccountTv.getText().toString().trim();
        mPsw = mPswTv.getText().toString().trim();
        if (TextUtils.isEmpty(mAccount) || TextUtils.isEmpty(mPsw)) {
            ToastUtils.show("用户名和密码不能为空");
            return;
        }

        mSweetAlertDialog.show();
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setAccount(mAccount);
        loginRequestBean.setPassword(mPsw);
        ImHelper.getInstance().login(loginRequestBean, new InternetObserver<LoginResponseBean>() {
            @Override
            public void handlerError() {
                ToastUtils.show("登陆失败");
                mSweetAlertDialog.dismiss();
            }

            @Override
            public void onNext(LoginResponseBean loginResponseBean) {
                ToastUtils.show("登陆成功");
                mSweetAlertDialog.dismiss();
                ContactManager.getInstance().refresh();
                MainActivity.start(LoginActivity.this);
                finish();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCustomVideoView.start();
    }
}
