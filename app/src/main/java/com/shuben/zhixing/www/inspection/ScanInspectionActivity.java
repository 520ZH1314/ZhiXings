package com.shuben.zhixing.www.inspection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.fragment.InspectionMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanInspectionActivity extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.scan_mess_layout;
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
                case 2:
                    JSONObject object = null;
                    try {
                        object = new JSONObject(result);
                        String line = object.getString("LineId");
                        load(line);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toasty.INSTANCE.showToast(ScanInspectionActivity.this,"二维码不正确");
                    }



                    break;
            }
        }
        private void load( String LineId){

            Map<String,String> params = new HashMap<>();
            params.put("ApiCode","GetLineList");
            params.put("AppCode","EPS");
            params.put("TenantId", SharedPreferencesTool.getMStool(ScanInspectionActivity.this).getTenantId());
            params.put("LineId",LineId);
            
            showDialog("数据验证中");
            httpPostSONVolley(SharedPreferencesTool.getMStool(ScanInspectionActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
                @Override
                public void success(JSONObject jsonObject) {
                        dismissDialog();
                    try {
                        JSONArray array = jsonObject.getJSONArray("rows");
                        Intent intent = new Intent();

                        JSONObject o = new JSONObject();
                        o.put("LineId",array.getJSONObject(0).getString("LineId"));
                        o.put("LineName",array.getJSONObject(0).getString("LineName"));
                        o.put("pos",getIntent().getIntExtra("pos",-1));
                        intent.putExtra("ret",o.toString());


                        setResult(1000,intent);
                        AppManager.getAppManager().finishActivity();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void error(VolleyError error) {
                    dismissDialog();
                }
            });
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
