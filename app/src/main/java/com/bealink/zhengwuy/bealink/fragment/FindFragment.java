package com.bealink.zhengwuy.bealink.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.activity.FriendOpActivity;

public class FindFragment extends Fragment {
    private LinearLayout mAddFriendLl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_find, container, false);
        initView(rootView);
        initListener();
        return rootView;
    }

    private void initView(View rootView) {
        mAddFriendLl = rootView.findViewById(R.id.add_friend_ll);
    }

    private void initListener() {
        mAddFriendLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendOpActivity.start(getActivity(), FriendOpActivity.OP_ADD_FRIENDS);
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
