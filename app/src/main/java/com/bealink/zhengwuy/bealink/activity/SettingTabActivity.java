package com.bealink.zhengwuy.bealink.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.utils.LogUtil;
import com.bealink.zhengwuy.bealink.view.TabView;

public class SettingTabActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_tab);

        initView();
    }

    private void initView() {
        TabView tabView = findViewById(R.id.tab_view);
        tabView.setText("德玛西亚", "100");
        tabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabView.toggle();
            }
        });
        tabView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }
}
