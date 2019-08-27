package com.bealink.zhengwuy.bealink.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.bean.response.RegisterResponseBean;
import com.bealink.zhengwuy.bealink.constant.Constants;
import com.bealink.zhengwuy.bealink.fragment.RegisterFragment;

public class RegisterActivity extends BaseActivity {
    private RegisterFragment mRegisterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterFragment = (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.register_one_fragment);
        mRegisterFragment.setRegisterListener(new RegisterFragment.RegisterListener() {
            @Override
            public void onRegisterSucess(RegisterResponseBean registerResponseBean) {
                Intent intent = new Intent();
                intent.putExtra(Constants.Key.USER_NAME, registerResponseBean.getUserName());
                intent.putExtra(Constants.Key.PASSWORD, registerResponseBean.getPassWord());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
