package com.bealink.zhengwuy.bealink.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.fragment.ChatListFragment;
import com.bealink.zhengwuy.bealink.fragment.ContactsFragment;
import com.bealink.zhengwuy.bealink.fragment.FindFragment;
import com.bealink.zhengwuy.bealink.fragment.PersonalFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    private final String[] mTitles = {"聊天", "通讯录", "发现", "我的"};
    private MyPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private TextView mTitleLeftTv;
//    private RadioButton mChatRB, mContactRB, mFindRB, mPersonalRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();

        mRadioGroup.check(R.id.main_tab_chat);
    }

    private void initData() {
        mFragments.add(ChatListFragment.newInstance("", ""));
        mFragments.add(ContactsFragment.newInstance("", ""));
        mFragments.add(FindFragment.newInstance("", ""));
        mFragments.add(PersonalFragment.newInstance("", ""));
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);

        mRadioGroup = findViewById(R.id.main_tab);
        mTitleLeftTv = findViewById(R.id.title_txt_left);
//        mChatRB = findViewById(R.id.main_tab_chat);
//        mContactRB = findViewById(R.id.main_tab_contact);
//        mFindRB = findViewById(R.id.main_tab_find);
//        mPersonalRB = findViewById(R.id.main_tab_personal);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTitleLeftTv.setText(mTitles[0]);
                        mRadioGroup.check(R.id.main_tab_chat);
                        break;
                    case 1:
                        mTitleLeftTv.setText(mTitles[1]);
                        mRadioGroup.check(R.id.main_tab_contact);
                        break;
                    case 2:
                        mTitleLeftTv.setText(mTitles[2]);
                        mRadioGroup.check(R.id.main_tab_find);
                        break;
                    case 3:
                        mTitleLeftTv.setText(mTitles[3]);
                        mRadioGroup.check(R.id.main_tab_personal);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.main_tab_chat:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.main_tab_contact:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.main_tab_find:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.main_tab_personal:
                    mViewPager.setCurrentItem(3);
                    break;
                default:
                    break;
            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
