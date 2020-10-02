package com.yunchao.tencentim.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;
import com.tencent.qcloud.tim.uikit.config.GeneralConfig;
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.yunchao.tencentim.IMApplication;
import com.yunchao.tencentim.activity.ChatActivity;
import com.yunchao.tencentim.common.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogRecord;


public class TencentIMModel extends ReactContextBaseJavaModule {

    public TencentIMModel(ReactApplicationContext reactContext) {
        super(reactContext);
    }


    @Override
    public String getName() {
        return "TencentIMModel";
    }

    public TUIKitConfigs getConfigs() {
        GeneralConfig config = new GeneralConfig();
        // 显示对方是否已读的view将会展示
        config.setShowRead(true);
        config.setAppCacheDir(IMApplication.getContext().getFilesDir().getPath());
        TUIKit.getConfigs().setGeneralConfig(config);
        return TUIKit.getConfigs();
    }

    @ReactMethod
    public void initSdk(final int sdkAppId) {
        final Activity activity = getCurrentActivity();
        if (null != activity) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TUIKit.init(IMApplication.getContext(), sdkAppId, getConfigs());
                }
            });
        }
    }

    @ReactMethod
    public void logout(final Promise promise) {
        final String loginUser = TIMManager.getInstance().getLoginUser();
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Map<String, Object> result = new HashMap<>(3);
                result.put("module", "onError");
                result.put("code", -9999);
                result.put("desc", "登出失败");
                promise.reject(result.toString(), new RuntimeException(s));
            }

            @Override
            public void onSuccess() {
                Map<String, Object> result = new HashMap<>(3);
                result.put("module", "onSuccess");
                result.put("code", 0);
                result.put("desc", "登出成功");
                promise.resolve(result);
            }
        });
    }


    @ReactMethod
    public void login(final String userId, String userSig, final Promise promise) {
        TUIKit.login(userId, userSig, new IUIKitCallBack() {
            @Override
            public void onError(String module, final int code, final String desc) {
                Map<String, Object> result = new HashMap<>(3);
                result.put("module", module);
                result.put("code", code);
                result.put("desc", desc);
                promise.reject(result.toString(), new RuntimeException(desc));
            }

            @Override
            public void onSuccess(Object data) {
                Map<String, Object> result = new HashMap<>(3);
                result.put("module", "success");
                result.put("code", 0);
                result.put("desc", "登录成功");
                promise.resolve(data);
            }
        });
    }

    @ReactMethod
    public void startChatView(String userId, String conTitle, int type) {
        final Activity activity = getCurrentActivity();
        if (activity != null) {

            ChatInfo chatInfo = new ChatInfo();
            if (type == 2) {
                chatInfo.setType(TIMConversationType.Group.value());
            } else {
                chatInfo.setType(TIMConversationType.C2C.value());
            }
            chatInfo.setId(userId);
            chatInfo.setChatName(conTitle);
            final Intent intent = new Intent(activity, ChatActivity.class);
            intent.putExtra(Constants.CHAT_INFO, chatInfo);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }

}
