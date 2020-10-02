package com.yunchao.tencentim.demo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import com.huawei.android.hms.agent.HMSAgent;
import com.tencent.imsdk.TIMBackgroundParam;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.yunchao.tencentim.IMApplication;
import com.yunchao.tencentim.demo.thridpush.MessageNotification;
import com.yunchao.tencentim.demo.utils.IMLog;

import java.util.List;

public class MainApplication extends Application implements ReactApplication {
    private static final String TAG = MainApplication.class.getSimpleName();

    private static MainApplication instance;

    public static MainApplication instance() {
        return instance;
    }

    private final ReactNativeHost mReactNativeHost =
            new ReactNativeHost(this) {
                @Override
                public boolean getUseDeveloperSupport() {
                    return BuildConfig.DEBUG;
                }

                @Override
                protected List<ReactPackage> getPackages() {
                    @SuppressWarnings("UnnecessaryLocalVariable")
                    List<ReactPackage> packages = new PackageList(this).getPackages();
                    // Packages that cannot be autolinked yet can be added manually here, for example:
                    // packages.add(new MyReactNativePackage());
                    packages.add(new DemoPackage()); // <-- Add this line with your package name.
                    return packages;
                }

                @Override
                protected String getJSMainModuleName() {
                    return "index";
                }
            };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        SoLoader.init(this, /* native exopackage */ false);

        // 初始化 Context
        IMApplication.setContext(this, MainActivity.class);
        //初始化IM
        tencentIMInitMethod();

    }

    private void tencentIMInitMethod() {
        instance = this;
        if (SessionWrapper.isMainProcess(getApplicationContext())) {

            // 配置 Config，请按需配置
            if (IMFunc.isBrandHuawei()) {
                // 华为离线推送
                HMSAgent.init(this);
            }
            registerActivityLifecycleCallbacks(new StatisticActivityLifecycleCallback());
        }
    }

    class StatisticActivityLifecycleCallback implements ActivityLifecycleCallbacks {
        private int foregroundActivities = 0;
        private boolean isChangingConfiguration;
        private IMEventListener mIMEventListener = new IMEventListener() {
            @Override
            public void onNewMessage(V2TIMMessage v2TIMMessage) {
                MessageNotification notification = MessageNotification.getInstance();
                notification.notify(v2TIMMessage);
            }

        };

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            IMLog.i(IMLog.TAG, "onActivityCreated bundle: " + bundle);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            foregroundActivities++;
            if (foregroundActivities == 1 && !isChangingConfiguration) {
                // 应用切到前台
                IMLog.i(IMLog.TAG, "application enter foreground");
                TIMManager.getInstance().doForeground(new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        IMLog.e(IMLog.TAG, "doForeground err = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        IMLog.i(IMLog.TAG, "doForeground success");
                    }
                });
                TUIKit.removeIMEventListener(mIMEventListener);
            }
            isChangingConfiguration = false;
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            foregroundActivities--;
            if (foregroundActivities == 0) {
                // 应用切到后台
                IMLog.i(IMLog.TAG, "application enter background");
                int unReadCount = 0;
                List<TIMConversation> conversationList = TIMManager.getInstance().getConversationList();
                for (TIMConversation timConversation : conversationList) {
                    unReadCount += timConversation.getUnreadMessageNum();
                }
                TIMBackgroundParam param = new TIMBackgroundParam();
                param.setC2cUnread(unReadCount);
                TIMManager.getInstance().doBackground(param, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        IMLog.e(IMLog.TAG, "doBackground err = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        IMLog.i(IMLog.TAG, "doBackground success");
                    }
                });
                // 应用退到后台，消息转化为系统通知
                TUIKit.addIMEventListener(mIMEventListener);
            }
            isChangingConfiguration = activity.isChangingConfigurations();
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    }
}
