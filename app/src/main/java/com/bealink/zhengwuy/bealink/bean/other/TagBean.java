package com.bealink.zhengwuy.bealink.bean.other;

/**
 * Created by zhengwuy on 2018/6/25.
 * email: 13802885114@139.com
 * des:
 */
public class TagBean {
    private String mTagName;
    private String mTagPrice;

    public TagBean(String tagName, String tagPrice) {
        mTagName = tagName;
        mTagPrice = tagPrice;
    }

    public String getTagName() {
        return mTagName;
    }

    public String getTagPrice() {
        return mTagPrice;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof TagBean && mTagName.equals(((TagBean) obj).mTagName);
    }

    @Override
    public int hashCode() {
        return mTagName.hashCode();
    }

    public void setTagName(String tagName) {
        mTagName = tagName;
    }

    public void setTagPrice(String tagPrice) {
        mTagPrice = tagPrice;
    }
}
