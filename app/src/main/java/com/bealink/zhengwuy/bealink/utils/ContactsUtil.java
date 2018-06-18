package com.bealink.zhengwuy.bealink.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.bealink.zhengwuy.bealink.bean.other.ContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengwuy on 2018/6/17.
 * Email: 963460692@qq.com
 * description:
 */
public class ContactsUtil {
    // 号码
    private final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    private final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    //上下文对象
    private Context context;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    public ContactsUtil(Context context){
        this.context = context;
    }

    //获取所有联系人
    public List<ContactBean> getPhone(){
        List<ContactBean> contactBeans = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(phoneUri,new String[]{NUM,NAME},null,null,null);
        if (cursor != null) {
            while (cursor.moveToNext()){
                ContactBean contactBean = new ContactBean();
                contactBean.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                contactBean.setPhoneNumber(cursor.getString(cursor.getColumnIndex(NUM)));
                contactBeans.add(contactBean);
            }
            cursor.close();
        }
        return contactBeans;
    }
}
