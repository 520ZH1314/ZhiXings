package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.module.andon.JavaScriptAndon;

import java.io.UnsupportedEncodingException;

public class CommonWebActivity extends BaseActvity {
    @Override
    public void process(Message msg) {

    }
    private WebView commonView;
    private JavaScriptAndon scriptObject;
    @Override
    public void initLayout() {
        commonView = findViewById(R.id.common_web);
        commonView.getSettings().setBuiltInZoomControls(false);
        commonView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        WebSettings webSettings = commonView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSettings.setDatabasePath(this.getDir("databases", 0).getPath());
        webSettings.setBuiltInZoomControls(false);
      //  scriptObject = new JavaScriptAndon(CommonWebActivity.this,getHandler(),commonView);
        commonView.addJavascriptInterface(scriptObject, "jsShared");//本地快速播货


        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                commonView.loadUrl(url);

               // commonView.loadUrl("javascript:callJS()");
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (!commonView.getSettings().getLoadsImagesAutomatically()) {
                    commonView.getSettings().setLoadsImagesAutomatically(true);
                }
//


                commonView.evaluateJavascript(getIntent().getStringExtra("js"), new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        P.c("返回值"+s);
                    }
                });
            }
        };
        WebChromeClient webChromeClient = new WebChromeClient() {
            public void onReceivedTitle(WebView view, String tl) {
//



            }

            ;
        };
        commonView.setWebChromeClient(webChromeClient);
        commonView.setWebViewClient(webViewClient);


        if (getIntent().hasExtra("url")) {
            //getIntent().getStringExtra("url")
            commonView.loadUrl(getIntent().getStringExtra("url"));

        } else if (getIntent().hasExtra("content")) {

        } else if (getIntent().hasExtra("file")) {
            commonView.loadUrl(getIntent().getStringExtra("file"));
        }
    }
    private String params(String params) throws UnsupportedEncodingException {
       return params;
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_web;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
