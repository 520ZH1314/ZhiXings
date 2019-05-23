package com.shuben.zhixing.module.mess_scan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.module.jsf.JavaScriptAndon;
import com.shuben.zhixing.www.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
@Route(path = "/mess_scan/MessScanActivity")
public class MessScanActivity extends BaseActvity {


    @Override
    public void process(Message msg) {

    }
    private ImageView tetle_back,share,close;
    private TextView title;
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
        setStatus(-1);
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 11) {
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
        tetle_back = findViewById(R.id.tetle_back);
        share = findViewById(R.id.share);
        close = findViewById(R.id.close);
        title = findViewById(R.id.title);
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
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSettings.setDatabasePath(this.getDir("databases", 0).getPath());
        webSettings.setBuiltInZoomControls(false);
        scriptObject = new JavaScriptAndon(MessScanActivity.this,getHandler(),commonView);
        scriptObject.addObj(new View[]{tetle_back,title,share,close});//添加四个组件
        close.setOnClickListener(v->{AppManager.getAppManager().finishActivity();
        });
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
            commonView.loadUrl(getIntent().getStringExtra("url"));

        } else if (getIntent().hasExtra("content")) {

        } else if (getIntent().hasExtra("file")) {

            commonView.loadUrl("file://"+getIntent().getStringExtra("file"));



        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){

            scriptObject.backJs();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    private String params(String params) throws UnsupportedEncodingException {
        return params;
    }

    @Override
    public int getLayoutId() {
        return R.layout.mess_scan_main;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(0==requestCode&&resultCode==1000){
            //
            StackTraceElement[] elements = (StackTraceElement[]) data.getSerializableExtra("elements");
            JSONObject object = new JSONObject();
            try {
                object.put("result",data.getStringExtra("ret"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            P.c("自动调用");
            if(elements!=null){
                scriptObject.callBK(elements,object.toString());
            }

        }

    }
}
