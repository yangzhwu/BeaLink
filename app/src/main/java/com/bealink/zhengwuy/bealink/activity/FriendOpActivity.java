package com.bealink.zhengwuy.bealink.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.constant.Constants;
import com.bealink.zhengwuy.bealink.fragment.AddFriendsFragment;

public class FriendOpActivity extends BaseActivity {
    public static final int OP_ADD_FRIENDS = 1;
    private int mOperator;

    public static void start(Context context, int op) {
        Intent intent = new Intent(context, FriendOpActivity.class);
        intent.putExtra(Constants.Key.KEY_OP_FRIENDS, op);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_op);

        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mOperator = bundle.getInt(Constants.Key.KEY_OP_FRIENDS, OP_ADD_FRIENDS);
            }
        }
    }

    private void initView() {
        if (mOperator == OP_ADD_FRIENDS) {
            AddFriendsFragment addFriendsFragment = new AddFriendsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.friend_op_container, addFriendsFragment).commit();
        }
    }
}
