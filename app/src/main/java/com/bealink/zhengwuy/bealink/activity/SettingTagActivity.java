package com.bealink.zhengwuy.bealink.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.bean.other.TagBean;
import com.bealink.zhengwuy.bealink.constant.Constants;
import com.bealink.zhengwuy.bealink.utils.DialogUtil;
import com.bealink.zhengwuy.bealink.utils.LogUtil;
import com.bealink.zhengwuy.bealink.utils.SharedPreferenceHelper;
import com.bealink.zhengwuy.bealink.utils.ToastUtils;
import com.bealink.zhengwuy.bealink.view.TagLayout;
import com.bealink.zhengwuy.bealink.view.TagView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingTagActivity extends BaseActivity {
    private static final String TAG = "SettingTagActivity";
    private List<TagBean> mTagBeans = new ArrayList<>();
    private TagLayout mTagLayout;
    private FloatingActionButton mFloatingActionButton;
    private Map<View, TagBean> mViewTagBeanMap = new HashMap<>();
    private View.OnLongClickListener mTagLongClickListener;
    private View.OnClickListener mTagOnClickListener;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_tab);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        String tags = SharedPreferenceHelper.getInstance().getString(Constants.SharedPreferenceKey.KEY_MY_TAG, "");
        if (!TextUtils.isEmpty(tags)) {
            Gson gson = new Gson();
            mTagBeans = gson.fromJson(tags, new TypeToken<List<TagBean>>(){}.getType());
        }

    }

    private void initView() {
        TextView titleTv = findViewById(R.id.title_txt_title);
        titleTv.setText("我的标签");
        titleTv.setVisibility(View.VISIBLE);

        mFloatingActionButton = findViewById(R.id.fab);
        mTagLayout = findViewById(R.id.tag_layout);

        for (int i = 0; i < mTagBeans.size(); i++) {
            TagBean tagBean = mTagBeans.get(i);
            TagView tagView = new TagView(this);
            tagView.setText(tagBean.getTagName(), tagBean.getTagPrice());
            mTagLayout.addView(tagView);
            mViewTagBeanMap.put(tagView, tagBean);
        }

    }

    private void addNewTag(Object object) {
        if (object != null && object instanceof TagBean) {
            TagBean tagBean = (TagBean) object;
            if (!mTagBeans.contains(tagBean)) {
                mTagBeans.add(tagBean);
                TagView tagView = new TagView(SettingTagActivity.this);
                tagView.setText(tagBean.getTagName(), tagBean.getTagPrice());
                mViewTagBeanMap.put(tagView, tagBean);
                mTagLayout.addView(tagView);
                tagView.setOnLongClickListener(mTagLongClickListener);
                tagView.setOnClickListener(mTagOnClickListener);
                saveTagBeans();
                ToastUtils.show("增加标签成功");
            } else {
                ToastUtils.show("已经有同样的标签");
            }
        }
    }

    private boolean modifyTag(View view, Object object) {
        if (object != null && object instanceof TagBean) {
            TagBean tagBeanNew = (TagBean) object;
            if (mViewTagBeanMap.containsKey(view) && !mTagBeans.contains(tagBeanNew)) {
                TagBean tagBean = mViewTagBeanMap.get(view);
                TagView tagView = (TagView) view;
                tagBean.setTagName(tagBeanNew.getTagName());
                tagBean.setTagPrice(tagBeanNew.getTagPrice());
                tagView.setText(tagBean.getTagName(), tagBean.getTagPrice());
                saveTagBeans();
                return true;
            }
        }
        return false;
    }

    private void saveTagBeans() {
        Gson gson = new Gson();
        String s = gson.toJson(mTagBeans);
        SharedPreferenceHelper.getInstance().putString(Constants.SharedPreferenceKey.KEY_MY_TAG, s);
    }

    private void closeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    private void initListener() {
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTagBeans.size() <= 20) {
                    mDialog = DialogUtil.creatTagDialog(SettingTagActivity.this, new DialogUtil.DataTransListener() {
                        @Override
                        public void confirm(Object object) {
                            addNewTag(object);
                        }

                        @Override
                        public void delete() {
                        }
                    }, true, null);
                    mDialog.show();
                } else {
                    ToastUtils.show("最多只能增加20个标签");
                }
            }
        });

        mTagLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TagBean tagBean = mViewTagBeanMap.get(v);
                if (tagBean != null) {
                   //弹出对话框
                    mDialog = DialogUtil.creatTagDialog(SettingTagActivity.this, new DialogUtil.DataTransListener() {
                        @Override
                        public void confirm(Object object) {
                            if (modifyTag(v, object)) {
                                ToastUtils.show("修改标签成功");
                            } else {
                                ToastUtils.show("已经存在相同的标签");
                            }
                        }

                        @Override
                        public void delete() {
                            ToastUtils.show("删除标签成功");
                            mTagLayout.removeView(v);
                            mTagBeans.remove(tagBean);
                            mViewTagBeanMap.remove(v);
                            saveTagBeans();
                        }
                    }, false, tagBean);
                    mDialog.show();
                }
                return true;
        }};

        mTagOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagView tagView = (TagView) v;
                tagView.toggle();
            }
        };
        LogUtil.d(TAG, mTagLayout.getChildCount() + " ");
        for (int i = 0; i < mTagLayout.getChildCount(); i++) {
            View view = mTagLayout.getChildAt(i);
            view.setOnLongClickListener(mTagLongClickListener);
            view.setOnClickListener(mTagOnClickListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDialog();
    }
}
