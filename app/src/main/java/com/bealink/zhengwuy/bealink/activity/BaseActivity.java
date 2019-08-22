package com.bealink.zhengwuy.bealink.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.bealink.zhengwuy.bealink.R;

/**
 * Created by zhengwuy on 2018/5/29.
 * email: 13802885114@139.com
 * des:
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private int mStatuesBarColorId = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            if (mStatuesBarColorId == -1) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            } else {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, mStatuesBarColorId));
            }
        }
    }

    protected void setStatusBarColorId(int colorId) {
        mStatuesBarColorId = colorId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
