package com.base.zhixing.www;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.view.Toasty;
import com.githang.statusbar.StatusBarCompat;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/21.
 */

public abstract class BaseActvity extends FragmentActivity   {
    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };
    public void backActivity(View v){

        AppManager.getAppManager().finishActivity(this);
    }
    private RequestQueue requestQueue ;
    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    /**
     * 显示toast
     * @param msg
     */
    public void showToast(String msg){
        Toasty.INSTANCE.showToast(BaseActvity.this,msg);
    }
    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    private Base_Handler base_handler;
    public LayoutInflater inflater;
    private LoadingDailog dialog;//加载动画
    public abstract int getLayoutId();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        base_handler = new Base_Handler(BaseActvity.this);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        requestQueue = Volley.newRequestQueue(BaseActvity.this);
        setContentView(getLayoutId());
        AppManager.getAppManager().addActivity(this);

        //        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }
    public Handler getHandler(){
        return  base_handler;
    }
    public void initDialog(){
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(BaseActvity.this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initDialog();
        initLayout();
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void setStatus(int color){

        if(color!=-1){
            StatusBarCompat.setStatusBarColor(this,ContextCompat.getColor(this,color), true);
        }else{
            StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.title_bg), true);
        }

    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
    /**
     * 接收handler消息处理方法

     */
    public abstract  void process(Message msg);
    /**
     * 空间数据初始化方法
     */
    public abstract void  initLayout();
    private class Base_Handler extends Handler {
        WeakReference<BaseActvity> mLeakActivityRef;
        public Base_Handler(BaseActvity leakActivity) {
            mLeakActivityRef = new WeakReference<BaseActvity>(leakActivity);
        }
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if(mLeakActivityRef.get()!=null){
                process(msg);
            }
        }
    }
    //volley方式数据加载构造
    public void httpPostVolley(String URL, Map<String,String> params, final VolleyResult result, final boolean isShow){
        httpPostSONVolley(URL,params,result);

        dis(isShow);
    }
    /**
     * 隐藏
     * @param isShow
     */
    private void dis(boolean isShow){
        if(isShow){
            if(dialog!=null){
                dialog.show();
            }
        }
    }
    public void httpPostSONVolley(String URL, Map<String,String> params, final VolleyResult result){
        FileUtils.parms(params);

        JsonObjectRequest newMissRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if(result!=null){
                    P.c("统一处理库success"+jsonObject.toString());
                    result.success(jsonObject);
                    if(dialog!=null){
                        dialog.cancel();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                P.c("错误"+volleyError.getLocalizedMessage());
                if(result!=null){
                    result.error(volleyError);
                    if(dialog!=null){
                        dialog.cancel();
                    }
                }
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );


        requestQueue.add(newMissRequest);
    }


    //显示进度dialog
    public void showDialog(String title) {
        if (dialog != null) {
            dialog.show();
        } else {
            LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(BaseActvity.this)
                    .setMessage(title)
                    .setCancelable(true)
                    .setCancelOutside(true);
            dialog=loadBuilder.create();
            dialog.show();
        }
    }
    //关闭进度dialog
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
