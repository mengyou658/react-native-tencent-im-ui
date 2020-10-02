package com.yunchao.tencentim.demo;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;


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
