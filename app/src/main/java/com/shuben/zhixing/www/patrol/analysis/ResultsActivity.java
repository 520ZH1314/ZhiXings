package com.shuben.zhixing.www.patrol.analysis;

import android.os.Bundle;
import android.os.Message;
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
import com.shuben.zhixing.www.patrol.adapter.SceneAdapter;
import com.shuben.zhixing.www.patrol.bean.SceneInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsActivity extends BaseActvity {
    private TextView tx_date,tx_back;
    private List<SceneInfo> list=new ArrayList<SceneInfo>();
    private ListView listview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_results;
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetWorkShopPatrolScore");
        params.put("TenantId", SharedPreferencesTool.getMStool(ResultsActivity.this).getTenantId());
        params.put("PatrolDate", getIntent().getStringExtra("myDate"));


        JSONObject mData=new JSONObject();
        try {
            mData.put("AppCode", "LinePatrol");
            mData.put("ApiCode", "GetWorkShopPatrolScore");
            mData.put("TenantId", SharedPreferencesTool.getMStool(ResultsActivity.this).getTenantId());
            mData.put("PatrolDate", getIntent().getStringExtra("myDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******管理分析巡线结果******",mData.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(ResultsActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    Log.e("******",jArray.toString());
                    JSONObject jData;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String WorkShopName=jData.getString("WorkShopName");//车间
                        String LeaderName=jData.getString("LeaderName");//责任人
                        String Scor=jData.getString("Score");
                        list.add(new SceneInfo(i+"",WorkShopName,LeaderName,Scor));
                    }
                    SceneAdapter adapter=new SceneAdapter(ResultsActivity.this,list);
                    listview.setAdapter(adapter);
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
        listview=(ListView)findViewById(R.id.listview_scene);
        View head01=getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView tx01=(TextView)head01.findViewById(R.id.tx_item01);
        TextView tx02=(TextView)head01.findViewById(R.id.tx_item02);
        TextView tx03=(TextView)head01.findViewById(R.id.tx_item03);
        TextView tx04=(TextView)head01.findViewById(R.id.tx_item04);
        tx01.setText("排名");
        tx02.setText("区域");
        tx03.setText("责任人");
        tx04.setText("评分");
        listview.addHeaderView(head01);


    }
}
