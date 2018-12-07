package com.shuben.zhixing.www.reminder.r_fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.shuben.zhixing.www.adapter.MyTaskFragmentAdapter;
import com.shuben.zhixing.www.data.FragmentTwoDate;
import com.shuben.zhixing.www.reminder.RenWuActivity;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 * 内不催单任务
 */

public class MyTaskFragment extends Fragment implements View.OnClickListener{
    public static String TAG="MyTaskFragment";
    private View view_layout;
    private Context context;
    private ImageView tetle_back;
    private ListView mytask_fm_list;
    private TextView tetle_text,fragment02_faqi,fragment_zeren,fragment_canyu,fragment_jiancha;
    private View view01,view02,view03;
    private List<FragmentTwoDate> data=new ArrayList<FragmentTwoDate>();
    private MyTaskFragmentAdapter adapter;
    Intent intent;
    private RequestQueue mQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view_layout = inflater.inflate(R.layout.fragment_mytask,container,false);
        context = getActivity();
        init();
        return view_layout;
    }

    //id初始化
    private void init() {
        mQueue = Volley.newRequestQueue(context);
        tetle_back = (ImageView)view_layout.findViewById(R.id.tetle_back);

        tetle_text = (TextView) view_layout.findViewById(R.id.tetle_text);
        tetle_text.setText("内部任务");

        fragment02_faqi = (TextView) view_layout.findViewById(R.id.fragment02_faqi);//我的发起
        fragment_zeren = (TextView) view_layout.findViewById(R.id.fragment_zeren);//我的责任
        fragment_canyu = (TextView) view_layout.findViewById(R.id.fragment_canyu);//我的参与

        view01 = (View) view_layout.findViewById(R.id.view01);//蓝色进度条
        view02 = (View) view_layout.findViewById(R.id.view02);//
        view03 = (View) view_layout.findViewById(R.id.view03);//

        mytask_fm_list = (ListView) view_layout.findViewById(R.id.mytask_fm_list);
        getRenwuDate(1);
        mytask_fm_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), RenWuActivity.class);
                intent.putExtra("Type","内部催单");
                intent.putExtra("BillNo",data.get(position).getBillNo());
                startActivity(intent);
            }
        });
        setOnclick();
        getRenwuDate(1);
    }
    private void setOnclick() {
        tetle_back.setOnClickListener(this);
        fragment02_faqi.setOnClickListener(this);
        fragment_zeren.setOnClickListener(this);
        fragment_canyu.setOnClickListener(this);
    }
    /*private List<FragmentTwoDate> getDate() {
       *//* //假数据来源，有借口了在此写接口
        List<FragmentTwoDate> datas = new ArrayList<>();

        for (int i =0;i<6;i++){
            FragmentTwoDate nd = new FragmentTwoDate();
            nd.setMission("我的通知"+1);
            nd.setBianhao("2017822"+1);
            nd.setSource("生产xx");
            nd.setRenwu_jindu("进行中");
            nd.setZeren_name("李聪老师");
            datas.add(nd);
        }
        return datas;*//*
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tetle_back://紧急催单

                getActivity().finish();
                break;
            case R.id.fragment02_faqi:

                //点击不通的状态，传递不通的参数，来调用服务器接口
                getRenwuDate(1);
                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#64a553"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#808080"));

                view01.setVisibility(View.VISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.INVISIBLE);

                mytask_fm_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        intent = new Intent(getActivity(), RenWuActivity.class);
                        intent.putExtra("BillNo",data.get(position).getBillNo());
                        intent.putExtra("Type","内部催单");
                        startActivity(intent);
                    }
                });

                break;

            case R.id.fragment_zeren:
                //点击不通的状态，传递不通的参数，来调用服务器接口
               getRenwuDate(2);
                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#64a553"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#808080"));

                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.VISIBLE);
                view03.setVisibility(View.INVISIBLE);




                mytask_fm_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        intent = new Intent(getActivity(), RenWuActivity.class);
                        intent.putExtra("BillNo",data.get(position).getBillNo());
                        intent.putExtra("Type","内部催单");
                        startActivity(intent);
                    }
                });


                break;

            case R.id.fragment_canyu:
                //点击不通的状态，传递不通的参数，来调用服务器接口

                fragment02_faqi.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_zeren.setTextColor(android.graphics.Color.parseColor("#808080"));
                fragment_canyu.setTextColor(android.graphics.Color.parseColor("#64a553"));

                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.VISIBLE);
                getRenwuDate(3);
                mytask_fm_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        intent = new Intent(getActivity(), RenWuActivity.class);
                        intent.putExtra("BillNo",data.get(position).getBillNo());
                        intent.putExtra("Type","内部催单");
                        startActivity(intent);
                    }
                });
                break;
        }
    }
    //默认调用发起接口
    private void getRenwuDate(final int type ) {
        data.clear();
        //接口
        String Url="";
        switch (type) {
            case 1:
                Url = UrlUtil.GetMyApplyList(UrlUtil.IP, UrlUtil.InnerUrgeOrder01, 1, 20, SharedPreferencesTool.getMStool(getActivity()).getUserId());
                Log.e("获取任务发起列表", Url);
                break;
            case 2:
                Url = UrlUtil.GetMyApplyList(UrlUtil.IP, UrlUtil.InnerUrgeOrder02, 1, 20, SharedPreferencesTool.getMStool(getActivity()).getUserId());
                Log.e("获取任务责任列表", Url);

                break;
            case 3:
                Url = UrlUtil.GetMyApplyList(UrlUtil.IP, UrlUtil.InnerUrgeOrder03, 1, 20, SharedPreferencesTool.getMStool(getActivity()).getUserId());
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

                                        String InnerUrgeOrderId = jsonObject.getString("InnerUrgeOrderId");//任务ID
                                        String BillNo = jsonObject.getString("BillNo");//任务编号
                                        String CustomerName = jsonObject.getString("CustomerName");//客户名称
                                        String ProductName = jsonObject.getString("ProductName");//产品名称
                                        String CreateDate = jsonObject.getString("CreateDate");//创建时间
                                        if(!CreateDate.equals("")||!CreateDate.equals("null")||CreateDate!=null){
                                            CreateDate= CreateDate.replaceAll("T","");
                                        }
                                        String TaskStatusName = jsonObject.getString("TaskStatusName");//创建时间
                                        FragmentTwoDate nd = new FragmentTwoDate(InnerUrgeOrderId,BillNo,CustomerName,ProductName,CreateDate,TaskStatusName);
                                        data.add(nd);

                                    }

                                    mytask_fm_list = (ListView) view_layout.findViewById(R.id.mytask_fm_list);
                                    adapter = new MyTaskFragmentAdapter(getActivity(),data,"内部催单");
                                    mytask_fm_list.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

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
        }
    }





}
