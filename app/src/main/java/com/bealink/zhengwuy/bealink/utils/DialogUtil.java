package com.bealink.zhengwuy.bealink.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.bean.other.TagBean;

import io.reactivex.annotations.NonNull;

/**
 * Created by zhengwuy on 2017/2/28.
 * Emali: 963460692@qq.com
 * description:
 */

public class DialogUtil {
    public interface DataTransListener {
        void confirm(Object object);
        void delete();
    }

    public static Dialog creatTagDialog(Activity activity, @NonNull DataTransListener dataTransListener, boolean isAdd, TagBean tagBean) {
        Dialog dialog = new Dialog(activity, R.style.dialog_custom);
        dialog.setContentView(R.layout.input_tag_dialog_layout);
        ImageView closeIv = dialog.findViewById(R.id.close_btn);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        EditText priceEt = dialog.findViewById(R.id.tag_price_et);
        EditText nameEt = dialog.findViewById(R.id.tag_name_et);
        Button buttonDelete = dialog.findViewById(R.id.delete_btn);
        Button buttonConfirm = dialog.findViewById(R.id.confirm_btn);
        View dividerLine = dialog.findViewById(R.id.divider_line);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = priceEt.getText().toString().trim();
                String name = nameEt.getText().toString().trim();
                if (TextUtils.isEmpty(price) || TextUtils.isEmpty(name)) {
                    ToastUtils.show("输入不能为空");
                    return;
                }
                if (!CommonUtil.isMoneyNumber(price)) {
                    ToastUtils.show("请输入正确的价格");
                    return;
                }
                TagBean tagBean = new TagBean(name, price);
                dataTransListener.confirm(tagBean);
                dialog.dismiss();
            }
        };
        buttonConfirm.setOnClickListener(onClickListener);
        if (isAdd) {
            dividerLine.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);
        } else {
            nameEt.setText(tagBean.getTagName());
            priceEt.setText(tagBean.getTagPrice());
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataTransListener.delete();
                    dialog.dismiss();
                }
            });
        }
        dialog.setCanceledOnTouchOutside(true);
        setDialogWidth(activity, dialog);
        return dialog;
    }

    private static void setDialogWidth(Activity activity, Dialog dialog) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        if (dialog.getWindow() != null) {
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = display.getWidth() * 4 / 5; // 设置dialog宽度为屏幕的4/5
            dialog.getWindow().setAttributes(lp);
        }
    }
}
