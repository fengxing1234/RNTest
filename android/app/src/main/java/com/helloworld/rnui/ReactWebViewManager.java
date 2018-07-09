package com.helloworld.rnui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by fengxing on 2018/7/9.
 */

public class ReactWebViewManager extends SimpleViewManager<WebView> {
    @Override
    public String getName() {
        return "AndroidRCTWebView";
    }

    @Override
    protected WebView createViewInstance(ThemedReactContext reactContext) {
        WebView webView = new RCTWebView(reactContext);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return webView;
    }

    @ReactProp(name = "url")
    public void setUrl(WebView webView, @Nullable String url) {
        webView.loadUrl(url);
    }

    @ReactProp(name = "html")
    public void setHtml(WebView webView, @Nullable String html) {
        webView.loadData(html, "text/html; charset=utf-8", "UTF-8");
    }

    public class RCTWebView extends WebView {
        public RCTWebView(Context context) {
            super(context);
        }

        public RCTWebView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public RCTWebView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
            Log.e("TAG", "onScrollChanged");
            WritableMap event = Arguments.createMap();
            event.putInt("ScrollX", l);
            event.putInt("ScrollY", t);
            ReactContext reactContext = (ReactContext) getContext();
            reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                    getId(), "topChange", event);
        }
    }
}
