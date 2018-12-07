package com.shuben.zhixing.www.listener;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.inspection.bean.InspectionQInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/7/30.
 */

public class InspectionQOnScrollListener  implements AbsListView.OnScrollListener {

    //ListView总共显示多少条
    private int totalItemCount;

    //ListView最后的item项
    private int lastItem;

    //用于判断当前是否在加载
    private boolean isLoading;

    //底部加载更多布局
    private View footer;

    //接口回调的实例
    private InspectionQOnScrollListener.OnloadDataListener listener;

    //数据
    private List<InspectionQInfo> data;
    private Activity activity;
    private String status;
    private String myDate;
    private int page = 1;
    private int total;
    private int item = 5;

    public InspectionQOnScrollListener(View footer, Activity activity, int total,String status,String myDate) {
        this.footer = footer;
        this.activity = activity;
        this.status = status;
        this.myDate=myDate;
        this.total = total;
    }

    //设置接口回调的实例
    public void setOnLoadDataListener(InspectionQOnScrollListener.OnloadDataListener listener) {
        this.listener = listener;
    }

    /**
     * 滑动状态变化
     *
     * @param view
     * @param scrollState 1 SCROLL_STATE_TOUCH_SCROLL是拖动   2 SCROLL_STATE_FLING是惯性滑动  0SCROLL_STATE_IDLE是停止 , 只有当在不同状态间切换的时候才会执行
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //如果数据没有加载，并且滑动状态是停止的，而且到达了最后一个item项
        if (!isLoading && lastItem == totalItemCount && scrollState == SCROLL_STATE_IDLE) {
            //显示加载更多
            footer.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            //模拟一个延迟两秒的刷新功能
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        //开始加载更多数据
                        //回调设置ListView的数据
                        //data.addAll(data);
                        if ((page * item) <= total) {
                            loadTask(status,myDate);
                        } else {
                            Toast.makeText(activity, "数据已加载完", Toast.LENGTH_SHORT).show();
                            loadComplete();
                        }


                    }
                }
            }, 2000);
        }
    }


    /**
     * 当加载数据完成后，设置加载标志为false表示没有加载数据了
     * 并且设置底部加载更多为隐藏
     */
    private void loadComplete() {
        isLoading = false;
        footer.setVisibility(View.GONE);

    }

    /**
     * 开始加载更多新数据，这里每次只更新三条数据
     */
    private void loadMoreData(List<InspectionQInfo> data) {
        isLoading = true;

        this.data = data;
    }
    @Override
    public void onScroll (AbsListView view,int firstVisibleItem, int visibleItemCount,
                          int totalItemCount){
        //当可见界面的第一个item  +  当前界面多有可见的界面个数就可以得到最后一个item项了
        lastItem = firstVisibleItem + visibleItemCount;
        //总listView的item个数
        this.totalItemCount = totalItemCount;
    }
    //回调接口
    public interface OnloadDataListener {
        void onLoadData(List<InspectionQInfo> data);
    }

    private void loadTask(final String status,final String DueDate) {
        page++;
        {
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "PatrolInspection");
            params.put("ApiCode", "GetRecordProblem");
            params.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
            params.put("LiableUserId", SharedPreferencesTool.getMStool(activity).getUserId());//责任人
            params.put("Status", status);//状态
            params.put("DueDate", DueDate);//
            params.put("page", "1");
            params.put("rows", "10");
            JSONObject myData=new JSONObject();
            try {
                myData.put("AppCode", "PatrolInspection");
                myData.put("ApiCode", "GetRecordProblem");
                myData.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
                myData.put("LiableUserId", SharedPreferencesTool.getMStool(activity).getUserId());//责任人
                myData.put("Status", "");//状态
                myData.put("DueDate", "");//
                myData.put("page", "1");
                myData.put("rows", "10");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("******我的问题*****",myData.toString());


            //Log.e("params", params.toString());
            JsonObjectRequest newMissRequest = new JsonObjectRequest(
                    Request.Method.POST, SharedPreferencesTool.getMStool(activity).getIp()+ UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonobj) {
                    Log.e("KKKK我的问题KKKK", " " + jsonobj.toString());
                    try {
                        int total = jsonobj.getInt("total");
                        JSONArray jArray = jsonobj.getJSONArray("rows");
                        JSONObject jData;
                        data = new ArrayList<InspectionQInfo>();
                        for (int i = 0; i < jArray.length(); i++) {
                            jData = jArray.getJSONObject(i);

                            String ProblemId = jData.getString("ProblemId");//产线名称
                            String PatrolTaskId = jData.getString("PatrolTaskId");//产线名称
                            String ProblemNo = jData.getString("ProblemNo");//问题单号
                            String ProblemDesc = jData.getString("ProblemDesc");//问题单号
                            String LiableDeptId = jData.getString("LiableDeptId");//工单号
                            String LiableDeptName = jData.getString("LiableDeptName");//巡检人ID
                            String LiableUserId = jData.getString("LiableUserId");//巡线人姓名
                            String LiableUserName = jData.getString("LiableUserName");//计划巡检时间
                            String PatrolUserName = jData.getString("PatrolUserName");//计划巡检时间
                            String DueDate = jData.getString("DueDate").replaceAll("T", " ");//实际巡检时间
                            String CompleteDate = jData.getString("CompleteDate").replaceAll("T", " ");//结束巡检时间
                            String Status = jData.getString("Status");//巡线状态，－5;//未完成；0;//未开始；5;//进行中；10;//已完成
                            String ProductName = jData.getString("ProductName");
                            InspectionQInfo info = new InspectionQInfo(ProblemNo, ProblemId, PatrolTaskId, ProblemDesc, LiableDeptId, LiableDeptName, LiableUserId, LiableUserName, PatrolUserName, DueDate, CompleteDate, Status, ProductName);
                            data.add(info);
                        }
                        listener.onLoadData(data);
                        loadComplete();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            newMissRequest.setRetryPolicy(new DefaultRetryPolicy(50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(newMissRequest);
        }





    }
}

