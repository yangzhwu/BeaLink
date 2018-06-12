package com.bealink.zhengwuy.bealink.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView mBtnLogin;
    private View progress;
    private View mInputLayout;
    private LinearLayout mName, mPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initListener();
    }

    private void initView() {
        mBtnLogin = findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName = findViewById(R.id.input_layout_name);
        mPsw = findViewById(R.id.input_layout_psw);
    }

    private void initListener() {
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        float width = mInputLayout.getMeasuredWidth();
        inputAnimator(mInputLayout, width);

    }

    private void inputAnimator(final View view, float w) {
        float progressWidth = progress.getMeasuredWidth();
        ValueAnimator animator = ValueAnimator.ofFloat(0, (w - progressWidth) / 2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }, 2000);
            }
        });
        animator.start();

    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view, animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new LinearInterpolator() {
            private double factor = 0.15f;
            @Override
            public float getInterpolation(float input) {
                return (float) (Math.pow(2, -10 * input)
                        * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
            }
        });
        animator3.start();

    }

}
