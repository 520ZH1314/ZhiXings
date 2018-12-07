package com.shuben.zhixing.www;

import android.content.Context;

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

import org.json.JSONObject;

import java.util.Map;

/**
 * 提供给dialog使用
 */
public class DialogHttp {
    private Context context;
    private RequestQueue requestQueue ;

    public void initDialogHttp(Context context){
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        initDialog();
    }
    private LoadingDailog dialog;//加载动画
    public void initDialog(){
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(context)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
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
                P.c("错误"+volleyError.getMessage());
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
}
