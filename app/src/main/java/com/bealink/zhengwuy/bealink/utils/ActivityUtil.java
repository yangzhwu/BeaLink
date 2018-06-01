package com.bealink.zhengwuy.bealink.utils;

import android.support.v7.app.AppCompatActivity;

import com.bealink.zhengwuy.bealink.R;

/**
 * Created by zhengwuy on 2017/2/19.
 * Email: 963460692@qq.com
 * description:
 */

public class ActivityUtil {


    public static void finishAnim(AppCompatActivity context) {
        context.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    private static void startAnim(AppCompatActivity context) {
        context.overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }
}
