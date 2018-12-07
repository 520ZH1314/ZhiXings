package com.shuben.zhixing.module.andon;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdk.chat.ChatSdk;
import com.shuben.zhixing.module.mess.ScanMessActivity;
import com.shuben.zhixing.www.inter.ScreenSelect;
import com.base.zhixing.www.inter.SelectTime;
import com.shuben.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.wxx.net.HttpResult;
import com.base.zhixing.www.AppManager;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.push.PushMessageModel;
import com.base.zhixing.www.util.TimeUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeTime;
import com.shuben.zhixing.www.widget.CommonSetSelectPop;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class JavaScriptAndon {
	Activity mContxt;
    Handler handler;
    private WebView commonView;
    public JavaScriptAndon(Activity mContxt, Handler handler,WebView commonView) {
        this.mContxt = mContxt;
        this.commonView = commonView;
    }
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
          });
    }

    //--------安灯

    public void loadJs( ){
        P.c("执行"+commonView.getTag().toString());
        mContxt.runOnUiThread(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                commonView.evaluateJavascript(commonView.getTag().toString(), new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        P.c("return   "+s);
                    }
                });
            }else{
                commonView.loadUrl(commonView.getTag().toString());
            }
        });



    }
    @JavascriptInterface
    public String getAndroidUrl(){
        return  SharedPreferencesTool.getMStool(mContxt).getIp();
    }
    @JavascriptInterface
    public void MainInit(){

        loadJs();

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
        AppManager.getAppManager().finishActivity();
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
        if(ChatSdk.isConnectSuccess()){
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
                PushMessageModel push=new PushMessageModel(object.toString(),from,tos,module);
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

        }
    }
    private boolean isSave = false;
    public void setSaveInfo(boolean isSave){
        this.isSave = isSave;
    }
    //
    @JavascriptInterface
    public void selectScreen(int STEP){
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
                        SharedPreferencesTool.getMStool(mContxt).setString("factory_id",id);
                        SharedPreferencesTool.getMStool(mContxt).setString("factory_name",name);
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
                    SharedPreferencesTool.getMStool(mContxt).setString("workshop_id",id);
                    SharedPreferencesTool.getMStool(mContxt).setString("workshop_code",code);
                    SharedPreferencesTool.getMStool(mContxt).setString("workshop_name",name);
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
                    SharedPreferencesTool.getMStool(mContxt).setString("line_id",id);
                    SharedPreferencesTool.getMStool(mContxt).setString("line_name",name);
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
                    SharedPreferencesTool.getMStool(mContxt).setString("station_id",id);
                    SharedPreferencesTool.getMStool(mContxt).setString("station_name",name);
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

                    }
                });
            }else{
                commonView.loadUrl("javascript:"+elements[2].getMethodName()+"T('"+json+"')");
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

