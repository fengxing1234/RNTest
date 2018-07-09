package com.helloworld.jsutils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by fengxing on 2018/7/2.
 */

public class UIManagerModule extends ReactContextBaseJavaModule {

    private static final String JS_NAME = "UIManagerModule";

    public UIManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return JS_NAME;
    }

    @ReactMethod
    public void measureLayout(int tag , int ancestorTag, Callback errorCallback,Callback successCallback){

    }
}
