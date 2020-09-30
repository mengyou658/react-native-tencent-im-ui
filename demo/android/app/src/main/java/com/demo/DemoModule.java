package com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class DemoModule extends ReactContextBaseJavaModule {

    public DemoModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }


    @Override
    public String getName() {
        return "DemoModule";
    }

    @ReactMethod
    public void getSig(int sdkAppId, String appsec, String userId, Promise promise) {
        String res = GenerateTestUserSig.genTestUserSig(Long.valueOf(sdkAppId), appsec, userId);
        promise.resolve(res);
    }

}
