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
import com.shuben.zhixing.www.patrol.bean.OneClassInfo;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/6/1.
 */

public class PatrolTaskOnScrollListener implements AbsListView.OnScrollListener {

    //ListView总共显示多少条
    private int totalItemCount;

    //ListView最后的item项
    private int lastItem;

    //用于判断当前是否在加载
    private boolean isLoading;

    //底部加载更多布局
    private View footer;

    //接口回调的实例
    private OnloadDataListener listener;

    //数据
    private List<OneClassInfo> data;
    private Activity activity;
    private  String ClassId;
    private  String WorkShopId;
    private int page=1;
    private int total;
    private int item=5;

    public PatrolTaskOnScrollListener(View footer, Activity activity, String ClassId, String WorkShopId, int total) {
        this.footer = footer;
        this.activity=activity;
        this.ClassId=ClassId;
        this.WorkShopId=WorkShopId;
        this.total=total;
    }
    //设置接口回调的实例
    public void setOnLoadDataListener(OnloadDataListener listener) {
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
                            loadPatrolTask( WorkShopId , ClassId);
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
    private void loadMoreData(List<OneClassInfo> data) {
        isLoading = true;

        this.data = data;
    }
    private void loadPatrolTask(String WorkShopId, final String ClassId) {
        page++;
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetLinePatrolTask");
        params.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
        params.put("PatrolUserId", SharedPreferencesTool.getMStool(activity).getUserId());
        params.put("ClassId", this.ClassId);
        params.put("PatrolDate", DateUtil.getInstance().getDateOfToDay()+"到");
        params.put("WorkShopId", WorkShopId);
        params.put("page", page+"");
        params.put("rows", item+"");
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetLinePatrolTask");
            myData.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
            myData.put("PatrolUserId", SharedPreferencesTool.getMStool(activity).getUserId());
            myData.put("ClassId", this.ClassId);
            myData.put("PatrolDate", DateUtil.getInstance().getDateOfToDay()+"到");
            myData.put("WorkShopId", WorkShopId);
            myData.put("page", page+"");
            myData.put("rows", "5");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*******滑动加载*******页数："+page,myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST,SharedPreferencesTool.getMStool(activity).getIp(),
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    data=new ArrayList<OneClassInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String FormNo=jData.getString("FormNo");//问题编号
                        String WorkShopName=jData.getString("MeetingPlace");//车间
                        String RecordId=jData.getString("RecordId");//
                        String WorkShopId=jData.getString("WorkShopId");//
                        String AreaId=jData.getString("AreaId");//
                        String LineId=jData.getString("LineId");//
                        String PatrolDate=jData.getString("PatrolDate").replaceAll("T"," ");
                        String Countdown=DateUtil.getInstance().getCountdown(PatrolDate);
                        String ParentRecordId="父任务";
                        String SubjectId=jData.getString("SubjectId");


                        if(jData.getString("SubjectId")==null||jData.getString("SubjectId").equals("null")){

                        }else{
                            SubjectId=jData.getString("SubjectId");
                        }

                        if(jData.getString("ParentRecordId")==null||jData.getString("ParentRecordId").equals("null")){

                        }else{
                            ParentRecordId=jData.getString("ParentRecordId");
                        }


                        //PatrolDate=PatrolDate.substring(5,PatrolDate.length());
                        //String FormNo=jData.getString("FormNo");
                        String Status=jData.getString("Status");

                        if(Status.equals("-10")){

                            Status="审核未通过";
                        }else if(Status.equals("-5")){

                            Status="未完成";
                        }else if(Status.equals("0")){

                            Status="未启动";
                        }else if(Status.equals("2")){

                            Status="进行中";
                        }else if(Status.equals("5")){
                            Status="已处理(待审核)";
                        }else if(Status.equals("10")){
                            Status="已审核";
                        }
                        String PatrolUserName=jData.getString("PatrolUserName");
                        String LiableUserName=jData.getString("LiableUserName");
                        String ClassName=jData.getString("ClassName");
                        String SubjectName=jData.getString("SubjectName");

                        String MeetingPlace=jData.getString("MeetingPlace");
                        data.add(new OneClassInfo(FormNo,WorkShopName,RecordId,Countdown,PatrolDate,Status,PatrolUserName,LiableUserName,ClassName,WorkShopId,AreaId,LineId,ClassId,ParentRecordId,SubjectId,SubjectName,MeetingPlace));

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
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );

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
        void onLoadData(List<OneClassInfo> data);
    }
}
