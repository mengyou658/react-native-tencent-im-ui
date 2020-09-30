package com.yunchao.tencentim;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.yunchao.tencentim.model.TencentIMConversationModel;

import java.util.ArrayList;
import java.util.List;

public class TencentImUiPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> list = new ArrayList<>();
        list.add(new com.yunchao.tencentim.model.TencentIMModule(reactContext));
        return list;
    }


    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        List<ViewManager> modules = new ArrayList<>();
        modules.add(new TencentIMConversationModel());
        return modules;
    }
}
