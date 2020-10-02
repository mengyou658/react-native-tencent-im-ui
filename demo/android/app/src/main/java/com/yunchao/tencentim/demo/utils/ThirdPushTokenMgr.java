package com.yunchao.tencentim.demo.utils;

import android.text.TextUtils;
import android.util.Log;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushToken;
import com.tencent.imsdk.log.QLog;
import com.tencent.imsdk.utils.IMFunc;

/**
 * 用来保存厂商注册离线推送token的管理类示例，当登陆im后，通过 setOfflinePushToken 上报证书 ID 及设备 token 给im后台。开发者可以根据自己的需求灵活实现
 */

public class ThirdPushTokenMgr {

    private static final String TAG = "HUAWEIPushReceiver";
    private String mThirdPushToken;
    private boolean mIsTokenSet;

    public static ThirdPushTokenMgr getInstance() {
        return ThirdPushTokenHolder.instance;
    }

    public String getThirdPushToken() {
        return mThirdPushToken;
    }

    public void setThirdPushToken(String mThirdPushToken) {
        this.mThirdPushToken = mThirdPushToken;
    }

    public void setPushTokenToTIM() {
        String token = ThirdPushTokenMgr.getInstance().getThirdPushToken();
        if (TextUtils.isEmpty(token)) {
            QLog.i(TAG, "setPushTokenToTIM third token is empty");
            mIsTokenSet = false;
            return;
        }

        TIMOfflinePushToken param = null;
        if (IMFunc.isBrandHuawei()) {
            param = new TIMOfflinePushToken(PrivateConstants.HW_PUSH_BUZID, token);
        } else {
            return;
        }
        TIMManager.getInstance().setOfflinePushToken(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.d(TAG, "setOfflinePushToken err code = " + code);
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "setOfflinePushToken success");
                mIsTokenSet = true;
            }
        });
    }

    private static class ThirdPushTokenHolder {
        private static final ThirdPushTokenMgr instance = new ThirdPushTokenMgr();
    }
}
