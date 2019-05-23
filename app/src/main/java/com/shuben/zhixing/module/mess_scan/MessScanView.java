package com.shuben.zhixing.module.mess_scan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.widget.TextView;

import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.www.R;

import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class MessScanView extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.mess_scan_layout;
    }

    @Override
    public void process(Message msg) {

    }
    private ZXingView zxingview;
    private TextView tetle_text;
    @Override
    public void initLayout() {
        setStatus(-1);
        tetle_text = findViewById(R.id.tetle_text);
      if(  getIntent().hasExtra("title")){
          tetle_text.setText(getIntent().getStringExtra("title"));
      }else{
          tetle_text.setText("扫描");
      }

        zxingview = findViewById(R.id.zxingview);
        zxingview.setType(BarcodeType.ALL, null); // 识别所有类型的码
        zxingview.setDelegate(delegate);
        zxingview.startCamera();
        zxingview.startSpotAndShowRect();
    }
    int TYPE = -1;
    StackTraceElement[] elements = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent.hasExtra("type")){
            TYPE = intent.getIntExtra("type",-1);
        }
        if(getIntent().hasExtra("elements")){
            elements = (StackTraceElement[]) getIntent().getSerializableExtra("elements");
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
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
    private QRCodeView.Delegate delegate = new QRCodeView.Delegate() {
        @Override
        public void onScanQRCodeSuccess(String result) {


            switch (TYPE){
                case 0:
                    Intent intent = new Intent();
                    intent.putExtra("ret",result);
                    if(elements!=null){
                        intent.putExtra("elements",elements);
                    }
                    setResult(1000,intent);
                    AppManager.getAppManager().finishActivity();
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
