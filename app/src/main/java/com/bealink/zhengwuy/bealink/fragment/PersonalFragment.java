package com.bealink.zhengwuy.bealink.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.activity.LoginActivity;
import com.bealink.zhengwuy.bealink.im.ImHelper;
import com.bealink.zhengwuy.bealink.utils.DialogUtil;
import com.bealink.zhengwuy.bealink.utils.ToastUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PersonalFragment extends Fragment {
    private Button mLogoutBtn;
    private SweetAlertDialog mSweetAlertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_personal, container, false);
        initView(rootView);
        initListener();
        return rootView;
    }

    private void initView(View rootView) {
        mLogoutBtn = rootView.findViewById(R.id.logout_btn);
        mSweetAlertDialog = DialogUtil.creatCommonLoadingDialog(getActivity(), "正在退出中");

    }

    private void initListener() {
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetAlertDialog.show();
                ImHelper.getInstance().logout(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtils.show("退出登录成功");
                        mSweetAlertDialog.dismiss();
                        LoginActivity.start(getActivity());
                        getActivity().finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show("退出登录失败");
                        mSweetAlertDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
