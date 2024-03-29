package com.shuben.zhixing.module.jsf;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.device.ScanManager;
import android.device.scanner.configuration.PropertyID;
import android.device.scanner.configuration.Triggering;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.inter.JsRet;
import com.base.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.util.SelectFac;
import com.base.zhixing.www.widget.CommonSetSelectPop;
import com.shuben.zhixing.module.mess.ScanMessActivity;
import com.shuben.zhixing.www.common.T;
import com.base.zhixing.www.inter.ScreenSelect;
import com.base.zhixing.www.inter.SelectTime;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class JavaScriptAndon {
	Activity mContxt;
    Handler handler;
    private WebView commonView;
    private SharedUtils sharedUtils;
    public JavaScriptAndon(Activity mContxt, Handler handler,WebView commonView) {
        this.mContxt = mContxt;
        this.handler = handler;
        this.commonView = commonView;
        sharedUtils = new SharedUtils(T.SET_F);
    }
    //公共部分
    private   String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }




    //mess-scan部分

    private ScanManager mScanManager;
    private SoundPool soundpool = null;
    private int soundid;
    private Vibrator mVibrator;
    private AssetManager assetManager;
    //初始化
    public void initMessScan(){
        P.c("初始化");
        mScanManager = new ScanManager();
        mScanManager.openScanner();
        mScanManager.switchOutputMode(0);
        mScanManager.setTriggerMode(Triggering.HOST);
        soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        assetManager = mContxt.getAssets();
        AssetFileDescriptor descriptor = null;
        try {
            descriptor = assetManager.openFd("sm.mp3");
            soundid = soundpool.load(descriptor, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mVibrator = (Vibrator) mContxt.getSystemService(Context.VIBRATOR_SERVICE);
    }
    private  volatile boolean isScaning = false;
    private    StackTraceElement[] messMlements;
    @JavascriptInterface
    public  void showScanCode( ){
        P.c("是否打开");
        if(messMlements!=null){
            messMlements = null;
        }
        messMlements = Thread.currentThread().getStackTrace();
        mScanManager.stopDecode();
        isScaning = true;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mScanManager.startDecode();
       
    }
    //得到mes扫码的函数壳
    public StackTraceElement[] getMessMlements(){
        return messMlements;
    }

    //暂停
    public void messPause(){

        if(mScanManager != null) {
            mScanManager.stopDecode();
            mScanManager.closeScanner();
            isScaning = false;
        }
    }
    public void state(){
        P.c("状态"+ mScanManager.getScannerState());
    }
    //完成
    private void messComplete(){
        isScaning = false;
        soundpool.play(soundid, 1, 1, 0, 0, 1);
        mVibrator.vibrate(200);
    }
    //取消注册
    public void nur(){
        if(mScanReceiver!=null){
            mContxt.unregisterReceiver(mScanReceiver);
        }
    }
    //注册广播
    public void reg(){
        IntentFilter filter = new IntentFilter();
        int[] idbuf = new int[]{PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG};
        String[] value_buf = mScanManager.getParameterString(idbuf);
        if(value_buf != null && value_buf[0] != null && !value_buf[0].equals("")) {
            filter.addAction(value_buf[0]);
        } else {
            filter.addAction(ScanManager.ACTION_DECODE);
        }

        mContxt.registerReceiver(mScanReceiver, filter);
    }

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            // TODO Auto-generated method stub
            messComplete();
            byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);
            int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
            byte temp = intent.getByteExtra(ScanManager.BARCODE_TYPE_TAG, (byte) 0);
          P.c("----codetype--" + temp);
            String barcodeStr = new String(barcode, 0, barcodelen);
          //  P.c("打印出"+barcodeStr);
           // Toasty.INSTANCE.showToast(context,barcodeStr);
            JSONObject object = new JSONObject();
            try {
                object.put("result",replaceBlank(barcodeStr));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //执行ok
            Message msg  = new Message();
            msg.what = 1;
            msg.obj = object.toString();
            handler.sendMessage(msg);
            barcodeStr = null;

        }

    };

    /**
     * 扫码显示标题栏
     * @param title
     */
    /* @JavascriptInterface
    public void showScanCode(String title){
       final StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        Intent intent = new Intent(mContxt, MessScanView.class);
        intent.putExtra("type",0);
        intent.putExtra("elements",elements);
        intent.putExtra("title",title);
        mContxt.startActivityForResult(intent,0);



    }*/


    //------Mini mess

    /**
     * 扫码显示标题栏
     * @param title
     */
    @JavascriptInterface
    public void showScan(String title){
        final StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        Intent intent = new Intent(mContxt,ScanMessActivity.class);
        intent.putExtra("type",0);
        intent.putExtra("elements",elements);
        intent.putExtra("title",title);
        mContxt.startActivityForResult(intent,0);
    }
    private View[] txtView;
    public void addObj(View []txtView){
        this.txtView = txtView;
    }

    /**
     * 控制显示返回
     * @param isShow
     */
    @JavascriptInterface
    public void showBackBtn(boolean isShow,String txt){
          mContxt.runOnUiThread(() -> {
              if(txtView[1] instanceof TextView){
                  (  (TextView)txtView[1]).setText(txt);
              }
              if(txtView[0] instanceof ImageView){
                  //是返回
                  if(isShow){
                      txtView[0].setVisibility(View.VISIBLE);
                  }else{
                      txtView[0].setVisibility(View.INVISIBLE);
                  }
                  txtView[0].setOnClickListener(l ->{
                      backJs();
                  });
              }
             if(txtView[2] instanceof ImageView){
                  txtView[2].setOnClickListener(view -> {
                      showHistory("javascript:GoHistoryVue()");
                  });
              }
          });
    }

    //--------安灯



    public void loadJs(String str){
        P.c("执行"+str);
        mContxt.runOnUiThread(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                commonView.evaluateJavascript(str, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        P.c("return   "+s);
                    }
                });
            }else{
                commonView.loadUrl(str);
            }
        });

    }

    /**
     *
     * @param json
     * @param pop 0是预览   1是上传
     */
    @JavascriptInterface
        public void uploadAndonImage(String json,int pop){
           Message msg = new Message();
           msg.what = 4;
           msg.obj = json;
           msg.arg1 = pop;
           handler.sendMessage(msg);
        }

        /**
         * 返回当前登录的用户信息
         * @return
         */
        @JavascriptInterface
        public String getCurrentUserInfo(){
        JSONObject jsonObject =new JSONObject();
        try {
            jsonObject.put("userId",SharedPreferencesTool.getMStool(mContxt).getUserId());
            jsonObject.put("userCode",SharedPreferencesTool.getMStool(mContxt).getUserCode());
            jsonObject.put("userName",SharedPreferencesTool.getMStool(mContxt).getUserName());
            jsonObject.put("tenanId",SharedPreferencesTool.getMStool(mContxt).getTenantId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 返回基础地址
     * @return
     */
    @JavascriptInterface
    public String getAndroidUrl(){
        P.c("返回给安灯"+SharedPreferencesTool.getMStool(mContxt).getIp());
        return  SharedPreferencesTool.getMStool(mContxt).getIp();
    }

    /**
     * 安灯需要的数据初始化
     */
    @JavascriptInterface
    public void MainInit(){
        P.c("vue初始化");
        loadJs(commonView.getTag().toString());
       /* Observable.timer(400, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

            }
        });*/
    }
    /**
     * H5对android手机原生日历的调用
     * @param format 时间格式化 ，需要返回的时间格式
     * @param device  调用设备来源  H5的参数是0
     * @param isShowClear  是否显示清除,填写按钮名字即可，空字符则表示不显示
     *                     type    是否显示时间   2只显示年月日   1只显示时间, 3只显示年月
     */
    @JavascriptInterface
    public void changeTime(final String format, final int device,String isShowClear,int type) {
        final StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        ChangeTime time = new ChangeTime(mContxt,isShowClear, type);
        time.setSelect(new SelectTime() {
            @Override
            public void select(final String time, long timestp) {
                    switch (device){
                        case 0:
                            //VUE
                            JSONObject object = new JSONObject();
                            try {
                                object.put("time", TimeUtil.getTimeTo(format,time));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            callBK(elements,(time.length()==0?"":object.toString()));


                            break;
                    }
            }
        });
        time.showSheet();
    }
    @JavascriptInterface
    public void backAndroid(){
//        Toasty.INSTANCE.showToast(mContxt,"触发原生返回");
     /*   mContxt.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                commonView.clearFormData();
                commonView.clearCache(true);
            }
        });*/

        AppManager.getAppManager().finishActivity();
    }


    /**
     * 返回当前登录的用户信息
     * //根据异常id传入
     * @return
     */
    @JavascriptInterface
    public String getAndonCurrentUserInfo(){

        JSONObject jsonObject =new JSONObject();
        try {
            jsonObject.put("userId",SharedPreferencesTool.getMStool(mContxt).getUserId());
            jsonObject.put("userCode",SharedPreferencesTool.getMStool(mContxt).getUserCode());
            jsonObject.put("userName",SharedPreferencesTool.getMStool(mContxt).getUserName());
            jsonObject.put("tenanId",SharedPreferencesTool.getMStool(mContxt).getTenantId());
            jsonObject.put("factory_id",sharedUtils.getStringValue("factory_id"));
            jsonObject.put("workshop_id",sharedUtils.getStringValue("workshop_id"));
            jsonObject.put("line_id",sharedUtils.getStringValue("line_id"));
            jsonObject.put("station_id",sharedUtils.getStringValue("station_id"));
            jsonObject.put("exception_id","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * H5默认传3
     * @param tos 接收人，多个人之间用逗号分隔
     * @param title 推送标题
     *              des 描述
     * @param txt 推送内容
     * @param flag 设备来源  H5是3
     * @param  action 动作，表示点击推送消息之后要产生什么动作。默认空字符
     *                module  模块名称
     */
    @JavascriptInterface
    public void pushToUsers(String tos,String title,String des,String txt,int flag,int action,String module){
        P.c(tos+"=="+title+"=="+des+"=="+txt+"=="+flag+"=="+action+"==="+module);


       /* if(ChatSdk.isConnectSuccess()){
            try {
                JSONObject object = new JSONObject();
                object.put("title",title);
                object.put("des",des);
                JSONObject o = new JSONObject(txt);
                object.put("txt",o);
                object.put("flag",flag);
                object.put("action",action);
                String from  = SharedPreferencesTool.getMStool(mContxt).getUserId();
                P.c( object.toString( ));
                String IP = SharedPreferencesTool.getMStool(mContxt).getString("IP");
                PushMessageModel push=new PushMessageModel(object.toString(),from,tos,module,IP);
                push.getSource(new Function1<HttpResult<String>, Unit>() {
                    @Override
                    public Unit invoke(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.getSuccess()){
                            //服务器响应成功
                            stringHttpResult.getData();
                            Toasty.INSTANCE.showToast(mContxt,stringHttpResult.getData());
                        }else {
                            Toasty.INSTANCE.showToast(mContxt,stringHttpResult.getMessage());
                        }
                        return null;
                    }

                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }*/
    }
    private boolean isSave = false;
    public void setSaveInfo(boolean isSave){
        this.isSave = isSave;
    }
    public boolean isSave(){
        return isSave;
    }

    @JavascriptInterface
    public void selectScreen(int STEP,int save){

        SelectFac fac = new SelectFac(mContxt,handler,commonView);
        fac.setSaveInfo(isSave());
        if(screenSelect!=null){
            fac.setScreenListen(screenSelect);
        }

        fac.setJsRet(new JsRet() {
            @Override
            public void result(StackTraceElement[] elements, String result) {
                callBK(elements,result);
            }
        });
        fac.selectScreen(STEP,save);
        if(true){
            return;
        }
        P.c("深度"+STEP);
        if(STEP<1||STEP>4){
            Toasty.INSTANCE.showToast(mContxt,"参数传入错误");
            return;
        }
        int stemp = 1;
        final StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContxt,null,"工厂");
        setSelectPop.getSet().put("ApiCode", "GetFactoryList");
        setSelectPop.setMidH(true);
        if(stemp==STEP){
            setSelectPop.isDoall(false);
        }

        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                    if(isSave){
                        sharedUtils.setStringValue("factory_id",id);
                        sharedUtils.setStringValue("factory_name",name);
                    }
                if(STEP==stemp){
                    screenBack(elements,new String[]{id,code,name},null,null,null);
                }else{
                    selectScreen0(new String[]{id,code,name},elements,STEP);
                }
            }

        });
        setSelectPop.showSheet();
    }
    private void selectScreen0(final String id0[], final StackTraceElement[] elements,int STEP){

        int stemp = 2;
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContxt,null,"车间");
        setSelectPop.getSet().put("ApiCode", "GetWorkShopList");
        setSelectPop.getSet().put("FactoryId",id0[0]);
        if(STEP==stemp){
            setSelectPop.isDoall(false);
        }
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                if(isSave){
                    sharedUtils.setStringValue("workshop_id",id);
                    sharedUtils.setStringValue("workshop_code",code);
                    sharedUtils.setStringValue("workshop_name",name);
                }
                if(STEP==stemp){
                    screenBack(elements,id0,new String[]{id,code,name},null,null);
                }else{
                    selectScreen1(id0,new String[]{id,code,name},elements,STEP);
                }
            }
        });
        setSelectPop.showSheet();
    }

    private void selectScreen1(final String id0[],String id1[], final StackTraceElement[] elements,int STEP ){
        int stemp = 3;
        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContxt,null,"产线");
        setSelectPop.getSet().put("ApiCode", "GetLineList");
        setSelectPop.getSet().put("WorkShopId",id1[0]);
        P.c("是什么"+(STEP==stemp));
        if(STEP==stemp){
            setSelectPop.isDoall(false);
        }

        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {
                if(isSave){
                    sharedUtils.setStringValue("line_id",id);
                    sharedUtils.setStringValue("line_name",name);
                }

                if(STEP==stemp){
                    screenBack(elements,id0,id1,new String[]{id,code,name},null);
                }else{
                    selectScreen2(id0,id1,new String[]{id,code,name},elements);
                }

            }


        });
        setSelectPop.showSheet();
    }
    private void selectScreen2(final String id0[], final String id1[],String id2[], final StackTraceElement[] elements){

        CommonSetSelectPop setSelectPop = new CommonSetSelectPop(mContxt,null,"工位");
        setSelectPop.getSet().put("ApiCode", "GetLineStationList");
        setSelectPop.getSet().put("LineId", id2[0]);
        setSelectPop.isDoall(false);
        setSelectPop.setMidH(true);
        setSelectPop.setSelect(new SetSelect() {
            @Override
            public void select(String id, String code, String name) {

                if(isSave){
                    sharedUtils.setStringValue("station_id",id);
                    sharedUtils.setStringValue("station_name",name);
                }
                    if(commonView==null){
                        if(screenSelect!=null){
                            screenSelect.select(id0,id1,id2,new String[]{id,code,name});
                        }
                    }else{
                        screenBack(elements,id0,id1,id2,new String[]{id,code,name});
                    }
            }
        });
        setSelectPop.showSheet();
    }
    private void screenBack( final StackTraceElement[] elements,String id0[],String id1[],String id2[],String id3[]){
        JSONObject object = new JSONObject();
        try {
            if(id0!=null){
                object.put("factory",id0[0]);
                object.put("factory_name",id0[2]);
            }

            if(id1!=null){
                object.put("work",id1[0]);
                object.put("work_name",id1[2]);
            }

            if(id2!=null){
                object.put("line",id2[0]);
                object.put("line_name",id2[2]);
            }

            if(id3!=null){
                object.put("station",id3[0]);
                object.put("station_name",id3[2]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        callBK(elements,object.toString());
    }
    private ScreenSelect screenSelect;
    public void setScreenListen(ScreenSelect screenSelect){
            this.screenSelect = screenSelect;
    }
    public void callBK(final StackTraceElement[] elements, final String json){
        mContxt.runOnUiThread( () -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                P.c("执行回调"+"javascript:"+elements[2].getMethodName()+"T('"+json+"')");
                commonView.evaluateJavascript("javascript:"+elements[2].getMethodName()+"T('"+json+"')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        P.c("执行情况"+s);
                    }
                });
            }else{
                commonView.loadUrl("javascript:"+elements[2].getMethodName()+"T('"+json+"')");
            }
        });
    }

    /**
     * 历史记录
     */
    private void showHistory(String js){
        mContxt.runOnUiThread(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                commonView.evaluateJavascript( js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {

                    }
                });
            }else{
                commonView.loadUrl(js);
            }
        });
    }

    public void backJs(){
       mContxt.runOnUiThread(() -> {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

               commonView.evaluateJavascript("javascript:goBack()", new ValueCallback<String>() {
                   @Override
                   public void onReceiveValue(String s) {

                   }
               });
           }else{
               commonView.loadUrl("javascript:goBack()");
           }
       });
    }

}

