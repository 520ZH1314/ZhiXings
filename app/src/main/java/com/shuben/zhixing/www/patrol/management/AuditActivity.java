package com.shuben.zhixing.www.patrol.management;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.listener.MyQuestionOnScrollListener;
import com.shuben.zhixing.www.patrol.adapter.QuestionAdapter;
import com.shuben.zhixing.www.patrol.bean.MyQuseInfo;
import com.shuben.zhixing.www.util.CalendarUtil;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SizeHelper;
import com.shuben.zhixing.www.util.SysUtils;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditActivity extends BaseActvity implements View.OnClickListener,MyQuestionOnScrollListener.OnloadDataListener{

    private HorizontalScrollView hs_activity_tabbar;
    private RadioGroup myRadioGroup;
    private List<String> titleList;
    private LinearLayout ll_activity_tabbar_content;
    private RelativeLayout search_lay;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private String channel;
    private TextView tx_back;
    private RelativeLayout lay_back;
    private ImageView title_back;

    private ListView record_listview;

    private List<String>  list01;
    private List<String>  list02;
    private List<String>  list03;
    private NiceSpinner niceSpinner01;
    private NiceSpinner niceSpinner02;
    private NiceSpinner niceSpinner03;
    private TextView tx_date01,tx_date02;
    private Button bnt_date01,bnt_date02;
    private List<MyQuseInfo> data=new ArrayList<MyQuseInfo>();

    private String myDate="";
    private String type="未审核";
    private int index01,index02;
    private String WorkShopId="";
    private String ClassId="";

    private QuestionAdapter adapter;
    private ListView mList;
    //底部加载更多布局
    private View footer;
    private  boolean isAdd=false;
    private LoadingDailog dialog=null;//加载动画
    private  String SearchType="3";


    @Override
    public int getLayoutId() {
        return R.layout.activity_audit;
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
        titleList = new ArrayList<String>();
        titleList.add("未审核");
        titleList.add("已审核");
        initView();
        initGroup();
        loadData("5",WorkShopId,ClassId,SearchType);
    }

    private void loadData(final String status, final String WorkShopId, final String ClassId,final String SearchType ) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetProblemListByStatus");
        params.put("TenantId", SharedPreferencesTool.getMStool(AuditActivity.this).getTenantId());
        params.put("LiableUserId", SharedPreferencesTool.getMStool(AuditActivity.this).getUserId());//责任人
        params.put("Status", status);//状态
        params.put("DueDate", myDate);//
        params.put("ClassId", ClassId);//状态
        params.put("WorkShopId", WorkShopId);//状态
        params.put("SearchType", SearchType);//状态
        params.put("page", "1");
        params.put("rows", "10");
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetProblemListByStatus");
            myData.put("TenantId", SharedPreferencesTool.getMStool(AuditActivity.this).getTenantId());
            myData.put("LiableUserId", SharedPreferencesTool.getMStool(AuditActivity.this).getUserId());//责任人
            myData.put("Status", status);//状态
            myData.put("DueDate", myDate);//
            myData.put("ClassId", ClassId);//状态
            myData.put("WorkShopId", WorkShopId);//状态
            myData.put("SearchType", SearchType);//状态
            myData.put("page", "1");
            myData.put("rows", "10");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******我的审核*****",myData.toString());


        //Log.e("params", params.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(AuditActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                dialog.dismiss();
                Log.e("KKKK我的审核KKKK", " " + jsonobj.toString());
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    data=new ArrayList<MyQuseInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);

                        String ProblemNo=jData.getString("ProblemNo");//问题记录ID
                        String ProblemId=jData.getString("ProblemId");//问题记录ID
                        String RecordId=jData.getString("RecordId");//巡线任务记录ID
                        String ProblemDesc=jData.getString("ProblemDesc");//问题描述
                        float Severity= (float) jData.getDouble("Severity");//严重程度
                        String LiableDeptId=jData.getString("LiableDeptId");//责任部门ID
                        String WorkShopName=jData.getString("WorkShopName");//责任部门名称
                        String ClassName=jData.getString("ClassName");//责任人ID
                        String PatrolUserName=jData.getString("PatrolUserName");//责任人姓名
                        String DueDate=jData.getString("DueDate").replaceAll("T"," ");//要求完成时间
                        String CompleteDate=jData.getString("CompleteDate").replaceAll("T"," ");//实际完成时间
                        String Status=jData.getString("Status");//
                        data.add(new MyQuseInfo(ProblemNo,ProblemId,RecordId,ProblemDesc,Severity,LiableDeptId,WorkShopName,ClassName,PatrolUserName,DueDate,CompleteDate,Status));
                    }
                    adapter=null;
                    showListView(data);
                    //自定义的滚动监听事件
                    MyQuestionOnScrollListener onScrollListener = new MyQuestionOnScrollListener(footer,AuditActivity.this,total,status,WorkShopId,ClassId,myDate,SearchType);
                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(AuditActivity.this);
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


    private void initView() {
        record_listview=(ListView)findViewById(R.id.a_listview);
        search_lay=(RelativeLayout) findViewById(R.id.search_lay);
        search_lay.setOnClickListener(this);
        //myDate=DateUtil.getInstance().getDateOfToDay()+"到";
    }

    private void initGroup() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        hs_activity_tabbar= (HorizontalScrollView) this.findViewById(R.id.hs_activity_tabbar);
        ll_activity_tabbar_content= (LinearLayout) this.findViewById(R.id.ll_activity_tabbar_content);
        //选项卡布局
        myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        ll_activity_tabbar_content.addView(myRadioGroup);
        for (int i = 0; i < titleList.size(); i++) {
            String channel = titleList.get(i);
            RadioButton radio = new RadioButton(this);
            radio.setButtonDrawable(android.R.color.transparent);
            radio.setBackgroundResource(R.drawable.radiobtn_selector);
            ColorStateList csl = getResources().getColorStateList(R.color.radiobtn_text_color);
            radio.setTextColor(csl);
            WindowManager manager = this.getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            int w = SysUtils.getScreenWidth(AuditActivity.this)/2;
            // LinearLayout.LayoutParams l = new LinearLayout.LayoutParams((int) SizeHelper.dp2px(this, w/3), ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setTextSize(15);
            radio.setGravity(Gravity.CENTER);
            radio.setText(channel);
            radio.setTag(channel);
            myRadioGroup.addView(radio);


            ListView q_listview=(ListView) findViewById(R.id.q_listview);
            tx_back= (TextView) findViewById(R.id.tx_back);
            lay_back= (RelativeLayout) findViewById(R.id.lay_back);
            title_back= (ImageView) findViewById(R.id.title_back);
            title_back.setOnClickListener(this);
            tx_back.setOnClickListener(this);
            lay_back.setOnClickListener(this);


        }


        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                channel = (String) rb.getTag();
                mCurrentCheckedRadioLeft = rb.getLeft();//更新当前按钮距离左边的距离
                int width=(int) SizeHelper.dp2px(AuditActivity.this, 150);
                hs_activity_tabbar.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);
                if(channel.equals("未审核")){
                    //加载未处理数据
                    if(!type.equals("未审核")){
                        type="未审核";
                        loadData("5",WorkShopId,ClassId,SearchType);
                    }

                }else if(channel.equals("已审核")){
                    //加载待审核数据
                    type="已审核";
                    loadData("10",WorkShopId,ClassId,SearchType);
                }


            }
        });
        //设定默认被选中的选项卡为第一项
        if (!titleList.isEmpty()) {
            myRadioGroup.check(myRadioGroup.getChildAt(0).getId());
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.tx_back:
                finish();
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.search_lay:
                showPopWindow();
                break;
            default:

                break;



        }
    }

    private void showPopWindow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(AuditActivity.this);
        View view = (LinearLayout)AuditActivity.this. getLayoutInflater().inflate(R.layout.serach, null);
        builder.setView(view);
        builder.setTitle("设置");
        niceSpinner01=(NiceSpinner) view.findViewById(R.id.nice_spinner01);
        niceSpinner02=(NiceSpinner) view.findViewById(R.id.nice_spinner02);
        niceSpinner03=(NiceSpinner) view.findViewById(R.id.nice_spinner03);
        niceSpinner01.setTextColor(Color.rgb(22,155,213));
        niceSpinner02.setTextColor(Color.rgb(22,155,213));
        niceSpinner03.setTextColor(Color.rgb(22,155,213));
        //niceSpinner01.setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        //niceSpinner02.setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        //niceSpinner03.setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        tx_date01=(TextView) view.findViewById(R.id.tx_date01);
        tx_date02=(TextView) view.findViewById(R.id.tx_date02);
        tx_date01.setText(DateUtil.getInstance().getDateOfToDay());
        tx_date02.setText(DateUtil.getInstance().getDateOfToDay());
        bnt_date01=(Button) view.findViewById(R.id.bnt_calendar01);
        bnt_date02=(Button) view.findViewById(R.id.bnt_calendar02);
        bnt_date01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate01();
            }
        });
        bnt_date02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate02();
            }
        });

        loadWorkShop();
        loadClass();


        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                dialog.cancel();
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

    private void loadWorkShop() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetWorkShopList");
        params.put("TenantId", SharedPreferencesTool.getMStool(AuditActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(AuditActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                // Log.e("KKKKKKKK", " " + jsonobj.to);
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list01=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        list01.add(jData.getString("WorkShopName"));
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

    private void loadClass() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetLinePatrolClass");
        params.put("TenantId", SharedPreferencesTool.getMStool(AuditActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(AuditActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                // Log.e("KKKKKKKK", " " + jsonobj.to);
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list02=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        list02.add(jData.getString("ClassName"));
                    }
                    niceSpinner02.attachDataSource(list02);

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

    private void setDate01() {
        CalendarUtil.getInstance().setDate(AuditActivity.this,tx_date01);
    }

    private void setDate02() {
        CalendarUtil.getInstance().setDate(AuditActivity.this,tx_date02);
    }

    /**
     * 将数据加载到ListView上
     *
     * @param data
     */
    private void showListView(List<MyQuseInfo> data) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件
            mList = (ListView) findViewById(R.id.a_listview);
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
            adapter = new QuestionAdapter(AuditActivity.this,data,"我的审核");
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
    public void onLoadData(List<MyQuseInfo> data) {
        showListView(data);
    }
}
