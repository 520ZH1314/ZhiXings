package com.shuben.zhixing.www.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseFragment;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.activity.DataAnalysisTwoActivity;
import com.shuben.zhixing.www.adapter.FragmentTwoAdapter;
import com.shuben.zhixing.www.data.MyTASkDate;
import com.shuben.zhixing.www.reminder.RenWuActivity;
import com.shuben.zhixing.www.util.DataChangeUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/8/21.
 * 智行力我的任务
 */

public class Fragment02 extends BaseFragment implements View.OnClickListener{
    private static String TAG="Fragment02";
    private View view_layout;
    private Context context;
    private ImageView tetle_back;
    private TextView tetle_text,fragment02_faqi,fragment_zeren,fragment_canyu,fragment_jiancha,tetle_tv1;
    private View view01,view02,view03,view04;
    private ListView fragment02_list;
    private List<MyTASkDate> data=null ;
    private FragmentTwoAdapter adapter;
    private RequestQueue mQueue;
    private RelativeLayout title_rl;
    private LoadingDailog dialog=null;//加载动画
    private int page=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment02,container,false);
        context = getActivity();
        init();
        myTask(1);
        return view_layout;
    }

    //默认调用发起接口
    private void myTask(final int type) {
        if(data!=null){
            data.clear();
        }else{
            data=new ArrayList<MyTASkDate>();
        }

        String ApiCode="";
        String UserName="";
        switch (type) {
            case 1:
                ApiCode="GetMyApplyList";
                UserName="CreateUserId";
                break;
            case 2:
                ApiCode="GetMyToDoList";
                UserName="ToDoUserId";
                break;

            case 3:
                ApiCode="GetMyPartInList";
                UserName="ToDoUserId";
                break;
            case 4:
                ApiCode="GetMyPartInList";
                UserName="ToDoUserId";
                break;

            default:
                break;

        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", ApiCode);
        params.put(UserName, SharedPreferencesTool.getMStool(context).getUserId());
        params.put("page", page+"");
        params.put("rows", "5");
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "EPS");
            myData.put("ApiCode", ApiCode);
            myData.put(UserName, SharedPreferencesTool.getMStool(context).getUserId());
            myData.put("page", page+"");
            myData.put("rows", "5");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****智行力我的任务****",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST,SharedPreferencesTool.getMStool(getActivity()).getIp(),
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK智行力我的任务KKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    //int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;

                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        Log.e("%%%%","测试1");
                        String ToDoListId = jData.getString("ToDoListId");//任务ID
                        String CreateUserId = jData.getString("CreateUserId");//发起人ID
                        String ToDoUserId = jData.getString("ToDoUserId");//发起人ID
                        String TaskNo = jData.getString("TaskNo");//任务编号
                        String Title = jData.getString("Title");//任务内容

                        String CreateDate = jData.getString("CreateDate");//创建时间
                        if(!CreateDate.equals("")||!CreateDate.equals("null")||CreateDate!=null){
                            CreateDate= CreateDate.replaceAll("T","");
                        }

                        String CreateDept="";
                        String User="";
                        if(type==1||type==4){
                            CreateDept = jData.getString("ToDoDept");//发起部门
                            User = jData.getString("ToDoUser");//发起人

                        }else{
                            CreateDept = jData.getString("CreateDept");//发起部门
                            User = jData.getString("CreateUser");//发起人
                        }



                        String Source = DataChangeUtil.getInstance().getSource(jData.getInt("Source"));//任务来源
                        int IsRead = jData.getInt("IsRead");//是否已读
                        String TaskStatusName = jData.getString("TaskStatusName");//任务状态
                        Log.e("%%%%","测试2");
                        MyTASkDate myTASkDate=new MyTASkDate(ToDoListId,CreateUserId,ToDoUserId,TaskNo,Title,CreateDate,CreateDept,User,Source,IsRead,TaskStatusName);
                        data.add(myTASkDate);
                        Log.e("%%%%","data"+data.size());


                    }
                    getDate(data);
                   /* adapter=null;
                    showListView(data);
                    //自定义的滚动监听事件
                    PatrolTaskOnScrollListener onScrollListener = new PatrolTaskOnScrollListener(footer,PatrolTaskActivity.this,getIntent().getStringExtra("ClassId"),WorkShopId,total);
                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(PatrolTaskActivity.this);
                    //设置ListView的滚动监听事件
                    mList.setOnScrollListener(onScrollListener);*/
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

    //id初始化
    private void init() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(context)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();

        title_rl = (RelativeLayout)view_layout.findViewById(R.id.title_rl);
        title_rl.setVisibility(View.VISIBLE);

        mQueue = Volley.newRequestQueue(context);
        tetle_back = (ImageView)view_layout.findViewById(R.id.tetle_back);
        tetle_back.setVisibility(View.GONE);

        tetle_text = (TextView) view_layout.findViewById(R.id.tetle_text);
        tetle_text.setText("我的任务");

        tetle_tv1 = (TextView) view_layout.findViewById(R.id.tetle_tv_ok);
        tetle_tv1.setText("分析");

        fragment02_faqi = (TextView) view_layout.findViewById(R.id.fragment02_faqi);//发起
        fragment_zeren = (TextView) view_layout.findViewById(R.id.fragment_zeren);//责任
        fragment_canyu = (TextView) view_layout.findViewById(R.id.fragment_canyu);//参与
        fragment_jiancha = (TextView) view_layout.findViewById(R.id.fragment_jiancha);//检查

        view01 = (View) view_layout.findViewById(R.id.view01);//蓝色进度条
        view02 = (View) view_layout.findViewById(R.id.view02);//
        view03 = (View) view_layout.findViewById(R.id.view03);//
        view04 = (View) view_layout.findViewById(R.id.view04);//


       fragment02_list = (ListView)view_layout.findViewById(R.id.fragment02_list);

        fragment02_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RenWuActivity.class);
                intent.putExtra("BillNo",data.get(position).getTaskNo());
                Log.e("Type",data.get(position).getSource());
                if(data.get(position).getSource().equals("采购催单")){
                    switch (position) {
                        case 0:
                            intent.putExtra("Type","采购催单1");
                            break;
                        case 1:
                            intent.putExtra("Type","采购催单2");
                            break;
                        case 2:
                            intent.putExtra("Type","采购催单3");
                            break;
                        case 3:
                            intent.putExtra("Type","采购催单3");
                            break;


                        default:
                            break;
                    }
                }else{
                    intent.putExtra("Type",data.get(position).getSource());
                }


                startActivity(intent);
            }
        });

        setOnclick();
    }

    private void setOnclick() {
        fragment02_faqi.setOnClickListener(this);
        fragment_zeren.setOnClickListener(this);
        fragment_canyu.setOnClickListener(this);
        fragment_jiancha.setOnClickListener(this);
        title_rl.setOnClickListener(this);
    }

    private void getDate( List<MyTASkDate> datas) {
        //假数据来源，有借口了在此写接口
        /*if(datas.size()<=0){
            for (int i =0;i<6;i++){
                MyTASkDate nd = new MyTASkDate();
                nd.setToDoListId("我的任务"+i);
                nd.setTaskNo("2017822"+1);
                nd.setSource("高级会议");
                nd.setTaskStatusName("进行中");
                nd.setUser("李聪老师");
                nd.setCreateDate("2017-8-22");
                datas.add(nd);
            }
        }*/
        adapter = new FragmentTwoAdapter(getActivity(),datas);
        fragment02_list.setAdapter(adapter);
    }




    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment02_faqi:

                //点击不通的状态，传递不通的参数，来调用服务器接口
                myTask(1);
                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#64a553"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_jiancha.setTextColor(android.graphics.Color.parseColor("#808080"));

                view01.setVisibility(View.VISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.INVISIBLE);
                view04.setVisibility(View.INVISIBLE);

                break;

            case R.id.fragment_zeren:
                myTask(2);
                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#64a553"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_jiancha.setTextColor(android.graphics.Color.parseColor("#808080"));

                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.VISIBLE);
                view03.setVisibility(View.INVISIBLE);
                view04.setVisibility(View.INVISIBLE);
                break;

            case R.id.fragment_canyu:
                myTask(3);


                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#64a553"));
                fragment_jiancha.setTextColor(android.graphics.Color.parseColor("#808080"));


                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.VISIBLE);
                view04.setVisibility(View.INVISIBLE);
                break;

            case R.id.fragment_jiancha:
                myTask(4);
                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_jiancha.setTextColor(android.graphics.Color.parseColor("#64a553"));

                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.INVISIBLE);
                view04.setVisibility(View.VISIBLE);
                break;

            case R.id.title_rl:
//                跳转到分析界面
                Intent intent = new Intent(getActivity(), DataAnalysisTwoActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void process(Message msg) {

    }
}
