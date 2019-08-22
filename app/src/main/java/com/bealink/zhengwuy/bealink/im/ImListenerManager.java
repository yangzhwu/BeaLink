package com.bealink.zhengwuy.bealink.im;

import com.bealink.zhengwuy.bealink.utils.LogUtil;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * Created by zhengwuy on 2019/8/21.
 * email: 13802885114@139.com
 * des:
 */
public class ImListenerManager {
    private static final String TAG = "ImListenerManager";
    private EMConnectionListener mEMConnectionListener;
    private EMContactListener mEMContactListener;
    private static ImListenerManager sImListenerManager = new ImListenerManager();

    private ImListenerManager() {

        //连接监听器
        mEMConnectionListener = new EMConnectionListener() {
            @Override
            public void onConnected() {
                LogUtil.e(TAG, "onConnected");
                ContactManager.getInstance().refresh();
            }

            @Override
            public void onDisconnected(int errorCode) {
                LogUtil.e(TAG, "onDisconnected");

            }
        };

        mEMContactListener = new EMContactListener() {
            @Override
            public void onContactAdded(String username) {
                LogUtil.e(TAG, "onContactAdded : " + username);
                ContactManager.getInstance().addUser(username);

            }

            @Override
            public void onContactDeleted(String username) {
                LogUtil.e(TAG, "onContactDeleted : " + username);
                ContactManager.getInstance().deleteUser(username);
            }

            @Override
            public void onContactInvited(String username, String reason) {
                LogUtil.e(TAG, "onContactInvited : " + username);

            }

            @Override
            public void onFriendRequestAccepted(String username) {
                LogUtil.e(TAG, "onFriendRequestAccepted : " + username);

            }

            @Override
            public void onFriendRequestDeclined(String username) {
                LogUtil.e(TAG, "onFriendRequestDeclined : " + username);

            }
        };
        EMClient.getInstance().addConnectionListener(mEMConnectionListener);
        EMClient.getInstance().contactManager().setContactListener(mEMContactListener);
    }

    public static ImListenerManager getInstance() {
        return sImListenerManager;
    }



}
