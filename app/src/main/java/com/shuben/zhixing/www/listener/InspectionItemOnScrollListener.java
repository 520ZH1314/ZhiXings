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
import com.shuben.zhixing.www.inspection.bean.ItemInfo;
import com.shuben.zhixing.www.inspection.bean.ParamInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Geyan on 2018/7/19.
 */

public class InspectionItemOnScrollListener implements AbsListView.OnScrollListener {

    //ListView总共显示多少条
    private int totalItemCount;

    //ListView最后的item项
    private int lastItem;

    //用于判断当前是否在加载
    private boolean isLoading;

    //底部加载更多布局
    private View footer;

    //接口回调的实例
    private InspectionItemOnScrollListener.OnloadDataListener listener;

    //数据
    private List<ItemInfo> data;
    private Activity activity;
    private String ClassId;
    private String ItemType;
    private int page = 1;
    private int total;
    private int item = 5;
    private List<ParamInfo> plist=null;

    public InspectionItemOnScrollListener(View footer, Activity activity, String ClassId,String ItemType ,int total,List<ParamInfo> plist) {
        this.footer = footer;
        this.activity = activity;
        this.ClassId = ClassId;
        this.ItemType=ItemType;
        this.total = total;
        this.plist=plist;
    }

    //设置接口回调的实例
    public void setOnLoadDataListener(InspectionItemOnScrollListener.OnloadDataListener listener) {
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
                            loadTask(ClassId,ItemType);
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
    private void loadMoreData(List<ItemInfo> data) {
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
        void onLoadData(List<ItemInfo> data);
    }

    private void loadTask(String PatrolTaskId,String ItemType) {
        page++;

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetAllCheckItemByTaskId");
        params.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
        params.put("PatrolTaskId", PatrolTaskId);
        params.put("ItemType", ItemType);

        JSONObject myData = new JSONObject();
        try {
            myData.put("AppCode", "PatrolInspection");
            myData.put("ApiCode", "GetAllCheckItemByTaskId");
            myData.put("TenantId", SharedPreferencesTool.getMStool(activity).getTenantId());
            myData.put("PatrolTaskId", PatrolTaskId);
            myData.put("ItemType", ItemType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******巡检项********", myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(activity).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK巡检项KKKK", " " + jsonobj.toString());

                try {
                    int total = jsonobj.getInt("total");
                    JSONArray jArray = jsonobj.getJSONArray("rows");

                    JSONObject jData;
                    for (int i = 0; i < jArray.length(); i++) {
                        jData=jArray.getJSONObject(i);
                        String PatrolRecord=jData.getString("PatrolRecord");//巡检记录ID
                        String PatrolTaskId=jData.getString("PatrolTaskId");//巡检任务ID
                        String ItemId=jData.getString("ItemId");//巡检项ID
                        String ItemName=jData.getString("ItemName");//巡线项描述
                        String ItemType=jData.getString("ItemType");//类型，人、机、料、法、环
                        String PatrolFashion=jData.getString("PatrolFashion");//巡检方式，取值有;//数量、拍照、说明
                        String Result=jData.getString("Result");//取值有OK/NG/空值
                        String CreateTime=jData.getString("CreateTime").replaceAll("T"," ");
                        String ItemSource=jData.getString("ItemSource");//取值有OK/NG/空值
                        JSONObject PatrolResult=jData.getJSONObject("PatrolResult");
                        JSONArray ss=PatrolResult.getJSONArray("rows");
                        JSONObject jsonObject;

                        try {
                            for(int j=0;j<ss.length();j++){
                                jsonObject=ss.getJSONObject(j);
                                String PatrolResultId=jsonObject.getString("PatrolResultId");
                                String PatrolRecordId=jsonObject.getString("PatrolRecordId");
                                String PatrolTaskId02=jsonObject.getString("PatrolTaskId");
                                String ItemId02=jsonObject.getString("ItemId");
                                String MappingId=jsonObject.getString("MappingId");
                                String PatrolValue=jsonObject.getString("PatrolValue");
                                String ItemSource02=jsonObject.getString("ItemSource");
                                String CategoryCode=jsonObject.getString("CategoryCode");
                                String CategoryName=jsonObject.getString("CategoryName");
                                String StandText=jsonObject.getString("StandText");
                                String StandValue=jsonObject.getString("StandValue");
                                String StandMaxValue=jsonObject.getString("StandMaxValue");
                                String StandMinValue=jsonObject.getString("StandMinValue");
                                String ParamName=jsonObject.getString("ParamName");
                                String ParamUnit=jsonObject.getString("ParamUnit");
                                ParamInfo  paramInfo=new ParamInfo(PatrolResultId,PatrolRecordId,PatrolTaskId02,ItemId02,MappingId,PatrolValue,ItemSource02,CategoryCode,CategoryName,StandText,StandValue,StandMaxValue,StandMinValue,ParamName,ParamUnit);
                                plist.add(paramInfo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        ItemInfo info=new ItemInfo(PatrolRecord,PatrolTaskId,ItemId,ItemName,ItemType,PatrolFashion,Result,CreateTime,ItemSource,plist);
                        data.add(info);
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
        newMissRequest.setRetryPolicy(new DefaultRetryPolicy(50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(newMissRequest);
    }





}

