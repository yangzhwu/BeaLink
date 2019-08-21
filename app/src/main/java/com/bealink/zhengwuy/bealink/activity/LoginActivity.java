package com.bealink.zhengwuy.bealink.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.bealink.zhengwuy.bealink.im.ContactManager;
import com.bealink.zhengwuy.bealink.im.ImHelper;
import com.bealink.zhengwuy.bealink.rxjava.InternetObserver;
import com.bealink.zhengwuy.bealink.utils.CommonUtil;
import com.bealink.zhengwuy.bealink.utils.ToastUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private static final int TYPE_LOGIN = 1, TYPE_REGISTER = 2;
    private TextView mBtnLogin, mBtnRegister;
    private View progress;
    private View mInputLayout;
    private LinearLayout mNameLL, mPswLL;
    private TextView mAccountTv, mPswTv;
    private String mAccount, mPsw;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    private void initView() {
        mBtnLogin = findViewById(R.id.main_btn_login);
        mBtnRegister = findViewById(R.id.main_btn_register);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mNameLL = findViewById(R.id.input_layout_name);
        mPswLL = findViewById(R.id.input_layout_psw);
        mAccountTv = findViewById(R.id.account_tv);
        mPswTv = findViewById(R.id.psw_tv);
    }

    private void initListener() {
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_login:
                if (checkInput()) {
                    CommonUtil.hideSoftInput(this, mAccountTv);
                    mNameLL.setVisibility(View.INVISIBLE);
                    mPswLL.setVisibility(View.INVISIBLE);
                    startAnimator(TYPE_LOGIN);
                }
                break;
            case R.id.main_btn_register:
                if (checkInput()) {
                    CommonUtil.hideSoftInput(this, mAccountTv);
                    mNameLL.setVisibility(View.INVISIBLE);
                    mPswLL.setVisibility(View.INVISIBLE);
                    startAnimator(TYPE_REGISTER);
                }
                break;
        }
    }

    private boolean checkInput() {
        mAccount = mAccountTv.getText().toString().trim();
        mPsw = mPswTv.getText().toString().trim();
        if (TextUtils.isEmpty(mAccount) || TextUtils.isEmpty(mPsw)) {
            ToastUtils.show("用户名和密码不能为空");
            return false;
        }
        return true;
    }

    private void startAnimator(int type) {
        int width = mInputLayout.getMeasuredWidth();
        ValueAnimator animator = ValueAnimator.ofFloat(0, width / 2);
        animator.addUpdateListener(animation -> {
            float value = (Float) animation.getAnimatedValue();
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
            params.leftMargin = (int) value;
            params.rightMargin = (int) value;
            mInputLayout.setLayoutParams(params);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progress.setVisibility(View.VISIBLE);
                mInputLayout.setVisibility(View.INVISIBLE);
                if (type == TYPE_LOGIN) {
                    login();
                } else {
                    register();
                }
            }
        });
        animator.setDuration(200);
        animator.start();
    }

    private void login() {
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setAccount(mAccount);
        loginRequestBean.setPassword(mPsw);
        ImHelper.getInstance().login(loginRequestBean, new InternetObserver<LoginResponseBean>() {
            @Override
            public void handlerError() {
                recoverAnimator();
            }

            @Override
            public void onNext(LoginResponseBean loginResponseBean) {
                ToastUtils.show("登陆成功");
                recoverAnimator();
                ContactManager.getInstance().refresh();
                MainActivity.start(LoginActivity.this);
                finish();
            }
        });
    }

    private void register() {
        RegisterRequestBean registerRequestBean = new RegisterRequestBean();
        registerRequestBean.setAccount(mAccount);
        registerRequestBean.setPassword(mPsw);
        ImHelper.getInstance().register(registerRequestBean, new InternetObserver<RegisterResponseBean>() {
            @Override
            public void handlerError() {
                recoverAnimator();
            }

            @Override
            public void onNext(RegisterResponseBean registerResponseBean) {
                ToastUtils.show("注册成功");
                recoverAnimator();
            }
        });
    }

    private void recoverAnimator() {
        progress.setVisibility(View.GONE);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);
        mInputLayout.setVisibility(View.VISIBLE);
        mNameLL.setVisibility(View.VISIBLE);
        mPswLL.setVisibility(View.VISIBLE);
    }

}
