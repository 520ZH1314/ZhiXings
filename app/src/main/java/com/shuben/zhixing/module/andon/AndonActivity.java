package com.shuben.zhixing.module.andon;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.common.P;

import java.io.UnsupportedEncodingException;

public class AndonActivity extends BaseActvity {


    @Override
    public void process(Message msg) {

    }
    private ProgressBar process_bar;
    private WebView commonView;
    private JavaScriptAndon scriptObject;

    public void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            commonView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            commonView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            commonView.getSettings().setLoadsImagesAutomatically(false);
        }
    }
    private boolean INIT = false;
    @SuppressLint("WrongViewCast")
    @Override
    public void initLayout() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        process_bar = findViewById(R.id.process_bar);
        commonView = findViewById(R.id.common_web);
      //  init();
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
        scriptObject = new JavaScriptAndon(AndonActivity.this,getHandler(),commonView);
        commonView.addJavascriptInterface(scriptObject, "jsDo");//本地快速播货


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
                process_bar.setVisibility(View.GONE);
                if(!INIT){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        commonView.evaluateJavascript(getIntent().getStringExtra("js"), new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {

                                INIT = true;
                            }
                        });
                    }else{
                        commonView.loadUrl(getIntent().getStringExtra("js"));
                    }
                }
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                process_bar.setVisibility(View.VISIBLE);
            }
        };
        WebChromeClient webChromeClient = new WebChromeClient() {
            public void onReceivedTitle(WebView view, String tl) {
//



            }

            @Override
            public void onProgressChanged(WebView webView, int newProgress) {

                if (newProgress == 100) {
                    process_bar.setVisibility(View.GONE);
                } else {
                    if (process_bar.getVisibility() == View.GONE)
                        process_bar.setVisibility(View.GONE);
                    process_bar.setProgress(newProgress);
                }
                super.onProgressChanged(webView, newProgress);

            }

            ;
        };
        commonView.setWebChromeClient(webChromeClient);
        commonView.setWebViewClient(webViewClient);


        if (getIntent().hasExtra("url")) {
            //getIntent().getStringExtra("url")
            P.c("存在"+getIntent().getStringExtra("url"));
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
    private String INIT_VALUE ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().hasExtra("init_value")){
            INIT_VALUE = getIntent().getStringExtra("init_value");
            commonView.setTag(INIT_VALUE);
        }
    }

}
