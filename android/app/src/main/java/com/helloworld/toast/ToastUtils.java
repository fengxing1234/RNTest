package com.helloworld.toast;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.helloworld.TestActivity;
import com.helloworld.test.Test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by fengxing on 2018/6/29.
 */

public class ToastUtils extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private static final String TAG = "ToastUtils";

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";


    private static final int IMAGE_PICKER_REQUEST = 467081;
    private static final String ERROR_ACTIVITY_DOES_NOT_EXIST = "ERROR_ACTIVITY_DOES_NOT_EXIST";
    private static final String ERROR_PICKER_CANCELLED = "E_PICKER_CANCELLED";
    private static final String ERROR_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER";
    private static final String ERROR_NO_IMAGE_DATA_FOUND = "E_NO_IMAGE_DATA_FOUND";


    private ReactContext mContext;
    private final Test mTest;
    private Promise mPickerPromise;


    public ToastUtils(ReactApplicationContext reactContext) {
        super(reactContext);
        //添加 onActivityForResult 监听
        reactContext.addActivityEventListener(mActivityEventListener);
        //监听activity的生命周期事件
        reactContext.addLifecycleEventListener(this);
        mContext = reactContext;
        mTest = new Test(mContext);

    }

    /**
     * 该方法的返回值 的RCT前缀可以自动被移除  所以 即使返回值是RCTToast 在js中也会编译通过
     * 在js中会根据这个方法返回的字符串来查找对应的类
     *
     * @return
     */
    @Override
    public String getName() {
        return "ToastShow";
    }

    /**
     * 该方法 不一定需要实现  所以可能返回值为空  一般用来返回一些可以被js同步访问的常量 或者是预定义的值
     * 在本类中 我们需要给js两个常量
     *
     * @return
     */
    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final HashMap<String, Object> constants = new HashMap<>();
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        return constants;
    }

    /**
     * 要导出一个方法给JavaScript使用，Java方法需要使用注解@ReactMethod。方法的返回类型必须为void。
     * React Native的跨语言访问是异步进行的，所以想要给JavaScript返回一个值的唯一办法是使用回调函数或者发送事件
     * 下面的参数类型在@ReactMethod注明的方法中，会被直接映射到它们对应的JavaScript类型。
     * Boolean -> Bool
     * Integer -> Number
     * Double -> Number
     * Float -> Number
     * String -> String
     * Callback -> function
     * ReadableMap -> Object
     * ReadableArray -> Array
     *
     * @param msg      弹出的提示信息
     * @param duration 显示事件长短
     */
    @ReactMethod
    public void show(String msg, int duration) {
        Toast.makeText(getReactApplicationContext(), msg, duration).show();
    }

    /**
     * 无参 调用
     */
    @ReactMethod
    public void showTime() {
        //new Test(mContext).getTime(mContext);

        mContext.startActivity(new Intent(mContext, TestActivity.class));
    }

    /**
     * Callback机制
     * <p>
     * 首先Calllback是异步的，RN端调用Native端，Native会callback，但是时机是不确定的，如果多次调用的话，会存在问题。
     * <p>
     * Naive端是无法主动通过回调函数向RN端发送消息的。
     *
     * @param callback
     */
    @ReactMethod
    public void getResult(final Callback callback) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callback.invoke("我是回调参数");
                super.run();
            }
        }.start();
    }


    /**
     * promise机制
     * <p>
     * 关于ES6中Promise的用法可以参考：http://www.cnblogs.com/lvdabao/p/5320705.html
     * <p>
     * Promise 的状态
     * 一个 Promise 的当前状态必须为以下三种状态中的一种：等待态（Pending）、完成态（Fulfilled）和拒绝态（Rejected）。
     *
     * @param promise
     */
    @ReactMethod
    public void getResultForPromise(String msg, Promise promise) {
        try {
            //业务逻辑处理
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            String componentName = getCurrentActivity().getComponentName().toString();
            promise.resolve(componentName);
        } catch (Exception e) {
            promise.reject("100", e.getMessage());//promise 失败
        }

    }


    private int position = 0;

    /**
     * 向js发送事件
     *
     * @param index
     */
    @ReactMethod
    public void sendEventDemo(final String index) {
        new Thread() {
            @Override
            public void run() {
                while (!index.equals("0")) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final WritableMap map = Arguments.createMap();
                    map.putInt("position", position);
                    mTest.sendEvent("EventName", map);
                    position++;
                }
            }
        }.start();
    }


    /**
     * 从startActivityForResult中获取结果#
     *
     * @param promise
     */
    @ReactMethod
    public void pickImage(Promise promise) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            promise.reject(ERROR_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }
        mPickerPromise = promise;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        Intent chooser = Intent.createChooser(intent, "Pick an image");
        currentActivity.startActivityForResult(chooser, IMAGE_PICKER_REQUEST);
    }

    private ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            if (requestCode == IMAGE_PICKER_REQUEST) {
                if (mPickerPromise != null) {
                    if (resultCode == Activity.RESULT_CANCELED) {
                        mPickerPromise.reject(ERROR_PICKER_CANCELLED, "Image picker was cancelled");
                    } else if (resultCode == Activity.RESULT_OK) {
                        Uri uri = data.getData();
                        if (uri == null) {
                            mPickerPromise.reject(ERROR_NO_IMAGE_DATA_FOUND, "No image data found");
                        } else {
                            mPickerPromise.resolve(uri.toString());
                        }
                    }
                    mPickerPromise = null;
                }
            }

        }
    };


    /**
     * js监听生命周期事件
     */
    private void sendEventLife(String life) {
        final WritableMap map = Arguments.createMap();
        map.putString("life", life);
        mTest.sendEvent("EventLife", map);
    }

    @Override
    public void onHostResume() {
        Log.d(TAG, "onHostResume: ");
        sendEventLife("Resume");
    }

    @Override
    public void onHostPause() {
        Log.d(TAG, "onHostPause: ");
        sendEventLife("Pause");
    }

    @Override
    public void onHostDestroy() {
        Log.d(TAG, "onHostDestroy: ");
        sendEventLife("Destroy");
    }
}
