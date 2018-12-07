package com.shuben.zhixing.www.listener;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.patrol.bean.MyQuseInfo;
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
 * Created by Geyan on 2018/6/3.
 */

public class MyQuestionOnScrollListener implements AbsListView.OnScrollListener {

    //ListView总共显示多少条
    private int totalItemCount;

    //ListView最后的item项
    private int lastItem;

    //用于判断当前是否在加载
    private boolean isLoading;

    //底部加载更多布局
    private View footer;

    //接口回调的实例
    private MyQuestionOnScrollListener.OnloadDataListener listener;

    //数据
    private List<MyQuseInfo> data;
    private Activity activity;
    private int page=1;
    private int total;
    private int item=5;
    private String mStatus,WorkShopId,ClassId,myDate;
    private String SearchType;

    public MyQuestionOnScrollListener(View footer, Activity activity, int total, String mStatus,  String WorkShopId,  String ClassId,String myDate,String SearchType) {
        this.footer = footer;
        this.activity=activity;
        this.ClassId=ClassId;
        this.mStatus=mStatus;
        this.WorkShopId=WorkShopId;
        this.ClassId=ClassId;
        this.myDate=myDate;
        this.total=total;
        this.SearchType=SearchType;
    }
    //设置接口回调的实例
    public void setOnLoadDataListener(MyQuestionOnScrollListener.OnloadDataListener listener) {
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
                        if((page*item)<=total){
                            loadRecord(SearchType);
                        }else{
                            Toast.makeText(activity,"数据已加载完",Toast.LENGTH_SHORT).show();
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
    private void loadMoreData(List<MyQuseInfo> data) {
        isLoading = true;

        this.data = data;
    }
    private void loadRecord(String SearchType) {
        page++;
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetProblemListByStatus");
        params.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
        params.put("LiableUserId", SharedPreferencesTool.getMStool(activity).getUserId());//责任人
        params.put("Status", mStatus);//状态
        params.put("DueDate", myDate);//
        params.put("ClassId", ClassId);//状态
        params.put("WorkShopId", WorkShopId);//状态
        params.put("SearchType", SearchType);//状态
        params.put("page", page+"");
        params.put("rows", "10");
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetProblemListByStatus");
            myData.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
            myData.put("LiableUserId", SharedPreferencesTool.getMStool(activity).getUserId());//责任人
            myData.put("Status", mStatus);//状态
            myData.put("DueDate", myDate);//
            myData.put("ClassId", ClassId);//状态
            myData.put("WorkShopId", WorkShopId);//状态
            myData.put("SearchType", SearchType);//
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
                // Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    data=new ArrayList<MyQuseInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String ProblemId=jData.getString("ProblemId");//问题记录ID
                        String RecordId=jData.getString("RecordId");//巡线任务记录ID
                        String ProblemDesc=jData.getString("ProblemDesc");//问题描述
                        float Severity= (float) jData.getDouble("Severity");//严重程度
                        String LiableDeptId=jData.getString("LiableDeptId");//责任部门ID
                        String LiableDeptName=jData.getString("LiableDeptName");//责任部门名称
                        String LiableUserId=jData.getString("LiableUserId");//责任人ID
                        String LiableUserName=jData.getString("LiableUserName");//责任人姓名
                        String DueDate=jData.getString("DueDate").replaceAll("T"," ");//要求完成时间
                        String CompleteDate=jData.getString("CompleteDate").replaceAll("T"," ");//实际完成时间
                        String Status=jData.getString("Status");//
                        data.add(new MyQuseInfo("P00001",ProblemId,RecordId,ProblemDesc,Severity,LiableDeptId,LiableDeptName,LiableUserId,LiableUserName,DueDate,CompleteDate,Status));
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
        requestQueue.add(newMissRequest);
    }



    /**
     * 监听可见界面的情况
     *
     * @param view             ListView
     * @param firstVisibleItem 第一个可见的 item 的索引
     * @param visibleItemCount 可以显示的 item的条数
     * @param totalItemCount   总共有多少个 item
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //当可见界面的第一个item  +  当前界面多有可见的界面个数就可以得到最后一个item项了
        lastItem = firstVisibleItem + visibleItemCount;
        //总listView的item个数
        this.totalItemCount = totalItemCount;
    }
    //回调接口
    public interface OnloadDataListener {
        void onLoadData(List<MyQuseInfo> data);
    }
}


