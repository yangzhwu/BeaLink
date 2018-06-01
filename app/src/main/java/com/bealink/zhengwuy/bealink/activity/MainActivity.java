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
    private RadioButton mChatRB, mContactRB, mFindRB, mPersonalRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
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

        mChatRB = findViewById(R.id.main_tab_chat);
        mContactRB = findViewById(R.id.main_tab_contact);
        mFindRB = findViewById(R.id.main_tab_find);
        mPersonalRB = findViewById(R.id.main_tab_personal);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        mChatRB.setChecked(true);
                        break;
                    case 1:
                        mContactRB.setChecked(true);
                        break;
                    case 2:
                        mFindRB.setChecked(true);
                        break;
                    case 3:
                        mPersonalRB.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mChatRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mViewPager.getCurrentItem() != 0) {
                        mViewPager.setCurrentItem(0);
                    }
                }
            }
        });
        mFindRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mViewPager.getCurrentItem() != 1) {
                        mViewPager.setCurrentItem(1);
                    }
                }
            }
        });

        mContactRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mViewPager.getCurrentItem() != 2) {
                        mViewPager.setCurrentItem(2);
                    }
                }
            }
        });

        mPersonalRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mViewPager.getCurrentItem() != 3) {
                        mViewPager.setCurrentItem(3);
                    }
                }
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
}
