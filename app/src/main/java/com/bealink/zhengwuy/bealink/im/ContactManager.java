package com.bealink.zhengwuy.bealink.im;

import android.annotation.SuppressLint;

import com.bealink.zhengwuy.bealink.event.ContactListUpdateMessage;
import com.bealink.zhengwuy.bealink.utils.LogUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhengwuy on 2019/8/21.
 * email: 13802885114@139.com
 * des:
 */
public class ContactManager {
    private static final String TAG = "ContactManager";
    private static ContactManager sContactManager = new ContactManager();
    private Map<String, EaseUser> mUserMap = new ConcurrentHashMap<>();
    private volatile boolean mIsGettingContactFromServer = false;

    public static ContactManager getInstance() {
        return sContactManager;
    }

    @SuppressLint("CheckResult")
    public void getAllConatctsFromServer() {
        if (mIsGettingContactFromServer) {
            return;
        }
        mIsGettingContactFromServer = true;
        LogUtil.d(TAG, "getAllConatctsFromServer");
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                LogUtil.d(TAG, usernames.size() + " ");
                emitter.onNext(usernames);
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> usernames) {
                //获取联系人成功，存入本地数据库
                mUserMap.clear();
                for (String username : usernames) {
                    mUserMap.put(username, new EaseUser(username));
                }
                postEvent();
                mIsGettingContactFromServer = false;
            }

            @Override
            public void onError(Throwable e) {
                //从网络获取联系人列表出错，从本地数据库获取联系人列表
                if (mUserMap.isEmpty()) {

                }
                mIsGettingContactFromServer = false;
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public Map<String, EaseUser> getAllContacts() {
        return mUserMap;
    }

    void addUser(String userName) {
        mUserMap.put(userName, new EaseUser(userName));
        postEvent();
    }

    private void postEvent() {
        EventBus.getDefault().post(new ContactListUpdateMessage());
    }

    void deleteUser(String userName) {
        mUserMap.remove(userName);
        postEvent();
    }

    public void refresh() {
        mUserMap.clear();
        getAllConatctsFromServer();
    }
}
