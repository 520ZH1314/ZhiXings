package com.base.zhixing.www;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;

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
import com.githang.statusbar.StatusBarCompat;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/28/028.
 */

public abstract class BaseFragment extends Fragment {
    private LoadingDailog dialog;//加载动画
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());
        base_handler = new BaseFragment.Base_Handler(BaseFragment.this);
        initDialog();
    }
    private RequestQueue requestQueue ;
    public void initDialog(){
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(getActivity())
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
    }
    public Handler getHandler(){
        return  base_handler;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void setStatus(int color){
        if(color!=-1){
            StatusBarCompat.setStatusBarColor( getActivity(),ContextCompat.getColor( getActivity(),color), true);
        }else{
            StatusBarCompat.setStatusBarColor( getActivity(), ContextCompat.getColor( getActivity(),R.color.title_bg), true);
        }
    }
    private BaseFragment.Base_Handler base_handler;
    public abstract  void process(Message msg);
    private class Base_Handler extends Handler {
        WeakReference<BaseFragment> mLeakActivityRef;
        public Base_Handler(BaseFragment leakActivity) {
            mLeakActivityRef = new WeakReference<BaseFragment>(leakActivity);
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
                P.c("请求出现错误");
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


    public void debugList(final AbsListView listView, final SwipeRefreshLayout refreshLayout){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if(listView != null && listView.getChildCount() > 0){
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                refreshLayout.setEnabled(enable);
            }});
    }


    //显示进度dialog
    public void showDialog(String title) {
        if (dialog != null) {
            dialog.show();
        } else {
            LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(getActivity())
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


}
