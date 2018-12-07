package com.shuben.zhixing.www.reminder.r_fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.adapter.FragmentTwoAdapter;
import com.shuben.zhixing.www.data.MyTASkDate;
import com.shuben.zhixing.www.reminder.RenWuActivity;
import com.shuben.zhixing.www.util.DataChangeUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryFragment extends Fragment implements View.OnClickListener {
    private static String TAG="QueryFragment";
    private View view_layout;
    private Context context;
    private ImageView tetle_back;
    private TextView tetle_text;
    private TextView fragment02_faqi,fragment_zeren,fragment_canyu;
    private View view01,view02,view03;
    private RequestQueue mQueue;
    private List<MyTASkDate> data=new ArrayList<MyTASkDate>() ;
    private FragmentTwoAdapter adapter;
    private ListView mytask_fm_list;
    private int type=-1;
    public QueryFragment(int type){
        this.type=type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view_layout = inflater.inflate(R.layout.fragment_mytask,container,false);
        context = getActivity();

        init();
        return view_layout;
    }

    private void init() {
        mQueue = Volley.newRequestQueue(context);
        tetle_back = (ImageView)view_layout.findViewById(R.id.tetle_back);

        tetle_text = (TextView) view_layout.findViewById(R.id.tetle_text);
        tetle_text.setText("历史查询");
        fragment02_faqi = (TextView) view_layout.findViewById(R.id.fragment02_faqi);//发起
        fragment_zeren = (TextView) view_layout.findViewById(R.id.fragment_zeren);//责任
        fragment_canyu = (TextView) view_layout.findViewById(R.id.fragment_canyu);//参与

        mytask_fm_list=(ListView)view_layout.findViewById(R.id.mytask_fm_list);

        mytask_fm_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


        view01 = (View) view_layout.findViewById(R.id.view01);//
        view02 = (View) view_layout.findViewById(R.id.view02);//
        view03 = (View) view_layout.findViewById(R.id.view03);//
        setOnclick();
        getRenwuData(1);
    }

    private void setOnclick() {
        tetle_back.setOnClickListener(this);
        fragment02_faqi.setOnClickListener(this);
        fragment_zeren.setOnClickListener(this);
        fragment_canyu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tetle_back://紧急催单
                getActivity().finish();
                break;
            case R.id.fragment02_faqi:

                //点击不通的状态，传递不通的参数，来调用服务器接口
                getRenwuData(1);
                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#64a553"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#808080"));

                view01.setVisibility(View.VISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.INVISIBLE);

                break;

            case R.id.fragment_zeren:
                getRenwuData(2);

                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#64a553"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#808080"));

                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.VISIBLE);
                view03.setVisibility(View.INVISIBLE);
                break;

            case R.id.fragment_canyu:
                getRenwuData(3);


                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#64a553"));


                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.VISIBLE);
                break;

        }
    }

    //默认调用发起接口
    private void getRenwuData(final int type ) {
        data.clear();
        //接口
        String Url="";
        switch (type) {
            case 1:
                if(type==1){

                }else if(type==2){

                }
                Url = UrlUtil.GetMyApplyListUrl(UrlUtil.IP, UrlUtil.GetMyApplyList, 1, 20, SharedPreferencesTool.getMStool(getActivity()).getUserId());
                Log.e("获取任务发起列表", Url);
                break;
            case 2:
                if(type==1){

                }else if(type==2){

                }
                Url = UrlUtil.GetMyToDoListUrl(UrlUtil.IP, UrlUtil.GetMyToDoList, 1, 20, SharedPreferencesTool.getMStool(getActivity()).getUserId());
                Log.e("获取任务责任列表", Url);

                break;
            case 3:
                if(type==1){

                }else if(type==2){

                }
                Url = UrlUtil.GetMyPartInListUrl(UrlUtil.IP, UrlUtil.GetMyPartInList, 1, 20, SharedPreferencesTool.getMStool(getActivity()).getUserId());
                Log.e("获取任务参与列表", Url);
                break;


            default:
                break;

        }
        {
            {

                StringRequest stringRequest = new StringRequest(Url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                response = XmlUtil.getDataByXml(response, "string", TAG);
                                Log.e("TAG", response);
                                data.clear();
                                try {
                                    JSONObject jsonData = new JSONObject(response);
                                    JSONObject jsonObject;
                                    JSONArray array = jsonData.getJSONArray("rows");
                                    for (int i = 0; i < array.length(); i++) {

                                        jsonObject = array.getJSONObject(i);

                                        String ToDoListId = jsonObject.getString("ToDoListId");//任务ID
                                        String CreateUserId = jsonObject.getString("CreateUserId");//发起人ID
                                        String ToDoUserId = jsonObject.getString("ToDoUserId");//发起人ID
                                        String TaskNo = jsonObject.getString("TaskNo");//任务编号
                                        String Title = jsonObject.getString("Title");//任务内容

                                        String CreateDate = jsonObject.getString("CreateDate");//创建时间
                                        if(!CreateDate.equals("")||!CreateDate.equals("null")||CreateDate!=null){
                                            CreateDate= CreateDate.replaceAll("T","");
                                        }

                                        String CreateDept="";
                                        String User="";
                                        if(type==1||type==4){
                                            CreateDept = jsonObject.getString("ToDoDept");//发起部门
                                            User = jsonObject.getString("ToDoUser");//发起人

                                        }else{
                                            CreateDept = jsonObject.getString("CreateDept");//发起部门
                                            User = jsonObject.getString("CreateUser");//发起人
                                        }



                                        String Source = DataChangeUtil.getInstance().getSource(jsonObject.getInt("Source"));//任务来源
                                        int IsRead = jsonObject.getInt("IsRead");//是否已读
                                        String TaskStatusName = jsonObject.getString("TaskStatusName");//任务状态

                                        MyTASkDate myTASkDate=new MyTASkDate(ToDoListId,CreateUserId,ToDoUserId,TaskNo,Title,CreateDate,CreateDept,User,Source,IsRead,TaskStatusName);
                                        data.add(myTASkDate);

                                    }


                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                getDate(data);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });
                mQueue.add(stringRequest);


            }
        }
    }

    private void getDate( List<MyTASkDate> datas) {
        //假数据来源，有借口了在此写接口
        if(datas.size()<=0){
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
        }
        adapter = new FragmentTwoAdapter(getActivity(),datas);
        mytask_fm_list.setAdapter(adapter);
    }





}
