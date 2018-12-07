package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.adapter.NewMissionAdapter;
import com.shuben.zhixing.www.data.NotificationData;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 * 新任务
 */

public class NewMissionActivity extends BaseActvity implements View.OnClickListener {
    private static String TAG="NewMissionActivity";
    private ListView mission_list;
    private NewMissionAdapter adapter;
    private ImageView tetle_back;
    private TextView tetle_text;
    private List<NotificationData> data_list=new ArrayList<NotificationData>();
    private NotificationData n_data;
    private RequestQueue mQueue;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mission;
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
        mQueue = Volley.newRequestQueue(this);
        tetle_back = (ImageView) findViewById(R.id.tetle_back);
        tetle_text = (TextView) findViewById(R.id.tetle_text);
        tetle_text.setText("新任务");


        setOnclick();
    }

    //调用接口方法，获取列表数据
    private void getListDate() {

            // TODO Auto-generated method stub
            String Url = UrlUtil.GetNewTaskListUrl(UrlUtil.IP, UrlUtil.GetNewTaskList, 1, 20, SharedPreferencesTool.getMStool(NewMissionActivity.this).getUserId());
            Log.e("获取新通知列表", Url);
            StringRequest stringRequest = new StringRequest(Url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            response = XmlUtil.getDataByXml(response, "string", TAG);
                            Log.e("TAG", response);
                            data_list.clear();
                            try {
                                JSONObject jsonData = new JSONObject(response);
                                JSONObject jsonObject;
                                JSONArray array = jsonData.getJSONArray("rows");
                                for (int i = 0; i < array.length(); i++) {
                                    jsonObject = array.getJSONObject(i);
                                    String ToDoListId = jsonObject.getString("ToDoListId");//任务ID
                                    String Title = jsonObject.getString("Title");//任务内容
                                    String TaskNo = jsonObject.getString("TaskNo");//任务编号
                                    String CreateDate = jsonObject.getString("CreateDate");//创建时间
                                    String CreateDept = jsonObject.getString("CreateDept");//发起部门
                                    String CreateUser = jsonObject.getString("CreateUser");//发起人
                                    int Source = jsonObject.getInt("Source");//任务来源
                                    String IsRead = jsonObject.getString("IsRead");//是否已读
                                    n_data = new NotificationData(ToDoListId, Title, TaskNo, CreateDate, CreateDept, CreateUser, Source, IsRead);
                                    data_list.add(n_data);
                                }
                                setDate(data_list);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            });
            mQueue.add(stringRequest);



    }

    private void setDate(List<NotificationData> data) {
        //假数据来源
        Log.e("",""+data_list.toString());
        /*if(data.size()<=0){
            for (int i =0;i<6;i++){
                NotificationData nd = new NotificationData();
                nd.setTitle("我的任务"+i);
                nd.setTaskNo("2017822"+i);
                nd.setCreateDept("产品部");
                nd.setSource(1);
                nd.setCreateUser("李聪老师");
                nd.setCreateDate("2017-8-22");
                data.add(nd);
            }
        }
*/

        mission_list = (ListView) findViewById(R.id.mission_list);
        adapter = new NewMissionAdapter(NewMissionActivity.this, data_list);
        mission_list.setAdapter(adapter);
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
