package com.bealink.zhengwuy.bealink.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bealink.zhengwuy.bealink.R;

public class LocalContactActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, LocalContactActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_contact);
    }
}
