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
import com.shuben.zhixing.www.patrol.bean.FindInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/6/4.
 */

public class GardenOnScrollListener implements AbsListView.OnScrollListener {

    //ListView总共显示多少条
    private int totalItemCount;

    //ListView最后的item项
    private int lastItem;

    //用于判断当前是否在加载
    private boolean isLoading;

    //底部加载更多布局
    private View footer;

    //接口回调的实例
    private GardenOnScrollListener.GardenOnloadDataListener listener;

    //数据
    private List<FindInfo> data;
    private Activity activity;
    private int page=1;
    private int total;
    private int item=5;
    private String mStatus,WorkShopId,ClassId,myDate;
    private String type;

    public GardenOnScrollListener(View footer, Activity activity, int total,  String WorkShopId,  String ClassId,String myDate,String type) {
        this.footer = footer;
        this.activity=activity;
        this.ClassId=ClassId;
        this.WorkShopId=WorkShopId;
        this.ClassId=ClassId;
        this.myDate=myDate;
        this.total=total;
        this.type=type;
    }
    //设置接口回调的实例
    public void setOnLoadDataListener(GardenOnScrollListener.GardenOnloadDataListener listener) {
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
                            loadRecord();
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
    private void loadMoreData(List<FindInfo> data) {
        isLoading = true;

        this.data = data;
    }
    private void loadRecord() {
        page++;
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetRecordHighlight");
        params.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
        JSONObject dat=new JSONObject();


        try {
            dat.put("AppCode", "LinePatrol");
            dat.put("ApiCode", "GetRecordHighlight");
            dat.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****一级巡线发现亮点****",dat.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(activity).getIp(),
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    JSONObject jFile;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);

                        String HighlightId=jData.getString("HighlightId");//问题描述
                        String ProblemDesc=jData.getString("HighlightDesc");//问题描述
                        String findUser=SharedPreferencesTool.getMStool(activity).getUserName();
                        //String WorkShopName=SharedPreferencesTool.getMStool(OneFindActivity.this).getUserName();//发现人
                        String LiableDeptName=jData.getString("ImproveDeptName");//改善部门
                        int Score=jData.getInt("Score");//严重程度
                        String ImproveUserName=jData.getString("ImproveUserName");//改善人
                        String DueDate=jData.getString("CreateTime").replaceAll("T"," ");//发现时间
                        int Thumb=jData.getInt("Thumb");
                        JSONArray FileList=jData.getJSONArray("FileList");
                        String FilePath="";
                        for(int j=0;j<FileList.length();j++){
                            jFile=FileList.getJSONObject(j);

                            if (!FilePath.equals("")){
                                FilePath += "#";
                            }
                            FilePath += jFile.getString("FilePath"); ;

                        }

                        FindInfo f=new FindInfo(ProblemDesc,findUser,LiableDeptName,Score,ImproveUserName,DueDate,HighlightId,FilePath,Thumb,type);
                        data.add(f);
                    }

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
    public interface GardenOnloadDataListener {
        void GardenOnload(List<FindInfo> data);
    }
}


