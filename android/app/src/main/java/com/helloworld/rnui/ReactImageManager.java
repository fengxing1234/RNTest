package com.helloworld.rnui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.Map;

import javax.annotation.Nullable;

import static com.helloworld.utils.BitmapUtils.uri2Bitmap;

/**
 * Created by fengxing on 2018/7/3.
 * <p>
 * React Native封装Android原生UI组件
 * <p>
 * 1. 创建ViewManager的子类。
 * 2. 实现createViewInstance方法，返回UI实例。
 * 3. 使用@ReactProp（或@ReactPropGroup）注解，注册UI组件的属性。
 * 4. 创建视图管理包，注册ViewManager到应用程序。
 * 5. 实现JavaScript模块。
 * 6. android层向js层发送消息。
 * 7. js层向android层发送消息。
 */

public class ReactImageManager extends SimpleViewManager<ImageView> implements LifecycleEventListener {

    private static final String TAG = "ReactImageManager";

    private static final String REACT_NAME = "ReactImageManager";

    public static final String SRC = "src";

    private static final String HANDLE_METHOD_NAME = "handleTask"; // 交互方法名
    private static final int HANDLE_METHOD_ID = 1; // 交互命令ID

    private static final String EVENT_NAME_ONCLICK = "onClick";

    private ThemedReactContext mContext;

    @Override
    public String getName() {
        return REACT_NAME;
    }


    /**
     * 在事件中传递数据到RN层
     *
     * @param reactContext
     * @return
     */
    @Override
    protected ImageView createViewInstance(ThemedReactContext reactContext) {
        mContext = reactContext;
        mContext.addLifecycleEventListener(this);
        final ImageView imageView = new ImageView(reactContext);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WritableMap map = Arguments.createMap();
                map.putString("msg", "Android端点击了图片!");
                mContext.getJSModule(RCTEventEmitter.class).
                        receiveEvent(imageView.getId(), EVENT_NAME_ONCLICK, map);
            }
        });
        return imageView;
    }

    /**
     * 接受交互通知
     *
     * @return
     */
    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {

        return MapBuilder.of(HANDLE_METHOD_NAME, HANDLE_METHOD_ID);
    }

    /**
     * 根据具体ID处理任务
     *
     * @param root
     * @param commandId
     * @param args
     */
    @Override
    public void receiveCommand(ImageView root, int commandId, @Nullable ReadableArray args) {
        switch (commandId){
            case HANDLE_METHOD_ID:
                if(args != null) {
                    String name = args.getString(0);//获取第一个位置的数据
                    final String string = args.getString(1);
                    Log.d(TAG, "receiveCommand: "+name+" name :"+string);
                    Toast.makeText(mContext, "收到RN层的任务通知，开始在原生层处理任务...", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 自定义事件
     *
     * @return
     */
    @Nullable
    @Override
    public Map getExportedCustomDirectEventTypeConstants() {
        Map<String, Map<String, String>> map = MapBuilder.of(EVENT_NAME_ONCLICK, MapBuilder.of("registrationName", EVENT_NAME_ONCLICK));
        return map;
    }

    @ReactProp(name = "imageName")
    public void setImageSrc(final ImageView imageView, String url) {
        //Bitmap bitmap = loadBitmap(url);
        Bitmap bitmap = uri2Bitmap(mContext, Uri.parse(url));
        imageView.setImageBitmap(bitmap);
    }

    //@ReactProp(name = "background")
    public void setBackground(ImageView imageView, String background) {
        int color = Color.parseColor(background);
        imageView.setBackgroundColor(color);
    }


    @Override
    public void onHostResume() {
        Log.d(TAG, "onHostResume: ");
    }

    @Override
    public void onHostPause() {
        Log.d(TAG, "onHostPause: ");
    }

    @Override
    public void onHostDestroy() {
        Log.d(TAG, "onHostDestroy: ");
    }
}
