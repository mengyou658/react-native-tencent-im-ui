package com.yunchao.tencentim;

import android.content.Context;

public class IMApplication {

    private static Context context;

    private static Class mainActivityClass;

    public static void setContext(final Context context, Class mainActivityClass) {
        IMApplication.context = context.getApplicationContext();
        IMApplication.mainActivityClass = mainActivityClass;
    }

    public static Context getContext() {
        return context;
    }

    public static Class getMainActivityClass() {
        return mainActivityClass;
    }



}

