package com.shuben.zhixing.www.inspection.fragment;

import android.app.Activity;
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
import com.shuben.zhixing.www.inspection.adapter.TaskAdapter;
import com.shuben.zhixing.www.inspection.bean.TaskInfo;
import com.shuben.zhixing.www.inspection.bean.TypeInfo;
import com.shuben.zhixing.www.listener.InspectionTaskOnScrollListener;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.EmptyLayout;
import com.shuben.zhixing.www.view.PopupAdapter;
import com.shuben.zhixing.www.view.PopupButton;

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

public class InspectionFragment01 extends BaseFragment implements View.OnClickListener,InspectionTaskOnScrollListener.OnloadDataListener{
    private View view_layout;
    private Activity context;
    private RelativeLayout lay_back;
    private ImageView title_back;
    private TextView tx_back;
    private ListView mList;
    private LoadingDailog dialog=null;//加载动画
    private List<TypeInfo> data=null;
    private List<TaskInfo> task=null;
    private TaskAdapter adapter;
    private LayoutInflater inflater;
    private PopupButton btn;
    private int index=0;
    private  List<String> arr=new ArrayList<String>();
    private ListView lv;
    private  boolean isAdd=false;
    private int page=1;
    private EmptyLayout emptyLayout;
    //底部加载更多布局
    View footer;
    private View line;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_inspection01,container,false);
        context = getActivity();
        initView();
        initType();
        return view_layout;
    }

    private void initType() {
        if(data==null){
            data=new ArrayList<TypeInfo>();
        }else{
            data.clear();
            arr.clear();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetPatrolInspectionClass");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "PatrolInspection");
            myData.put("ApiCode", "GetPatrolInspectionClass");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******巡检类别********",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());

                dialog.dismiss();
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");

                    JSONObject jData;


                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String ClassId=jData.getString("ClassId");//问题编号
                        String ClassName=jData.getString("ClassName");//车间
                        String Period=jData.getString("Period");//
                        String Frequency=jData.getString("Frequency");//
                        String IsRelationWorksheet=jData.getString("IsRelationWorksheet");//
                        String IsCreateTaskByStart=jData.getString("IsCreateTaskByStart");//
                        data.add(new TypeInfo(ClassId,ClassName,Period,Frequency,IsRelationWorksheet,IsCreateTaskByStart));

                    }
                    for(int j=0;j<data.size();j++){
                       arr.add(j,data.get(j).getClassName());

                    }



                    final PopupAdapter adapter = new PopupAdapter(getActivity(),R.layout.popup_item,arr,R.drawable.normal,R.drawable.press);
                    lv.setAdapter(adapter);
                    if(data.size()>0){
                        adapter.setPressPostion(index);
                        adapter.notifyDataSetChanged();
                        btn.setText(arr.get(index));
                        btn.hidePopup();
                        initData(data.get(index).getClassId(),data.get(index).getIsRelationWorksheet());
                    }
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            adapter.setPressPostion(position);
                            adapter.notifyDataSetChanged();
                            btn.setText(arr.get(position));
                            btn.hidePopup();
                            index=position;
                            initData(data.get(index).getClassId(),data.get(index).getIsRelationWorksheet());
                        }
                    });



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
    private void initData(final String ClassId, final String IsRelationWorksheet) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetPatrolInspectionTask");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("ClassId", ClassId);
        params.put("PatrolTaskId", "");
        params.put("LineId", "");
        params.put("PlanPatrolTime", DateUtil.getInstance().getDateOfToDay()+"到");
        //params.put("PatrolUserId", SharedPreferencesTool.getMStool(context).getUserId());
        params.put("PatrolUserId", SharedPreferencesTool.getMStool(context).getUserId());
        params.put("page", page+"");
        params.put("rows", "15");


        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "PatrolInspection");
            myData.put("ApiCode", "GetPatrolInspectionTask");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            myData.put("ClassId", ClassId);
            myData.put("PatrolTaskId", "");
            myData.put("LineId", "");
            myData.put("PlanPatrolTime", DateUtil.getInstance().getDateOfToDay()+"到");
            myData.put("PatrolUserId", SharedPreferencesTool.getMStool(context).getUserId());
            myData.put("page", page+"");
            myData.put("rows", "15");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******巡检任务********",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK巡检任务KKKK", " " + jsonobj.toString());

                dialog.dismiss();
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");


                    JSONObject jData;
                    if(task==null){
                        task=new ArrayList<TaskInfo>();
                    }else{
                        task.clear();
                    }

                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                         String PatrolTaskId=jData.getString("PatrolTaskId");//产线名称
                         String LineName=jData.getString("LineName");//产线名称
                         String LineId=jData.getString("LineId");//产线名称
                         String FormNo=jData.getString("FormNo");//巡检单号
                         String WorkSheetNo=jData.getString("WorkSheetNo");//工单号
                         String PatrolUserId=jData.getString("PatrolUserId");//巡检人ID
                         String PatrolUserName=jData.getString("PatrolUserName");//巡线人姓名

                        String PlanPatrolTime=jData.getString("PlanPatrolTime").replaceAll("T"," ");//计划巡检时间
                        String RealPatrolDate;
                        if(jData.getString("RealPatrolDate").equals("null")){
                            RealPatrolDate="";
                         }else{
                            RealPatrolDate=jData.getString("RealPatrolDate").replaceAll("T"," ");//实际巡检时间
                            RealPatrolDate=RealPatrolDate.substring(0,RealPatrolDate.length()-3);
                        }
                         String EndPatrolDate;
                        if(jData.getString("EndPatrolDate").equals("null")){
                            EndPatrolDate="";
                        }else{
                            EndPatrolDate=jData.getString("EndPatrolDate").replaceAll("T"," ");//结束巡检时间
                            EndPatrolDate=EndPatrolDate.substring(0,EndPatrolDate.length()-3);

                        }

                         String Status=jData.getString("Status");//巡线状态，－5;//未完成；0;//未开始；5;//进行中；10;//已完成
                         if(Status.equals("-5")){
                             Status="未完成";
                         }else if(Status.equals("0")){
                             Status="未开始";
                         }else if(Status.equals("5")){
                             Status="进行中";
                         }else if(Status.equals("10")){
                             Status="已完成";
                         }
                         String ClassName=jData.getString("ClassName");//巡线类别

                         String ProductName=jData.getString("ProductName");
                         TaskInfo info=new TaskInfo(PatrolTaskId,LineName,LineId,FormNo,WorkSheetNo,PatrolUserId,PatrolUserName,PlanPatrolTime,RealPatrolDate,EndPatrolDate,Status,ClassName,ProductName);
                         Log.e("********",DateUtil.getInstance().getDateOfToDay());
                         Log.e("********",PlanPatrolTime.substring(0,10));
                        /* if(DateUtil.getInstance().getDateOfToDay().equals(PlanPatrolTime.substring(0,10))){
                             task.add(模块用法);
                         }*/
                        task.add(info);


                    }

                    if(task.size()<0){
                        emptyLayout.showEmpty();
                        mList.setVisibility(View.GONE);
                    }

                    adapter=null;
                    showListView(task,IsRelationWorksheet);
                    //自定义的滚动监听事件
                    InspectionTaskOnScrollListener onScrollListener = new InspectionTaskOnScrollListener(footer,getActivity(),ClassId,total);
                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(InspectionFragment01.this);
                    //设置ListView的滚动监听事件
                    mList.setOnScrollListener(onScrollListener);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                emptyLayout.showError();
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
        inflater = LayoutInflater.from(getActivity());
        //MyTool.getInstance().showLoadingDailog(dialog,getActivity());
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(getActivity())
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        tx_back= (TextView) view_layout.findViewById(R.id.tx_back);
        lay_back= (RelativeLayout) view_layout.findViewById(R.id.lay_back);
        title_back= (ImageView) view_layout.findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        line = view_layout.findViewById(R.id.line);
        View view = inflater.inflate(R.layout.popup,null);
        lv = (ListView) view.findViewById(R.id.lv);
        btn = (PopupButton) view_layout.findViewById(R.id.btn);
        btn.setPopupView(view,line);
        emptyLayout = (EmptyLayout) view_layout.findViewById(R.id.emptyLayout);


    }

    @Override
    public void onResume() {
        super.onResume();
        if(data.size()>0){
            initData(data.get(index).getClassId(),data.get(index).getIsRelationWorksheet());
        }
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
            default:

            break;



        }
    }
    /**
     * 将数据加载到ListView上
     *
     * @param
     */
    private void showListView(List<TaskInfo> task,String IsRelationWorksheet) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件
            mList=(ListView) view_layout.findViewById(R.id.type_listview);
            //将底部加载一个加载更多的布局
            footer = LayoutInflater.from(getActivity()).inflate(R.layout.foot_boot, null);
            //初始状态为隐藏
            footer.setVisibility(View.GONE);
            //加入到ListView的底部
            if(!isAdd){
                isAdd=true;
                mList.addFooterView(footer);
            }

            //创建adpter数据
            adapter = new TaskAdapter(getActivity(),task,IsRelationWorksheet);
            //设置adapter
            mList.setAdapter(adapter);
        } else {
            //不为空，则刷新数据
            this.task.addAll(task);
            //提醒ListView重新更新数据
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onLoadData(List<TaskInfo> task) {
        showListView(task,data.get(index).getIsRelationWorksheet());
    }


    @Override
    public void process(Message msg) {

    }
}
