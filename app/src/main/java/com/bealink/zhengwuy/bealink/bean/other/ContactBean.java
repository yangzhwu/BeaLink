package com.bealink.zhengwuy.bealink.bean.other;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Created by zhengwuy on 2018/6/15.
 * email: 13802885114@139.com
 * des:
 */
public class ContactBean {
    private String name;
    private String phoneNumber;

    //拼音
    private String pinyin;
    //拼音首字母
    private String headerWord;

    public void setName(String name) {
        this.name = name;
        pinyin = Pinyin.toPinyin(name, null);
        if (pinyin.toUpperCase().charAt(0) < 'A' || pinyin.toUpperCase().charAt(0) > 'Z') {
            headerWord = "#";
        } else {
            headerWord = pinyin.substring(0, 1);
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getHeaderWord() {
        return headerWord;
    }
}
