package com.helloworld.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fengxing on 2018/7/2.
 */

public class Test {

    private static final String TAG = "Test";

    private ReactContext mContext;

    public Test(ReactContext mContext) {
        this.mContext = mContext;
    }

    public void getTime(Context context) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Log.d(TAG, "getTime: " + format);
        Toast.makeText(context, format, Toast.LENGTH_LONG).show();
    }

    public void sendEvent(String eventName, @Nullable WritableMap params) {
        if (mContext == null) {
            Log.d(TAG, "sendEvent: reactContext == null");
        } else {
            mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
        }
    }
}
