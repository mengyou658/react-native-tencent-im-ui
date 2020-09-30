package com.yunchao.tencentim.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.yunchao.tencentim.R;
import com.yunchao.tencentim.common.Constants;
import com.yunchao.tencentim.utils.IMLog;


/**
 * 登录状态的Activity都要集成该类，来完成被踢下线等监听处理。
 */
public class BaseActivity extends Activity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    public static void logout(Context context, boolean autoLogin) {
        SharedPreferences shareInfo = context.getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shareInfo.edit();
        editor.putBoolean(Constants.AUTO_LOGIN, autoLogin);
        editor.commit();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        IMLog.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_color));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.navigation_bar_color));
            int vis = getWindow().getDecorView().getSystemUiVisibility();
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            getWindow().getDecorView().setSystemUiVisibility(vis);
        }

        TUIKit.addIMEventListener(new IMEventListener() {
            @Override
            public void onForceOffline() {
                ToastUtil.toastLongMessage("您的帐号已在其它终端登录");
                logout(BaseActivity.this.getApplicationContext(), false);
            }
        });
    }

    @Override
    protected void onStart() {
        IMLog.i(TAG, "onStart");
        super.onStart();
        SharedPreferences shareInfo = getSharedPreferences(Constants.USERINFO, Context.MODE_PRIVATE);
        boolean login = shareInfo.getBoolean(Constants.AUTO_LOGIN, false);
        if (!login) {
            BaseActivity.logout(getApplicationContext(), false);
        }
    }

    @Override
    protected void onResume() {
        IMLog.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        IMLog.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        IMLog.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        IMLog.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        IMLog.i(TAG, "onNewIntent");
        super.onNewIntent(intent);
    }
}
