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
import android.widget.AdapterView;
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
import com.shuben.zhixing.www.listener.RecordOnScrollListener;
import com.shuben.zhixing.www.patrol.adapter.RecordAdapter;
import com.shuben.zhixing.www.patrol.bean.RecordInfo;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordActivity extends BaseActvity implements View.OnClickListener,RecordOnScrollListener.OnloadDataListener{

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

    private List<RecordInfo> r_list;

    private List<String>  list01;
    private List<String>  listId01;
    private List<String>  list02;
    private List<String>  listId02;
    private List<String>  list03;
    private NiceSpinner niceSpinner01;
    private NiceSpinner niceSpinner02;
    private NiceSpinner niceSpinner03;
    private TextView tx_date01,tx_date02;
    private Button bnt_date01,bnt_date02;
    private String type="未处理";
    private String WorkShopId="";
    private String ClassId="";
    private LoadingDailog dialog;//加载动画
    private String myDate="";
    private List<RecordInfo> data;
    private int index01,index02;

    private RecordAdapter adapter;
    private ListView mList;
    //底部加载更多布局
    View footer;
    private int page=1;
    private  boolean isAdd=false;


    @Override
    public int getLayoutId() {
        return R.layout.activity_record;
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
        titleList.add("未处理");
        titleList.add("待审核");
        titleList.add("已通过");
        initView();
        initGroup();
        initData("0",WorkShopId,ClassId);
    }

    private void initData(final String mStatus, final String WorkShopId, final String ClassId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetRecordProblem");
        params.put("TenantId", SharedPreferencesTool.getMStool(RecordActivity.this).getTenantId());
        params.put("Status", mStatus);
        params.put("WorkShopId", WorkShopId);
        params.put("ClassId", ClassId);
        params.put("DueDate", myDate);

        params.put("page", "1");
        params.put("rows", "10");
        final JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetRecordProblem");
            myData.put("TenantId", SharedPreferencesTool.getMStool(RecordActivity.this).getTenantId());
            myData.put("Status", mStatus);
            myData.put("WorkShopId", WorkShopId);
            myData.put("ClassId", ClassId);
            myData.put("DueDate", myDate);
            myData.put("page", page+"");
            myData.put("rows", "10");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("**************",myData.toString());



        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(RecordActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    JSONObject jData01;
                    JSONObject jData02;
                    data=new ArrayList<RecordInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String ClassName=jData.getString("ClassName");//巡线类别
                        Log.e("******AAAA******","1");
                        String WorkShopName=jData.getString("WorkShopName");//车间名称
                        Log.e("******AAAA******","2");
                        String ProblemNo=jData.getString("ProblemNo");//问题单号
                        Log.e("******AAAA******","3");
                        String ProblemId=jData.getString("ProblemId");//问题单号
                        Log.e("******AAAA******","4");
                        String RecordId=jData.getString("RecordId");//巡线任务记录ID
                        Log.e("******AAAA******","5");
                        String ProblemDesc=jData.getString("ProblemDesc");//问题描述
                        Log.e("******AAAA******","6");
                        float Severity= (float) jData.getDouble("Severity");//严重程度
                        Log.e("******AAAA******","7");
                        String LiableDeptId=jData.getString("LiableDeptId"); //责任部门ID
                        Log.e("******AAAA******","8");
                        String LiableDeptName=jData.getString("LiableDeptName");//责任部门名称
                        Log.e("******AAAA******","9");
                        String LiableUserId=jData.getString("LiableUserId");//责任人ID
                        Log.e("******AAAA******","10");
                        String LiableUserName=jData.getString("LiableUserName"); //责任人姓名
                        Log.e("******AAAA******","11");
                        String DueDate=jData.getString("DueDate").replaceAll("T"," ");//要求完成时间
                        Log.e("******AAAA******","12");
                        String CompleteDate=jData.getString("CompleteDate").replaceAll("T"," ");//实际完成时间
                        Log.e("******AAAA******","13");
                        String Status=jData.getString("Status");//问题状态，－10//审核未通过，－5//未完成，0//未启动，2//进行中，5//已处理（待审核），10//已审核
                        Log.e("******AAAA******","14");
                        String HandleResult=jData.getString("HandleResult");//处理意见
                        String AuditDesc=jData.getString("AuditDesc");//审核意见
                        String AuditResult=jData.getString("AuditResult");//审核意见
                        int Thumb=jData.getInt("Thumb");//点赞数
                        String FilePath01="";
                        String FilePath02="";
                        JSONArray ListFile=jData.getJSONArray("FileList");//改善前照片列表，值为json格式
                        Log.e("******AAAA******","15");
                        for(int j=0;j<ListFile.length();j++){
                            jData01=ListFile.getJSONObject(j);

                            if (!FilePath01.equals("")){
                                FilePath01 += "#";
                            }
                            FilePath01 += jData01.getString("FilePath");                         }
                       JSONArray ImprovedListFile=jData.getJSONArray("ImprovedListFile");//改善前照片列表，值为json格式
                        Log.e("******AAAA******","16");
                        for(int k=0;k<ImprovedListFile.length();k++) {
                            jData02 = ImprovedListFile.getJSONObject(k);

                            if (!FilePath02.equals("")) {
                                FilePath02 += "#";
                            }
                            FilePath02 += jData02.getString("FilePath");
                        }
                        RecordInfo recordInfo=new RecordInfo(ClassName,WorkShopName,ProblemNo,ProblemId,RecordId,ProblemDesc,
                                                   Severity,HandleResult,LiableDeptName,AuditResult,LiableUserName,
                                                   DueDate,CompleteDate,Status,FilePath01,FilePath02,AuditResult,Thumb,"记录");
                        data.add(recordInfo);
                        Log.e("%%%%%%%%%%",data.size()+"");

                    }
                    adapter=null;
                    showListView(data);
                    //自定义的滚动监听事件
                    RecordOnScrollListener onScrollListener = new RecordOnScrollListener(footer,RecordActivity.this,total,mStatus,WorkShopId,ClassId,myDate,"记录");
                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(RecordActivity.this);
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
    private void showListView(List<RecordInfo> data) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件
            mList = (ListView) findViewById(R.id.record_listview);
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
            adapter = new RecordAdapter(RecordActivity.this,data);
            //设置adapter
            mList.setAdapter(adapter);
        } else {
            //不为空，则刷新数据
            this.data.addAll(data);
            //提醒ListView重新更新数据
            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        //String star=DateUtil.getInstance().getDateOfMonthStart();
        String end=DateUtil.getInstance().getDateOfToDay();
        String createTime = "";
        createTime=" 到";
       /* try {
            createTime = URLEncoder.encode("到", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        //myDate=end+createTime;


        record_listview=(ListView)findViewById(R.id.record_listview);
        search_lay=(RelativeLayout) findViewById(R.id.search_lay);
        search_lay.setOnClickListener(this);
    }

    private void initGroup() {
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
            //int w = outMetrics.widthPixels;

            int w =SysUtils.getScreenWidth(RecordActivity.this)/3;
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
                int width=(int) SizeHelper.dp2px(RecordActivity.this, 150);
                hs_activity_tabbar.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);
                if(channel.equals("未处理")){
                    //加载未处理数据
                    if(!type.equals("未处理")){
                        type="未处理";
                        initData("0",WorkShopId,ClassId);
                    }

                }else if(channel.equals("待审核")){
                    //加载待审核数据
                    type="待审核";
                    initData("5",WorkShopId,ClassId);

                }else if(channel.equals("已通过")){
                    //加载已通过数据
                    type="已通过";
                    initData("10",WorkShopId,ClassId);

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
            case  R.id.lay_back:
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
        AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
        View view = (LinearLayout)RecordActivity.this. getLayoutInflater().inflate(R.layout.serach, null);
        builder.setView(view);
        builder.setTitle("设置");
        niceSpinner01=(NiceSpinner) view.findViewById(R.id.nice_spinner01);
        niceSpinner02=(NiceSpinner) view.findViewById(R.id.nice_spinner02);
        niceSpinner03=(NiceSpinner) view.findViewById(R.id.nice_spinner03);
        niceSpinner01.setTextColor(Color.rgb(22,155,213));
        niceSpinner02.setTextColor(Color.rgb(22,155,213));
        niceSpinner03.setTextColor(Color.rgb(22,155,213));
        //niceSpinner01.setTextSize(TypedValue.COMPLEX_UNIT_PX,24);
        //niceSpinner02.setTextSize(TypedValue.COMPLEX_UNIT_PX,24);
        //niceSpinner03.setTextSize(TypedValue.COMPLEX_UNIT_PX,24);

        niceSpinner01.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index01=position;
                WorkShopId=listId01.get(index01);
            }
        });
        niceSpinner02.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index02=position;
                ClassId=listId02.get(index02);
            }
        });
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
                String star=tx_date01.getText().toString();
                String end=tx_date02.getText().toString();
                String createTime = "";
                try {
                    createTime = URLEncoder.encode("到", "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                myDate=star+createTime+end;



                if(type.equals("未处理")){
                    initData("0",listId01.get(index01),listId02.get(index02));
                }else if(type.equals("待审核")){
                    initData("5",listId01.get(index01),listId02.get(index02));
                }if(type.equals("已通过")){
                    initData("10",listId01.get(index01),listId02.get(index02));
                }


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
        params.put("TenantId", SharedPreferencesTool.getMStool(RecordActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST,SharedPreferencesTool.getMStool(RecordActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                // Log.e("KKKKKKKK", " " + jsonobj.to);
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list01=new ArrayList<String>();
                    listId01=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        list01.add(jData.getString("WorkShopName"));
                        listId01.add(jData.getString("WorkShopId"));
                    }
                    niceSpinner01.attachDataSource(list01);

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

    private void loadClass() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetLinePatrolClass");
        params.put("TenantId", SharedPreferencesTool.getMStool(RecordActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(RecordActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                // Log.e("KKKKKKKK", " " + jsonobj.to);
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list02=new ArrayList<String>();
                    listId02=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        list02.add(jData.getString("ClassName"));
                        listId02.add(jData.getString("ClassId"));
                    }
                    niceSpinner02.attachDataSource(list02);

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

    private void setDate01() {
        CalendarUtil.getInstance().setDate02(RecordActivity.this,tx_date01);
    }

    private void setDate02() {
        CalendarUtil.getInstance().setDate02(RecordActivity.this,tx_date02);
    }


    @Override
    public void onLoadData(List<RecordInfo> data) {
        showListView(data);
    }
}


