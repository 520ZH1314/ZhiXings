package com.shuben.zhixing.www.inspection.fragment;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.adapter.ProblemAdapter;
import com.shuben.zhixing.www.inspection.bean.InspectionQInfo;
import com.shuben.zhixing.www.listener.InspectionQOnScrollListener;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SizeHelper;
import com.shuben.zhixing.www.util.SysUtils;
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

public class InspectionFragment02 extends BaseFragment implements View.OnClickListener,InspectionQOnScrollListener.OnloadDataListener{
    private View view_layout;
    private Activity context;
    private RelativeLayout lay_back;
    private ImageView title_back;
    private TextView tx_back;
    private HorizontalScrollView hs_activity_tabbar;
    private RadioGroup myRadioGroup;
    private List<String> titleList;
    private LinearLayout ll_activity_tabbar_content;
    private RelativeLayout search_lay;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private String channel;
    private ListView record_listview;


    private String myDate="";
    private String type="我的问题";
    private int index01,index02;
    private String WorkShopId="";
    private String ClassId="";

    private ProblemAdapter adapter;
    private ListView mList;
    private List<InspectionQInfo> data=null;
    //底部加载更多布局
    private View footer;
    private  boolean isAdd=false;
    private LoadingDailog dialog=null;//加载动画
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_inspection02,container,false);
        context = getActivity();


        return view_layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initGroup();
        
    }

    private int des;
    //设置我的问题第二序号
    public void setDes(int des){
        this.des = des;
    }
    private void initView() {
        tx_back= (TextView) view_layout.findViewById(R.id.tx_back);
        lay_back= (RelativeLayout) view_layout.findViewById(R.id.lay_back);
        title_back= (ImageView) view_layout.findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        titleList = new ArrayList<String>();
        titleList.add("我的问题");
        titleList.add("我的发现");

    }
    private void initGroup() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(context)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        hs_activity_tabbar= (HorizontalScrollView) view_layout.findViewById(R.id.hs_activity_tabbar);
        ll_activity_tabbar_content= (LinearLayout) view_layout.findViewById(R.id.ll_activity_content);
        //选项卡布局
        myRadioGroup = new RadioGroup(context);
        myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        ll_activity_tabbar_content.addView(myRadioGroup);
        for (int i = 0; i < titleList.size(); i++) {
            String channel = titleList.get(i);
            RadioButton radio = new RadioButton(context);
            radio.setButtonDrawable(android.R.color.transparent);
            radio.setBackgroundResource(R.drawable.radiobtn_selector);
            ColorStateList csl = getResources().getColorStateList(R.color.radiobtn_text_color);
            radio.setTextColor(csl);
            WindowManager manager = context.getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            int w = SysUtils.getScreenWidth(context)/2;
            // LinearLayout.LayoutParams l = new LinearLayout.LayoutParams((int) SizeHelper.dp2px(this, w/3), ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setTextSize(15);
            radio.setGravity(Gravity.CENTER);
            radio.setText(channel);
            radio.setTag(channel);
            myRadioGroup.addView(radio);




        }


        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) view_layout.findViewById(radioButtonId);
                channel = (String) rb.getTag();
                mCurrentCheckedRadioLeft = rb.getLeft();//更新当前按钮距离左边的距离
                int width=(int) SizeHelper.dp2px(context, 150);
                hs_activity_tabbar.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);
                P.c(type+"==="+channel);
                if(channel.equals("我的问题")){
                    //加载未处理数据
                    type="我的问题";
                    loadData("0","",type);
                   /* if(!type.equals("我的问题")){
                        type="我的问题";
                        loadData("0","",type);
//                        loadData("","",type);
                    }*/

                }else if(channel.equals("我的发现")){
                    //加载待审核数据
                    type="我的发现";
                    loadData("0","",type);

                }


            }
        });
        //设定默认被选中的选项卡为第一项
       select(des);

    }
    private void select(int des){
        if (!titleList.isEmpty()) {
            myRadioGroup.check(myRadioGroup.getChildAt(des).getId());
        }
    }

    private void loadData(final String status, final String DueDate,final String type) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetRecordProblem");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        if(type.equals("我的问题")){
            params.put("LiableUserId", SharedPreferencesTool.getMStool(context).getUserId());//责任人
        }else{
            params.put("PatrolUserId", SharedPreferencesTool.getMStool(context).getUserId());//责任人
        }

        params.put("Status", status);//状态
        params.put("DueDate", DueDate);//
        params.put("page", "1");
        params.put("rows", "10");
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "PatrolInspection");
            myData.put("ApiCode", "GetRecordProblem");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            if(type.equals("我的问题")){
                myData.put("LiableUserId", SharedPreferencesTool.getMStool(context).getUserId());//责任人
            }else{
                myData.put("PatrolUserId", SharedPreferencesTool.getMStool(context).getUserId());//责任人
            }
            myData.put("Status", status);//状态
            myData.put("DueDate", "");//
            myData.put("page", "1");
            myData.put("rows", "10");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******我的问题*****",myData.toString());


        //Log.e("params", params.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK我的问题KKKK", " " + jsonobj.toString());
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
                        String LiableDeptId = jData.getString("LiableDeptId");//工单号
                        String LiableDeptName = jData.getString("LiableDeptName");//巡检人ID
                        String LiableUserId = jData.getString("LiableUserId");//巡线人姓名
                        String LiableUserName = jData.getString("LiableUserName");//计划巡检时间
                        String PatrolUserName = jData.getString("PatrolUserName");
                        String DueDate = jData.getString("DueDate").replaceAll("T"," ");//实际巡检时间
                        String CompleteDate = jData.getString("CompleteDate");//结束巡检时间
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
                       // String ProductName=jData.getString("ProductName");
                        InspectionQInfo info = new InspectionQInfo(ProblemNo,ProblemId,PatrolTaskId, ProblemNo, LiableDeptId, LiableDeptName, LiableUserId, LiableUserName,PatrolUserName, DueDate, CompleteDate, Status,"");
                        data.add(info);
                    }
                  adapter=null;
                    showListView(data);
                    //自定义的滚动监听事件
                    InspectionQOnScrollListener onScrollListener = new InspectionQOnScrollListener(footer,context,total,status,myDate);
                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(InspectionFragment02.this);
                    //设置ListView的滚动监听事件
                   // mList.setOnScrollListener(onScrollListener);
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
     * @param data
     */
    private void showListView(List<InspectionQInfo> data) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件
            mList = (ListView) view_layout.findViewById(R.id.a_listview);
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
            adapter = new ProblemAdapter(context,data,"我的问题");
            adapter.setDes(des);
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
    public void process(Message msg) {

    }
}

