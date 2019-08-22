package com.bealink.zhengwuy.bealink.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.im.ImHelper;
import com.bealink.zhengwuy.bealink.utils.DialogUtil;
import com.bealink.zhengwuy.bealink.utils.ToastUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AddFriendsFragment extends Fragment {
    private static final String TAG = "AddFriendsFragment";
    private EditText mUserNameEt;
    private Button mAddFriendBtn;
    private SweetAlertDialog mSweetAlertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_friends, container, false);
        initView(rootView);
        initListener();
        return rootView;
    }

    private void initView(View rootView) {
        mUserNameEt = rootView.findViewById(R.id.friend_username_et);
        mAddFriendBtn = rootView.findViewById(R.id.add_friend_btn);
        mSweetAlertDialog = DialogUtil.creatCommonLoadingDialog(getActivity(), "添加中");
    }

    private void initListener() {
        mAddFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSweetAlertDialog.show();
                String userName = mUserNameEt.getText().toString();
                ImHelper.getInstance().addFrined(userName, new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        ToastUtils.show("添加成功");
                        mSweetAlertDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.show("添加失败");
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
