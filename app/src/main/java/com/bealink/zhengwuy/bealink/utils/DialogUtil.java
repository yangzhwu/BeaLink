package com.bealink.zhengwuy.bealink.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by zhengwuy on 2017/2/28.
 * Emali: 963460692@qq.com
 * description:
 */

public class DialogUtil {

    public static SweetAlertDialog creatCommonLoadingDialog(Activity activity, String title) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setCancelable(false);
        return sweetAlertDialog;
    }
}
