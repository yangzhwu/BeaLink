package com.bealink.zhengwuy.bealink.adapter;

import android.support.annotation.Nullable;

import com.bealink.zhengwuy.bealink.R;
import com.bealink.zhengwuy.bealink.bean.other.ContactBean;
import com.bealink.zhengwuy.bealink.utils.LogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by zhengwuy on 2018/6/15.
 * email: 13802885114@139.com
 * des:
 */
public class ContactsListAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {

    public ContactsListAdapter(@Nullable List<ContactBean> data) {
        super(R.layout.item_contact_layout, data);
        LogUtil.e("list", mData.toString());
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {
        if (helper.getAdapterPosition() == 0) {
            helper.setGone(R.id.tv_contact_word, true);
        } else {
            int position = helper.getAdapterPosition();
            if (!mData.get(position).getHeaderWord().equals(mData.get(position - 1).getHeaderWord())) {
                helper.setGone(R.id.tv_contact_word, true);
            } else {
                helper.setGone(R.id.tv_contact_word, false);
            }
        }
        helper.setText(R.id.tv_contact_name, item.getName())
                .setText(R.id.tv_contact_word, item.getHeaderWord());

    }

    public int getFirstPosition(String headerWord) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getHeaderWord().equals(headerWord)) {
                return i;
            }
        }
        return -1;
    }
}
