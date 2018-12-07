package com.shuben.zhixing.www.patrol.twoclass;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.SortAdapter;
import com.shuben.zhixing.www.patrol.bean.SortInfo;
import com.shuben.zhixing.www.util.JsonArrayRequest;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.BarChartView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/4/25.
 */

public class FragmentNotice02 extends Fragment {
    private View view_layout;
    private Activity context;
    private LoadingDailog dialog;//加载动画
    private List<SortInfo> data;
    private SortAdapter adapter;
    private ListView mList;
    private BarChartView view;
    private LinearLayout layoutViewContent,layn;
    private List<String> options = new ArrayList<String>();
    private String aClass;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_notice02,container,false);
        context = getActivity();
        initView();

        return view_layout;
    }
    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(context)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        mList=(ListView) view_layout.findViewById(R.id.listview_sort);
        layoutViewContent = (LinearLayout) view_layout.findViewById(R.id.barview_content);
        layoutViewContent.removeAllViews();
        layn=(LinearLayout) view_layout.findViewById(R.id.barview_content);
        /*if(aClass.equals("three")){
            layn.setVisibility(View.GONE);
            initData02();
        }else{
            initData01();
            initData02();
        }*/


    }

    private void initData01() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetChartByProblemClass");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetChartByProblemClass");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            myData.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*******巡线问题分类*******",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jData) {
                 Log.e("KKKK巡线问题分类KKKK", " " + jData.toString());
                dialog.dismiss();
                List<Double> list=new ArrayList<Double>();
                try {

                    int Manpower = jData.getInt("Manpower");//问题编号
                    int Machine = jData.getInt("Machine");//车间
                    int Material = jData.getInt("Material");//
                    int Method = jData.getInt("Method");//
                    int Environments = jData.getInt("Environments");//
                    options.add("0");
                    options.add("人员");
                    options.add("设备");
                    options.add("物料");
                    options.add("方法");
                    options.add("环境");
                    double [] data={Manpower,Machine,Material,Method,Environments};

                    //double [] data={2,5,7,8,4};

                    view = new BarChartView(context, true);
                    view.initData(data, data, options,"");
                    layoutViewContent.addView(view.getBarChartView((options.size()+1),Math.ceil(10),layoutViewContent.getWidth()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );

        dialog.show();
        requestQueue.add(newMissRequest);
    }

    private void initData02() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetPatrolRanking");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetPatrolRanking");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            myData.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*******巡线结果*******",myData.toString());
        JsonArrayRequest newMissRequest = new JsonArrayRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jArray) {
                Log.e("KKKK巡线结果KKKK", " " + jArray.toString());

                dialog.dismiss();
                try {

                    JSONObject jData;
                    data=new ArrayList<SortInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String UserName=jData.getString("UserName");//问题编号
                        String WorkShopName=jData.getString("WorkShopName");//车间
                        String TotalScore=jData.getString("TotalScore");//
                        String ProblemCount=jData.getString("ProblemCount");//
                        String HighLightCount=jData.getString("HighLightCount");//
                        SortInfo sortInfo=new SortInfo(UserName,WorkShopName,TotalScore,ProblemCount,HighLightCount);
                        data.add(sortInfo);
                    }

                    adapter=new SortAdapter(context,data);
                    mList.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );

        dialog.show();
        requestQueue.add(newMissRequest);
    }

}
