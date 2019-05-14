package com.shuben.zhixing.www.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.Tips;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.CommonTips;
import com.shuben.zhixing.www.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanLoginActivity extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.scan_login_layout;
    }

    @Override
    public void process(Message msg) {

    }
    private ZXingView zxingview;
    private TextView tetle_text;
    @Override
    public void initLayout() {
        setStatus(-1);
        tetle_text = findViewById(com.zhixing.masslib.R.id.tetle_text);
        tetle_text.setText("扫描");
        zxingview = findViewById(com.zhixing.masslib.R.id.zxingview);
        zxingview.setType(BarcodeType.TWO_DIMENSION, null); // 识别所有类型的码
        zxingview.setDelegate(delegate);
        zxingview.startCamera();
        zxingview.startSpotAndShowRect();
    }
    int TYPE = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent.hasExtra("type")){
            TYPE = intent.getIntExtra("type",-1);
        }
    }

    @Override
    protected void onStop() {
        zxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingview.onDestroy();
        super.onDestroy();
    }
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
    /*
    {"AppCode":"EPS","ApiCode":"ScanLogin","token":"3943e7da-696b-72e4-6432-0c62dd910636","usercode":"15936424949","password":"1234"}
     */
    private void login(String token){
        Map<String,String> params = new HashMap<>();
        params.put("AppCode","EPS");
        params.put("ApiCode","ScanLogin");
        params.put("token",token);
        params.put("usercode",SharedPreferencesTool.getMStool(ScanLoginActivity.this).getPhone());
        params.put("password",SharedPreferencesTool.getMStool(ScanLoginActivity.this).getPassword());
        showDialog("网页端登录中");
        httpPostVolley(SharedPreferencesTool.getMStool(ScanLoginActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    if(  jsonObject.getString("status").equals("success")){
                        Toasty.INSTANCE.showToast(ScanLoginActivity.this,"网页端登录成功!");

                    }
                    AppManager.getAppManager().finishActivity();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(VolleyError error) {

            }
        },true);
    }

    private QRCodeView.Delegate delegate = new QRCodeView.Delegate() {
        @Override
        public void onScanQRCodeSuccess(String result) {


            switch (TYPE){
                case 0:
//                    Intent intent = new Intent();
//                    intent.putExtra("ret",result);
//                    setResult(1000,intent);
//                    AppManager.getAppManager().finishActivity();
                    vibrate();
                 //   Toasty.INSTANCE.showToast(ScanLoginActivity.this,result);
                    zxingview.stopCamera();
                    CommonTips tips = new CommonTips(ScanLoginActivity.this,getHandler());
                    tips.init("离开","登录","识别完成!\n是否登录网页智行力");
                    tips.setI(new Tips() {
                        @Override
                        public void cancel() {
                            AppManager.getAppManager().finishActivity();
                        }
                        @Override
                        public void sure() {
                            login(result);
                        }
                    });
                    tips.setCancelLis(new CommonTips.CancelI() {
                        @Override
                        public void cancel() {
                            AppManager.getAppManager().finishActivity();
                        }
                    });
                    tips.showSheet();
                    break;
                case 1:
//                     Toasty.INSTANCE.showToast(ScanMassActivity.this,result);
                    P.c(result);
                     vibrate();
                     zxingview.startSpot(); // 延迟0.1秒后开始识别
                    break;
            }

        }

        @Override
        public void onCameraAmbientBrightnessChanged(boolean isDark) {
            // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
            String tipText = zxingview.getScanBoxView().getTipText();
            String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
            if (isDark) {
                if (!tipText.contains(ambientBrightnessTip)) {
                    zxingview.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
                }
            } else {
                if (tipText.contains(ambientBrightnessTip)) {
                    tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                    zxingview.getScanBoxView().setTipText(tipText);
                }
            }
        }

        @Override
        public void onScanQRCodeOpenCameraError() {

        }
    };
}
