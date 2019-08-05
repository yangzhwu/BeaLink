package com.bealink.zhengwuy.bealink.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;

/**
 * Created by zhengwuy on 2018/6/22.
 * email: 13802885114@139.com
 * des:
 */
public class TabView extends LinearLayout {

    private TextView mFrontTv;
    private TextView mBackTv;
    private Context mContext;
    private boolean mIsToggling;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.tab_view_layout, this);
        mFrontTv = view.findViewById(R.id.tab_front_tv);
        mBackTv = view.findViewById(R.id.tab_back_tv);
    }

    public void setText(String frontText, String bakcText) {
        if (!TextUtils.isEmpty(frontText)) {
            mFrontTv.setText(frontText);
        }
        if (!TextUtils.isEmpty(bakcText)) {
            mBackTv.setText(bakcText);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int width = Math.max(mFrontTv.getWidth(), mBackTv.getWidth());
        int height = Math.max(mFrontTv.getHeight(), mBackTv.getHeight());
        mFrontTv.setWidth(width);
        mFrontTv.setHeight(height);
        mBackTv.setWidth(width);
        mBackTv.setHeight(height);
    }

    public synchronized void toggle() {
        if (mIsToggling) {
            return;
        }
        mIsToggling = true;
        TextView runAnimationFront, runAnimationBack;
        if (mFrontTv.getVisibility() == VISIBLE) {
            runAnimationFront = mFrontTv;
            runAnimationBack = mBackTv;
        } else {
            runAnimationFront = mBackTv;
            runAnimationBack = mFrontTv;
        }
        ObjectAnimator objectAnimatorFront = ObjectAnimator.ofFloat(runAnimationFront, "rotationY", 0, 90);
        ObjectAnimator objectAnimatorBack = ObjectAnimator.ofFloat(runAnimationBack, "rotationY", -90, 0);
        objectAnimatorFront.setDuration(100);
        objectAnimatorBack.setDuration(100);
        objectAnimatorFront.start();
        objectAnimatorFront.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                toggleTv();
                objectAnimatorBack.start();
            }
        });
        objectAnimatorBack.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIsToggling = false;
            }
        });
    }

    private void toggleTv() {
        if (mFrontTv.getVisibility() == VISIBLE) {
            mFrontTv.setVisibility(INVISIBLE);
            mBackTv.setVisibility(VISIBLE);
        } else {
            mFrontTv.setVisibility(VISIBLE);
            mBackTv.setVisibility(GONE);
        }
    }


}
