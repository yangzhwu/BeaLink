package com.bealink.zhengwuy.bealink.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.bealink.zhengwuy.bealink.bean.recycler.ContactBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zhengwuy on 2018/6/15.
 * email: 13802885114@139.com
 * des:
 */
public class ContactsListAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {

    public ContactsListAdapter(int layoutResId, @Nullable List<ContactBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {

    }
}
