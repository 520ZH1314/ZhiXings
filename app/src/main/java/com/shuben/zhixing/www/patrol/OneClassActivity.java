package com.shuben.zhixing.www.patrol;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.shuben.zhixing.www.patrol.adapter.OneClassAdapter;
import com.shuben.zhixing.www.patrol.bean.OneClassInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneClassActivity extends BaseActvity implements View.OnClickListener{
    private String TAG="OneClassActivity";
    private TextView tx_back;
    private List<String>  list;
    private List<String>  listId;
    private OneClassInfo oneClassInfo;
    private List<OneClassInfo> data;
    private  NiceSpinner niceSpinner;

    @Override
    public int getLayoutId() {
        return R.layout.activity_one_class;
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
        initListner();
        loadData();
    }

    private void loadData() {
        //加载车间
        loadWorkShop();
        //加载巡线任务



    }

    private void loadPatrolTask(String WorkShopId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetLinePatrolTask");
        params.put("TenantId", SharedPreferencesTool.getMStool(OneClassActivity.this).getTenantId());
        params.put("PatrolUserId", SharedPreferencesTool.getMStool(OneClassActivity.this).getUserId());
        params.put("ClassId", getIntent().getStringExtra("ClassId"));
        params.put("PatrolDate", "2018-03-28");
        params.put("WorkShopId", WorkShopId);


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(OneClassActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                 Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list=new ArrayList<String>();
                    data=new ArrayList<OneClassInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String FormNo=jData.getString("FormNo");//问题编号
                        String WorkShopName=jData.getString("WorkShopName");//车间
                        String AreaName=jData.getString("AreaName");//区域
                        String PatrolDate=jData.getString("PatrolDate");
                        PatrolDate=PatrolDate.substring(8,PatrolDate.length());
                        //String FormNo=jData.getString("FormNo");
                        String Status=jData.getString("Status");

                        if(Status.equals("0")){

                            Status="未完成";
                        }else if(Status.equals("1")){

                            Status="已完成";
                        }else if(Status.equals("2")){
                            Status="已过期";
                        }
                        String PatrolUserName=jData.getString("PatrolUserName");
                        String LiableUserName=jData.getString("LiableUserName");





                        data.add(new OneClassInfo(FormNo,WorkShopName,AreaName,PatrolDate,PatrolDate,Status,PatrolUserName,LiableUserName));
                    }
                    ListView listView= (ListView) findViewById(R.id.listView_oneClass);
                    OneClassAdapter adapter=new OneClassAdapter(OneClassActivity.this,data);
                    listView.setAdapter(adapter);
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

    private void loadWorkShop() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetWorkShopList");
        params.put("TenantId", SharedPreferencesTool.getMStool(OneClassActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(OneClassActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                // Log.e("KKKKKKKK", " " + jsonobj.to);
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list=new ArrayList<String>();
                    listId=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        list.add(jData.getString("WorkShopName"));
                        listId.add(jData.getString("WorkShopId"));

                    }
                    niceSpinner.attachDataSource(list);
                    loadPatrolTask(listId.get(0));

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


    private void initListner() {
        tx_back.setOnClickListener(this);
    }

    private void initView() {
        tx_back= (TextView) findViewById(R.id.tx_back);
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        niceSpinner.setTextColor(Color.rgb(22,155,213));
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadPatrolTask(listId.get(position));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_back:
                Intent intent=new Intent();
                intent.setClass(OneClassActivity.this, PatrolActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
