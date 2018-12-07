package com.shuben.zhixing.www.patrol;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.shuben.zhixing.www.listener.PatrolTaskOnScrollListener;
import com.shuben.zhixing.www.patrol.adapter.OneClassAdapter;
import com.shuben.zhixing.www.patrol.bean.OneClassInfo;
import com.shuben.zhixing.www.util.DateUtil;
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

public class PatrolTaskActivity extends BaseActvity implements View.OnClickListener, PatrolTaskOnScrollListener.OnloadDataListener{
    private String TAG="PatrolTaskActivity";
    private TextView tx_tittle;
    LinearLayout lay_back;
    //底部加载更多布局
    View footer;
    private List<String> list;
    private List<String>  listId;
    private OneClassInfo oneClassInfo;
    private List<OneClassInfo> data;
    private NiceSpinner niceSpinner;
    private LinearLayout lay_sp;
    private OneClassAdapter adapter;
    private ListView mList;
    private LoadingDailog dialog=null;//加载动画
    private int page=1;
    private  boolean isAdd=false;
    private int index=0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_patrol_task;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        initView();
        initListner();
        loadData("");
    }

    private void loadData(String WorkShopId) {
        //加载车间
    if(getIntent().getStringExtra("ClassName").equals("一级巡线")){
        loadWorkShop();
    }else{
        lay_sp.setVisibility(View.GONE);
        loadPatrolTask(WorkShopId);
    }
    }
    private void loadPatrolTask(final String WorkShopId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetLinePatrolTask");
        params.put("TenantId", SharedPreferencesTool.getMStool(PatrolTaskActivity.this).getTenantId());
        params.put("PatrolUserId", SharedPreferencesTool.getMStool(PatrolTaskActivity.this).getUserId());
        params.put("ClassId", getIntent().getStringExtra("ClassId"));
        params.put("PatrolDate", DateUtil.getInstance().getDateOfToDay()+"到");

        params.put("WorkShopId", WorkShopId);
        params.put("page", page+"");
        params.put("rows", "5");
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetLinePatrolTask");
            myData.put("TenantId", SharedPreferencesTool.getMStool(PatrolTaskActivity.this).getTenantId());
            myData.put("PatrolUserId", SharedPreferencesTool.getMStool(PatrolTaskActivity.this).getUserId());
            myData.put("ClassId", getIntent().getStringExtra("ClassId"));
            myData.put("PatrolDate", DateUtil.getInstance().getDateOfToDay()+"到");
            myData.put("WorkShopId", WorkShopId);
            myData.put("page", page+"");
            myData.put("rows", "5");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("**************",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(PatrolTaskActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK巡线任务KKKK", " " + jsonobj.toString());

                dialog.dismiss();
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");

                    JSONObject jData;
                    data=new ArrayList<OneClassInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String FormNo=jData.getString("FormNo");//问题编号
                        String WorkShopName=jData.getString("WorkShopName");//车间
                        String RecordId=jData.getString("RecordId");//
                        String WorkShopId=jData.getString("WorkShopId");//
                        String AreaId=jData.getString("AreaId");//
                        String LineId=jData.getString("LineId");//
                        String ClassId=getIntent().getStringExtra("ClassId");//
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
                        Log.e("***ParentRecordId***"+i,ParentRecordId);
                        String Status=jData.getString("Status");

                        if(Status.equals("-10")){

                            Status="审核未通过";
                        }else if(Status.equals("-5")){

                            Status="未完成";
                        }else if(Status.equals("0")){

                            Status="未启动";
                        }else if(Status.equals("2")){

                            Status="进行中";
                        }else if(Status.equals("10")){
                            Status="已完成";
                        }
                        String PatrolUserName=jData.getString("PatrolUserName");
                        String LiableUserName=jData.getString("LiableUserName");
                        String ClassName=jData.getString("ClassName");
                        String SubjectName=jData.getString("SubjectName");
                        String MeetingPlace=jData.getString("MeetingPlace");

                        data.add(new OneClassInfo(FormNo,WorkShopName,RecordId,Countdown,PatrolDate,Status,PatrolUserName,LiableUserName,ClassName,WorkShopId,AreaId,LineId,ClassId,ParentRecordId,SubjectId,SubjectName,MeetingPlace));



                       //if(PatrolDate.substring(0,10).equals(DateUtil.getInstance().getDateOfToDay())){
                        //}

                    }
                    adapter=null;
                    showListView(data);
                    //自定义的滚动监听事件
                    PatrolTaskOnScrollListener onScrollListener = new PatrolTaskOnScrollListener(footer,PatrolTaskActivity.this,getIntent().getStringExtra("ClassId"),WorkShopId,total);
                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(PatrolTaskActivity.this);
                    //设置ListView的滚动监听事件
                    mList.setOnScrollListener(onScrollListener);
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
    private void loadWorkShop() {
        Log.e("SSS",SharedPreferencesTool.getMStool(PatrolTaskActivity.this).getIp()+UrlUtil.Url);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetWorkShopList");
        params.put("TenantId", SharedPreferencesTool.getMStool(PatrolTaskActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");
        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "EPS");
            mJson.put("ApiCode", "GetWorkShopList");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(PatrolTaskActivity.this).getTenantId());
            mJson.put("page", "1");
            mJson.put("rows", "20");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****加载车间****",mJson.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(PatrolTaskActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                //Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
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
                dialog.dismiss();

            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        dialog.show();
        requestQueue.add(newMissRequest);



    }
    private void initListner() {
        lay_back.setOnClickListener(this);
    }
    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        lay_sp=(LinearLayout)findViewById(R.id.lay_sp);
        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        tx_tittle= (TextView) findViewById(R.id.tx_tittle);
        tx_tittle.setText(getIntent().getStringExtra("ClassName"));
        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
        niceSpinner.setTextColor(Color.rgb(22,155,213));
        //Toast.makeText(PatrolTaskActivity.this,R.dimen.x12+"",Toast.LENGTH_SHORT).show();

        //niceSpinner.setTextSize(TypedValue.COMPLEX_UNIT_PX,24); //
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index=position;
                loadPatrolTask(listId.get(position));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
           if (requestCode == UrlUtil.PatrolTaskActivity_RequstCode) {
                if (resultCode == UrlUtil.TwoClassActivity_ResultCode) {
                    loadData("");

                }
            }else  if (requestCode == UrlUtil.PatrolTaskActivity_RequstCode) {
                if (resultCode == UrlUtil.ThreeClassActivity_ResultCode) {
                    loadData("");
                }
            }else  if (requestCode == UrlUtil.PatrolTaskActivity_RequstCode) {
               if (resultCode == UrlUtil.OneFindActivity_ResultCode) {
                   loadPatrolTask(listId.get(index));
               }
           }


        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 将数据加载到ListView上
     *
     * @param data
     */
    private void showListView(List<OneClassInfo> data) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件
            mList = (ListView) findViewById(R.id.listView_oneClass);
            //将底部加载一个加载更多的布局
            footer = LayoutInflater.from(this).inflate(R.layout.foot_boot, null);
            //初始状态为隐藏
            footer.setVisibility(View.GONE);
            //加入到ListView的底部
            if(!isAdd){
                isAdd=true;
                mList.addFooterView(footer);
            }

            //创建adpter数据
            adapter = new OneClassAdapter(PatrolTaskActivity.this,data);
            //设置adapter
            mList.setAdapter(adapter);
        } else {
            //不为空，则刷新数据
            this.data.addAll(data);
            //提醒ListView重新更新数据
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onLoadData(List<OneClassInfo> data) {
        showListView(data);
    }
}

