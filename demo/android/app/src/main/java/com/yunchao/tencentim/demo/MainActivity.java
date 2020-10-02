package com.yunchao.tencentim.demo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.react.ReactActivity;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.tencent.imsdk.log.QLog;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.tencent.qcloud.tim.uikit.utils.ToastUtil;
import com.yunchao.tencentim.demo.utils.ThirdPushTokenMgr;

public class MainActivity extends ReactActivity {
  private static final String TAG = "HUAWEIPushReceiver";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (IMFunc.isBrandHuawei()) {
      // 华为离线推送
      HMSAgent.connect(this, new ConnectHandler() {
        @Override
        public void onConnect(int rst) {
          QLog.i(TAG, "huawei push HMS connect end:" + rst);
        }
      });
      getHuaWeiPushToken();
    }

  }

  private void getHuaWeiPushToken() {
    HMSAgent.Push.getToken(new GetTokenHandler() {
      @Override
      public void onResult(int rtnCode) {
        QLog.i(TAG, "huawei push get token result code: " + rtnCode);
      }
    });
  }

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "demo";
  }

}
