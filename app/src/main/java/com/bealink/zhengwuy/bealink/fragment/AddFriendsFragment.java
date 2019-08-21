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
import com.bealink.zhengwuy.bealink.utils.LogUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class AddFriendsFragment extends Fragment {
    private static final String TAG = "AddFriendsFragment";
    private EditText mUserNameEt;
    private Button mAddFriendBtn;

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
    }

    private void initListener() {
        mAddFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserNameEt.getText().toString();
                 new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().addContact(userName, "");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            LogUtil.e(TAG, "添加失败");
                            return;
                        }
                        LogUtil.e(TAG, "添加成功");
                    }
                }).start();
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
