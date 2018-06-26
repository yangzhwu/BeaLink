package com.bealink.zhengwuy.bealink.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhengwuy on 2018/6/18.
 * Email: 963460692@qq.com
 * description:
 */
public class CommonUtil {
    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isMoneyNumber(String nmoneyNumer) {
        //金额验证
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(nmoneyNumer);
        return match.matches();
    }
}
