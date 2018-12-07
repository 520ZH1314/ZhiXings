package com.shuben.zhixing.www.inspection.fragment;

import android.app.Activity;
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
import com.base.zhixing.www.BaseFragment;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.ProblemRecordActivity;
import com.shuben.zhixing.www.inspection.adapter.PListAdapter;
import com.shuben.zhixing.www.inspection.bean.InspectionQInfo;
import com.shuben.zhixing.www.listener.InspectionQOnScrollListener;
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
 * Created by Geyan on 2018/7/2.
 */

public class InspectionFragment03 extends BaseFragment implements View.OnClickListener,InspectionQOnScrollListener.OnloadDataListener{
    private View view_layout;
    private Activity context;
    private LinearLayout lay_back;
    private ImageView title_back;
    private TextView tx_back;
    private TextView tx_send;
    private ListView mList;
    private List<InspectionQInfo> data=null;
    //底部加载更多布局
    private View footer;
    private  boolean isAdd=false;
    private LoadingDailog dialog=null;//加载动画
    private PListAdapter adapter;
    private String myDate="";



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_inspection03,container,false);
        context = getActivity();
        initView();
        loadData("", "");
        return view_layout;
    }

    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(context)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        tx_back= (TextView) view_layout.findViewById(R.id.tx_back);
        lay_back= (LinearLayout) view_layout.findViewById(R.id.lay_back);
        title_back= (ImageView) view_layout.findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        tx_send=(TextView) view_layout.findViewById(R.id.tx_send);
        tx_send.setOnClickListener(this);

    }
    private void loadData(final String status, final String DueDate) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetRecordProblem");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("Status", status);//状态
        params.put("DueDate", DueDate);//
        params.put("page", "1");
        params.put("rows", "10");
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "PatrolInspection");
            myData.put("ApiCode", "GetRecordProblem");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            myData.put("Status", status);//状态
            myData.put("DueDate", "");//
            myData.put("page", "1");
            myData.put("rows", "10");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******问题一览*****",myData.toString());


        //Log.e("params", params.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK问题一览KKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    if(data==null){
                        data=new ArrayList<InspectionQInfo>();
                    }else{
                        data.clear();
                    }

                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);

                        String ProblemId=jData.getString("ProblemId");//产线名称
                        String PatrolTaskId = jData.getString("PatrolTaskId");//产线名称
                        String ProblemNo = jData.getString("ProblemNo");//问题单号
                        String ProblemDesc = jData.getString("ProblemDesc");//问题单号

                        String LiableDeptId = jData.getString("LiableDeptId");//工单号
                        String LiableDeptName = jData.getString("LiableDeptName");//巡检人ID
                        String PatrolUserName = jData.getString("PatrolUserName");
                        String LiableUserId = jData.getString("LiableUserId");//巡线人姓名
                        String LiableUserName = jData.getString("LiableUserName");//计划巡检时间


                        String DueDate = jData.getString("DueDate").replaceAll("T"," ");//实际巡检时间
                        String CompleteDate = jData.getString("CompleteDate").replaceAll("T"," ");//结束巡检时间
                        String Status = jData.getString("Status");//巡线状态，－5;//未完成；0;//未开始；5;//进行中；10;//已完成
                        if(Status.equals("0")){
                            Status="未开始";
                        }else if(Status.equals("5")){
                            Status="进行中";
                        }else if(Status.equals("10")){
                            Status="已完成";
                        }else if(Status.equals("-5")){
                            Status="未完成";
                        }
                        String ProductName=jData.getString("ProductName");
                        InspectionQInfo info = new InspectionQInfo(ProblemNo,ProblemId,PatrolTaskId, ProblemDesc, LiableDeptId, LiableDeptName, LiableUserId, LiableUserName,PatrolUserName, DueDate, CompleteDate, Status,ProductName);
                        data.add(info);
                    }
                    adapter=null;
                    showListView(data);
                    //自定义的滚动监听事件
                    InspectionQOnScrollListener onScrollListener = new InspectionQOnScrollListener(footer,context,total,status,myDate);
                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(InspectionFragment03.this);
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
    /**
     * 将数据加载到ListView上
     *
     * @param data
     */
    private void showListView(final List<InspectionQInfo> data) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件
            mList = (ListView) view_layout.findViewById(R.id.m_listview);
            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent();
                    intent.putExtra("ProblemId",data.get(position).getProblemId());
                    intent.putExtra("Status",data.get(position).getStatus());
                    intent.putExtra("LiableUserName",data.get(position).getLiableUserName());
                    intent.setClass(context, ProblemRecordActivity.class);
                    context.startActivity(intent);

                }
            });
            //将底部加载一个加载更多的布局
            footer = LayoutInflater.from(context).inflate(R.layout.foot_boot, null);
            //初始状态为隐藏
            footer.setVisibility(View.GONE);
            //加入到ListView的底部
            if(!isAdd){
                isAdd=true;
                mList.addFooterView(footer);
            }

            //创建adpter数据
            adapter = new PListAdapter(context,data,"");
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
    public void onLoadData(List<InspectionQInfo> data) {
        showListView(data);

    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                getActivity().finish();
                break;
            case R.id.tx_back:
                getActivity().finish();
                break;
            case R.id.title_back:
                getActivity().finish();
                break;
            case R.id.tx_send:
               Intent intent=new Intent();
                intent.setClass(context, AnalysisActivity.class);
                context.startActivity(intent);
                break;
            default:

                break;



        }
    }


    @Override
    public void process(Message msg) {

    }
}
