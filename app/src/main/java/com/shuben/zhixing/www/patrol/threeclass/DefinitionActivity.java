package com.shuben.zhixing.www.patrol.threeclass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.DefineAdapter;
import com.shuben.zhixing.www.patrol.bean.GroupInfo;
import com.shuben.zhixing.www.util.HankkinUtil;
import com.shuben.zhixing.www.util.JsonArrayRequest;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.swipeview.ISwipeMenuCreator;
import com.shuben.zhixing.www.util.swipeview.RefreshLayout;
import com.shuben.zhixing.www.util.swipeview.SwipeMenu;
import com.shuben.zhixing.www.util.swipeview.SwipeMenuItem;
import com.shuben.zhixing.www.util.swipeview.SwipeMenuListView;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefinitionActivity extends BaseActvity implements OnClickListener,SwipeRefreshLayout.OnRefreshListener,RefreshLayout.OnLoadListener{
    private RelativeLayout lay_add;
    private LinearLayout lay_back;
    private Intent intent;
    private SwipeMenuListView add_listview;
    private RefreshLayout refreshLayout;
    private List<GroupInfo> list=new ArrayList<GroupInfo>();
    private DefineAdapter adapter;
    private TextView tx_send;
    private   EditText editText;
    private LoadingDailog dialog;//加载动画
    private NiceSpinner niceSpinner01;
    private List<String>  list01;
    private List<String>  listId01;
    private int index=0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_definition;
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
        params.put("ApiCode", "GetTaskGroup");
        params.put("TenantId", SharedPreferencesTool.getMStool(DefinitionActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("GroupId", "");

        
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetGroup");
            myData.put("TenantId", SharedPreferencesTool.getMStool(DefinitionActivity.this).getTenantId());
            myData.put("RecordId", getIntent().getStringExtra("RecordId"));
            myData.put("GroupId", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("**********",myData.toString());



        JsonArrayRequest newMissRequest = new JsonArrayRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(DefinitionActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray jArray) {
                Log.e("KKKKKKKK", " " + jArray.toString());
                dialog.dismiss();
                try {
                    JSONObject jData;
                    JSONObject userData;
                    list.clear();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String GroupId=jData.getString("GroupId");//
                        /* String RecordId=jData.getString("RecordId");//
                        String WorkShopId=jData.getString("WorkShopId");//
                        String SubjectId=jData.getString("SubjectId");//
                        String CreateTime=jData.getString("CreateTime");//
                        String SubjectName=jData.getString("SubjectName");//*/
                        String WorkShopName=jData.getString("WorkShopName");//
                        String Durations=jData.getString("Durations");//
                        String CreateTime=jData.getString("CreateTime").replaceAll("T"," ");//

                        GroupInfo groupInfo=new GroupInfo(WorkShopName,Durations,CreateTime,GroupId);
                        list.add(groupInfo);
                    }

                    Log.e("SSSSSSSSSSSSSSSSSSS", "list"+list.size());
                    adapter=new DefineAdapter(DefinitionActivity.this,list);
                    add_listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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

    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        lay_add=(RelativeLayout)findViewById(R.id.lay_add);
        lay_add.setOnClickListener(this);

        tx_send=(TextView) findViewById(R.id.tx_send);
        tx_send.setOnClickListener(this);

        refreshLayout = (RefreshLayout) findViewById(R.id.swipeRefreshLayout);
        //设置卷内的颜色
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        refreshLayout.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
        add_listview= (SwipeMenuListView) findViewById(R.id.add_listview);
        refreshLayout.setChildView(add_listview);


        ISwipeMenuCreator creator = new ISwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // 创建滑动选项
                SwipeMenuItem showItem = new SwipeMenuItem(
                        getApplicationContext());
                // 设置选项背景
                showItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // 设置选项宽度
                showItem.setWidth(HankkinUtil.dp2px(90,DefinitionActivity.this));
                // 设置选项标题
                showItem.setTitle("Show");
                // 设置选项标题
                showItem.setTitleSize(18);
                // 设置选项标题颜色
                showItem.setTitleColor(Color.WHITE);
                // 添加选项
                menu.addMenuItem(showItem);

                // 创建删除选项
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(HankkinUtil.dp2px(90, DefinitionActivity.this));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };

        add_listview.setMenuCreator(creator);

        add_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0: //第一个选项
                        HankkinUtil.showToast(DefinitionActivity.this, list.get(position).getItem02());
                        break;
                    case 1: //第二个选项
                        HankkinUtil.showToast(DefinitionActivity.this, "删除"+list.get(position).getItem02());
                        remove(list.get(position).getItem04());
                        break;

                }
            }


        });


    }

    private void remove(String groupId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "DeleteTaskGroup");
        params.put("TenantId", SharedPreferencesTool.getMStool(DefinitionActivity.this).getTenantId());
        params.put("GroupId", groupId);
        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "DeleteTaskGroup");
            data.put("TenantId", SharedPreferencesTool.getMStool(DefinitionActivity.this).getTenantId());
            data.put("GroupId",groupId );

            Log.e("*******删除任务定义********",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(DefinitionActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(DefinitionActivity.this,"删除任务定义成功",Toast.LENGTH_SHORT).show();
                        initData();
                    }else{
                        Toast.makeText(DefinitionActivity.this,message,Toast.LENGTH_LONG).show();
                    }




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

    @Override
    public void onLoad() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                refreshLayout.setLoading(false);
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                initData();
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.lay_add:
                showPopWindow();
                break;
            case R.id.tx_send:
                send();
                break;
            default:
            break;
        }
    }
    private void showPopWindow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DefinitionActivity.this);
        View view = (LinearLayout)DefinitionActivity.this. getLayoutInflater().inflate(R.layout.define_edit, null);
        builder.setView(view);
        builder.setTitle("设置");
        niceSpinner01=(NiceSpinner) view.findViewById(R.id.nice_spinner01);
        niceSpinner01.setTextColor(Color.rgb(22,155,213));
        //niceSpinner01.setTextSize(18);
        niceSpinner01.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index=position;
            }
        });
         editText=(EditText) view.findViewById(R.id.editText);
        loadWorkShop();


        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                dialog.cancel();
                add(listId01.get(index),editText.getText().toString());
            }
        });
        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();

    }

    private void add(String WorkShopId,String Durations) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditTaskGroup");
        params.put("TenantId", SharedPreferencesTool.getMStool(DefinitionActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("GroupId", "");
        params.put("WorkShopId", WorkShopId);
        params.put("Durations", Durations);
        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditGroupTask");
            data.put("TenantId", SharedPreferencesTool.getMStool(DefinitionActivity.this).getTenantId());
            data.put("RecordId", getIntent().getStringExtra("RecordId"));
            data.put("GroupId", "");
            data.put("WorkShopId", WorkShopId);
            data.put("Durations", Durations);
            Log.e("******新增任务定义*********",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(DefinitionActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(DefinitionActivity.this,"任务定义成功",Toast.LENGTH_LONG).show();
                        initData();
                    }else{
                        Toast.makeText(DefinitionActivity.this,message,Toast.LENGTH_SHORT).show();
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
    private void send() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditTaskGroupJob");
        params.put("TenantId", SharedPreferencesTool.getMStool(DefinitionActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("CreateUserCode", SharedPreferencesTool.getMStool(DefinitionActivity.this).getUserCode());



        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditTaskGroupJob");
            data.put("TenantId", SharedPreferencesTool.getMStool(DefinitionActivity.this).getTenantId());
            data.put("RecordId", getIntent().getStringExtra("RecordId"));
            data.put("CreateUserCode", SharedPreferencesTool.getMStool(DefinitionActivity.this).getUserCode());

            Log.e("*******三级巡线新增任务********",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(DefinitionActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(DefinitionActivity.this,"生成定义任务成功",Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(DefinitionActivity.this,message,Toast.LENGTH_SHORT).show();
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
    private void loadWorkShop() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetWorkShopList");
        params.put("TenantId", SharedPreferencesTool.getMStool(DefinitionActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(DefinitionActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                // Log.e("KKKKKKKK", " " + jsonobj.to);
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list01=new ArrayList<String>();
                    listId01=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        list01.add(jData.getString("WorkShopName"));
                        listId01.add(jData.getString("WorkShopId"));
                    }
                    niceSpinner01.attachDataSource(list01);

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
