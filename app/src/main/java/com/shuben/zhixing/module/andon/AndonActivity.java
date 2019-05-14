package com.shuben.zhixing.module.andon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.module.jsf.JavaScriptAndon;
import com.shuben.zhixing.www.R;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AndonActivity extends BaseActvity {
    @Override
    public void process(Message msg) {

    }

    @Override
    public void backActivity(View v) {

        super.backActivity(v);

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
              /*  if(!INIT){
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
                }*/
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
            commonView.loadUrl("file://"+getIntent().getStringExtra("file"));
        }


    }

    private String params(String params) throws UnsupportedEncodingException {
       return params;
    }

    @Override
    public int getLayoutId() {
        return R.layout.andon_web;
    }
    private String INIT_VALUE ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().hasExtra("init_value")){
            P.c("正常接收"+getIntent().getStringExtra("init_value"));
            INIT_VALUE = getIntent().getStringExtra("init_value");
            commonView.setTag(INIT_VALUE);
        }
        sendInfo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if(getIntent().hasExtra("init_value")){
            P.c("通知接收"+getIntent().getStringExtra("init_value"));
            scriptObject.loadJs(getIntent().getStringExtra("init_value"));
        }
    }
    /*
    {"AppCode":"Andon","ApiCode":"GetSaveAppIp","TenantId":"ed37a619-d6ba-4e2f-9486-c28f3f2c2978","IP":"15936424949","UserId":"44bc1450-d4ee-2022-36b3-df255a1aed53","UserCode":"00000","UserName":"15936424949","UserHostAddress":"1234","Port":"1234"}
     */
    private void sendInfo(){
        Map<String, String> params = new HashMap<>();
        params.put("AppCode", "Andon");
        params.put("ApiCode", "GetSaveAppIp");
        params.put("UserName",SharedPreferencesTool.getMStool(AndonActivity.this).getUserName());
        params.put("UserId", SharedPreferencesTool.getMStool(AndonActivity.this).getUserId());
        params.put("UserCode", SharedPreferencesTool.getMStool(AndonActivity.this).getUserCode());
        params.put("TenantId", SharedPreferencesTool.getMStool(AndonActivity.this).getTenantId());

        try {
            params.put("UserHostAddress",getLocalHostLANAddress().getHostAddress());
            params.put("IP",getLocalHostLANAddress().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            params.put("IP","");
        }
        params.put("Port","");
        httpPostVolley(SharedPreferencesTool.getMStool(AndonActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {


            }

            @Override
            public void error(VolleyError error) {

            }
        },"");
    }
    private   InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }

}
