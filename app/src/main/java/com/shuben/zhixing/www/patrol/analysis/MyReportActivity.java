package com.shuben.zhixing.www.patrol.analysis;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.AnanlysisAdapter;
import com.shuben.zhixing.www.patrol.bean.SceneInfo;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyReportActivity extends BaseActvity {
    private TextView tx_date,tx_back;
    private List<SceneInfo> list01=new ArrayList<SceneInfo>();
    private List<SceneInfo> list02=new ArrayList<SceneInfo>();
    private ListView listview01,listview02;
    private String myDate="";

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_report;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        initView();
        initData();
    }

    private void initData() {
        String star= DateUtil.getInstance().getDateOfMonthStart();
        String end=DateUtil.getInstance().getDateOfToDay();
        String createTime = "";
        try {
            createTime = URLEncoder.encode("到", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        myDate=star+createTime+end;
        loadData01(myDate);//问题关闭统计
        loadData02(myDate);//巡线统计



    }

    private void loadData01(String date) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetPatrolStatistics");
            params.put("TenantId", SharedPreferencesTool.getMStool(this).getTenantId());
            params.put("PatrolUserId", SharedPreferencesTool.getMStool(this).getUserId());
            params.put("PatrolDate", date);
            params.put("Type", "PatrolClass");

            JSONObject mJoson=new JSONObject();
            try {
                mJoson.put("AppCode", "LinePatrol");
                mJoson.put("ApiCode", "GetPatrolStatistics");
                mJoson.put("TenantId", SharedPreferencesTool.getMStool(this).getTenantId());
                mJoson.put("PatrolUserId", SharedPreferencesTool.getMStool(this).getUserId());
                mJoson.put("PatrolDate", date);
                mJoson.put("Type", "PatrolClass");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("*****个人巡线统计******",mJoson.toString());

            JsonObjectRequest newMissRequest = new JsonObjectRequest(

                    Request.Method.POST, SharedPreferencesTool.getMStool(MyReportActivity.this).getIp()+ UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject jobject) {

                    try {
                        JSONArray jArray=jobject.getJSONArray("rows");

                        JSONObject jData;
                        list01.clear();
                        for (int i=0;i<jArray.length();i++){
                            jData=jArray.getJSONObject(i);
                            String ClassName=jData.getString("ClassName").substring(0,2);
                            String Total=jData.getString("Total");
                            String  Closed=jData.getString("Closed");
                            String rate="";
                            if(jData.getInt("Closed")==0||jData.getInt("Total")==0){
                                rate="0%";
                            }else{
                                float rr=(float)jData.getInt("Closed")/jData.getInt("Total");
                                DecimalFormat format = new DecimalFormat("0.00%");
                                rate=format.format(rr);
                            }

                            list01.add(new SceneInfo(ClassName,Total,Closed,rate));
                        }

                        AnanlysisAdapter adapter01=new AnanlysisAdapter(MyReportActivity.this,list01);
                        listview01.setAdapter(adapter01);

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

    }
    private void loadData02(String date) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetProblemStatistics");
            params.put("TenantId", SharedPreferencesTool.getMStool(this).getTenantId());
            params.put("LiableUserId", SharedPreferencesTool.getMStool(this).getUserId());
            params.put("PatrolDate", date);
            params.put("Type", "PatrolClass");


            JsonObjectRequest newMissRequest = new JsonObjectRequest(

                    Request.Method.POST, SharedPreferencesTool.getMStool(MyReportActivity.this).getIp()+UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject jobject) {

                    try {
                        JSONArray jArray=jobject.getJSONArray("rows");

                        JSONObject jData;
                        list02.clear();
                        for (int i=0;i<jArray.length();i++){
                            jData=jArray.getJSONObject(i);
                            String ClassName=jData.getString("ClassName").substring(0,2);
                            String Total=jData.getString("Total");
                            String  Closed=jData.getString("Closed");
                            String rate="";
                            if(jData.getInt("Closed")==0||jData.getInt("Total")==0){
                                rate="0%";
                            }else{
                                float rr=(float)jData.getInt("Closed")/jData.getInt("Total");
                                DecimalFormat format = new DecimalFormat("0.00%");
                                rate=format.format(rr);
                            }

                            list02.add(new SceneInfo(ClassName,Total,Closed,rate));
                        }

                        AnanlysisAdapter adapter02=new AnanlysisAdapter(MyReportActivity.this,list02);
                        listview02.setAdapter(adapter02);

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

    }
    private void initView() {
        tx_back=(TextView)findViewById(R.id.tx_back);
        tx_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tx_date=(TextView)findViewById(R.id.tx_date);
        tx_date.setText(getIntent().getStringExtra("tx_date"));

        listview01=(ListView)findViewById(R.id.listview_scene01);
        View head01=getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView t01=(TextView)head01.findViewById(R.id.tx_item01);
        TextView t02=(TextView)head01.findViewById(R.id.tx_item02);
        TextView t03=(TextView)head01.findViewById(R.id.tx_item03);
        TextView t04=(TextView)head01.findViewById(R.id.tx_item04);
        t01.setText("类型");
        t02.setText("计划巡线次数");
        t03.setText("实际巡线次数");
        t04.setText("完成率");
        listview01.addHeaderView(head01);


        listview02=(ListView)findViewById(R.id.listview_scene02);
        View head02=getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView tx01=(TextView)head01.findViewById(R.id.tx_item01);
        TextView tx02=(TextView)head01.findViewById(R.id.tx_item02);
        TextView tx03=(TextView)head01.findViewById(R.id.tx_item03);
        TextView tx04=(TextView)head01.findViewById(R.id.tx_item04);
        tx01.setText("类型");
        tx02.setText("发现问题次数");
        tx03.setText("关闭问题次数");
        tx04.setText("关闭率");
        listview01.addHeaderView(head01);


    }
}
