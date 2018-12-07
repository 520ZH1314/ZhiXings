package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.shuben.zhixing.www.adapter.NoticeAdapter;
import com.shuben.zhixing.www.data.NotificationData;
import com.shuben.zhixing.www.util.DateUtil;
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
 * Created by Administrator on 2017/8/22.
 * 新通知
 */

public class NewNotificationActivity extends BaseActvity implements View.OnClickListener{
    private static String TAG="NewNotificationActivity";
    private ListView notification_list;
    private NoticeAdapter adapter;
    private ImageView tetle_back;
    private TextView tetle_text;
    private RequestQueue mQueue;
    private List<NotificationData> data_list=new ArrayList<NotificationData>();
    private NotificationData n_data;
    private LoadingDailog dialog=null;//加载动画
    private int page=1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_notification;
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
        init();
        getListDate();//获取服务器数据方法
    }

    private void init() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();


        tetle_back = (ImageView)findViewById(R.id.tetle_back);
        tetle_text = (TextView)findViewById(R.id.tetle_text);
        tetle_text.setText("新通知");


        setOnclick();
    }
    //调用接口方法，获取列表数据
    private void getListDate() {
        String ApiCode="";
        String UserName="";
        RequestQueue requestQueue = Volley.newRequestQueue(NewNotificationActivity.this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetNoticeList");
        params.put("ReceiveUserId", SharedPreferencesTool.getMStool(NewNotificationActivity.this).getUserId());
        params.put("page", page+"");
        params.put("rows", "5");
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "EPS");
            myData.put("ApiCode", "GetNoticeList");
            myData.put("ReceiveUserId", SharedPreferencesTool.getMStool(NewNotificationActivity.this).getUserId());
            myData.put("page", page+"");
            myData.put("rows", "5");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****新通知****",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(NewNotificationActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK新通知KKKK", " " + jsonobj.toString());

                dialog.dismiss();
                try {
                    JSONObject jsonObject;
                    JSONArray array=jsonobj.getJSONArray("rows");
                    for (int i = 0; i <array.length();i++) {
                        jsonObject=array.getJSONObject(i);
                        String NoticeId=jsonObject.getString("NoticeId");//通知ID
                        String Title=jsonObject.getString("Title");//任务内容
                        String TaskNo=jsonObject.getString("TaskNo");//任务编号
                        String CreateDate=jsonObject.getString("CreateDate");//创建时间
                        if(!CreateDate.equals("")||!CreateDate.equals("null")||CreateDate!=null){
                            CreateDate= CreateDate.replaceAll("T"," ");
                        }
                        String CreateDept=jsonObject.getString("CreateDept");//发起部门
                        String CreateUser=jsonObject.getString("CreateUser");//发起人
                        int Source= jsonObject.getInt("Source");//任务来源
                        String IsRead=jsonObject.getString("IsRead");//是否已读
                        if(IsRead.equals("0")){
                            n_data=new NotificationData(NoticeId,Title,TaskNo,CreateDate,CreateDept,CreateUser,Source,IsRead);
                            data_list.add(n_data);
                        }else if(IsRead.equals("0")&CreateDate.substring(0,10).equals(DateUtil.getInstance().getDateOfToDay())){
                            n_data=new NotificationData(NoticeId,Title,TaskNo,CreateDate,CreateDept,CreateUser,Source,IsRead);
                            data_list.add(n_data);
                        }


                    }
                    setDate(data_list);

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

    private void setDate(List<NotificationData> data) {
        //假数据来源
        Log.e("",""+data.size());
       if(data.size()<=0){
         /*  for (int i =0;i<6;i++){
               NotificationData nd = new NotificationData();
               nd.setTitle("我的通知"+i);
               nd.setTaskNo("2017822"+i);
               nd.setCreateDept("产品部");
               nd.setSource(1);
               nd.setCreateUser("李聪老师");
               nd.setCreateDate("2017-8-22");
               data.add(nd);
           }*/
       }
        notification_list = (ListView)findViewById(R.id.notification_list);
        adapter = new NoticeAdapter(NewNotificationActivity.this,data);
        notification_list.setAdapter(adapter);
    }

    private void setOnclick() {
        tetle_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tetle_back:
                finish();
                break;
        }
    }
}
