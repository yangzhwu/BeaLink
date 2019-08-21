package com.bealink.zhengwuy.bealink.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.event.ContactListUpdateMessage;
import com.bealink.zhengwuy.bealink.fragment.FindFragment;
import com.bealink.zhengwuy.bealink.fragment.PersonalFragment;
import com.bealink.zhengwuy.bealink.im.ContactManager;
import com.bealink.zhengwuy.bealink.utils.LogUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    private final String[] mTitles = {"聊天", "通讯录", "发现", "我的"};
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private TextView mTitleLeftTv;
    private EaseConversationListFragment mEaseConversationListFragment;
    private EaseContactListFragment mEaseContactListFragment;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

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
        mEaseConversationListFragment = new EaseConversationListFragment();
        mEaseContactListFragment = new EaseContactListFragment();
        mEaseContactListFragment.setContactsMap(ContactManager.getInstance().getAllContacts());
        mFragments.add(mEaseConversationListFragment);
        mFragments.add(mEaseContactListFragment);
        mFragments.add(new FindFragment());
        mFragments.add(new PersonalFragment());
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);

        mRadioGroup = findViewById(R.id.main_tab);
        mTitleLeftTv = findViewById(R.id.title_txt_left);
//        mChatRB = findViewById(R.id.main_tab_chat);
//        mContactRB = findViewById(R.id.main_tab_contact);
//        mFindRB = findViewById(R.id.main_tab_find);
//        mPersonalRB = findViewById(R.id.main_tab_personal);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        mEaseConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                ChatActivity.start(MainActivity.this, conversation.conversationId());
            }
        });

        mEaseContactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                ChatActivity.start(MainActivity.this, user.getUsername());
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

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ContactListUpdateMessage contactListUpdateMessage) {
        mEaseContactListFragment.setContactsMap(ContactManager.getInstance().getAllContacts());
        mEaseContactListFragment.refresh();
    }
}
