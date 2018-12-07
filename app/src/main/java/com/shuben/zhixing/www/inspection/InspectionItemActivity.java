package com.shuben.zhixing.www.inspection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

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
import com.shuben.zhixing.www.inspection.adapter.ItemAdapter;
import com.shuben.zhixing.www.inspection.bean.ItemInfo;
import com.shuben.zhixing.www.inspection.bean.ParamInfo;
import com.shuben.zhixing.www.listener.InspectionItemOnScrollListener;
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

public class InspectionItemActivity extends BaseActvity implements View.OnClickListener, InspectionItemOnScrollListener.OnloadDataListener{
    private HorizontalScrollView hs_activity_tabbar;
    private RadioGroup myRadioGroup;
    private List<String> titleList;
    private LinearLayout ll_activity_tabbar_content,lay_back;

    private TextView tx_close,tx_back;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private String  type="人";
    private String  PatrolTaskId="";
    private List<ParamInfo> plist=null;
    private String channel;
    private LoadingDailog dialog=null;//加载动画
    private ListView standar_listview;
    //底部加载更多布局
    View footer;
    private ItemAdapter adapter;
    private ListView mList;
    private  boolean isAdd=false;
    private List<ItemInfo> data;
    private Intent intent;
    private ImageView title_back;


    @Override
    public int getLayoutId() {
        return R.layout.activity_inspection_item;
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
        initView();
        initData(PatrolTaskId,type);
    }

    private void initData(final String PatrolTaskId, final String ItemType) {
        RequestQueue requestQueue = Volley.newRequestQueue(InspectionItemActivity.this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetAllCheckItemByTaskId");
        params.put("TenantId", SharedPreferencesTool.getMStool(InspectionItemActivity.this).getTenantId());
        params.put("PatrolTaskId", PatrolTaskId);
        params.put("ItemType", ItemType);

        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "PatrolInspection");
            myData.put("ApiCode", "GetAllCheckItemByTaskId");
            myData.put("TenantId", SharedPreferencesTool.getMStool(InspectionItemActivity.this).getTenantId());
            myData.put("PatrolTaskId", PatrolTaskId);
            myData.put("ItemType", ItemType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******巡检项********",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST,  SharedPreferencesTool.getMStool(InspectionItemActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK巡检项KKKK", " " + jsonobj.toString());

                dialog.dismiss();
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    data=new ArrayList<ItemInfo>();
                    JSONObject jData;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String PatrolRecord=jData.getString("PatrolRecordId");//巡检记录ID
                        String PatrolTaskId=jData.getString("PatrolTaskId");//巡检任务ID
                        String ItemId=jData.getString("ItemId");//巡检项ID
                        String ItemName=jData.getString("ItemName");//巡线项描述
                        String ItemType=jData.getString("ItemType");//类型，人、机、料、法、环
                        String PatrolFashion=jData.getString("PatrolFashion");//巡检方式，取值有;//数量、拍照、说明
                        String Result=jData.getString("Result");//取值有OK/NG/空值
                        String CreateTime=jData.getString("CreateTime").replaceAll("T"," ");
                        String ItemSource=jData.getString("ItemSource");//取值有OK/NG/空值
                        JSONObject PatrolResult=jData.getJSONObject("PatrolResult");
                        JSONArray ss=PatrolResult.getJSONArray("rows");
                        JSONObject jsonObject;
                        if(plist==null){
                            plist=new ArrayList<ParamInfo>();
                        }else{
                            plist.clear();
                        }

                        try {
                            for(int j=0;j<ss.length();j++){
                                jsonObject=ss.getJSONObject(j);
                                String PatrolResultId=jsonObject.getString("PatrolResultId");
                                String PatrolRecordId=jsonObject.getString("PatrolRecordId");
                                String PatrolTaskId02=jsonObject.getString("PatrolTaskId");
                                String ItemId02=jsonObject.getString("ItemId");
                                String MappingId=jsonObject.getString("MappingId");
                                String PatrolValue=jsonObject.getString("PatrolValue");
                                String ItemSource02=jsonObject.getString("ItemSource");
                                String CategoryCode=jsonObject.getString("CategoryCode");
                                String CategoryName=jsonObject.getString("CategoryName");
                                String StandText=jsonObject.getString("StandText");
                                String StandValue=jsonObject.getString("StandValue");
                                String StandMaxValue=jsonObject.getString("StandMaxValue");
                                String StandMinValue=jsonObject.getString("StandMinValue");
                                String ParamName=jsonObject.getString("ParamName");
                                String ParamUnit=jsonObject.getString("ParamUnit");
                                ParamInfo  paramInfo=new ParamInfo(PatrolResultId,PatrolRecordId,PatrolTaskId02,ItemId02,MappingId,PatrolValue,ItemSource02,CategoryCode,CategoryName,StandText,StandValue,StandMaxValue,StandMinValue,ParamName,ParamUnit);
                                plist.add(paramInfo);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();




                        }

                        ItemInfo info=new ItemInfo(PatrolRecord,PatrolTaskId,ItemId,ItemName,ItemType,PatrolFashion,Result,CreateTime,ItemSource,plist);
                        data.add(info);
                    }
                    adapter=null;
                    showListView(data);
                    //自定义的滚动监听事件
                    InspectionItemOnScrollListener onScrollListener = new InspectionItemOnScrollListener(footer,InspectionItemActivity.this,PatrolTaskId,ItemType,total,plist);
                    //设置接口回调
                    onScrollListener.setOnLoadDataListener(InspectionItemActivity.this);
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
        PatrolTaskId=getIntent().getStringExtra("PatrolTaskId");
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(InspectionItemActivity.this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        initGroup();
        tx_back= (TextView) findViewById(R.id.tx_back);
        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        title_back= (ImageView) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        tx_close= (TextView) findViewById(R.id.tx_close);
        tx_close.setOnClickListener(this);
        standar_listview= (ListView) findViewById(R.id.standar_listview);
        if(getIntent().getStringExtra("Status").equals("已完成")){
            tx_close.setVisibility(View.INVISIBLE);
        }




    }
    private void initGroup() {
        titleList = new ArrayList<String>();
        titleList.add("人");
        titleList.add("机");
        titleList.add("料");
        titleList.add("法");
        titleList.add("环");
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
            int w = SysUtils.getScreenWidth(InspectionItemActivity.this)/5;
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
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                channel = (String) rb.getTag();
                mCurrentCheckedRadioLeft = rb.getLeft();//更新当前按钮距离左边的距离
                int width=(int) SizeHelper.dp2px(InspectionItemActivity.this, 150);
                hs_activity_tabbar.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);
                if(channel.equals("人")){
                    //加载未处理数据
                    if(!type.equals("人")){
                        type="人";
                        initData(PatrolTaskId,type);
                    }

                }else if(channel.equals("机")){
                    //加载待审核数据
                    type="机";
                    initData(PatrolTaskId,type);

                } else if(channel.equals("料")){
                    //加载待审核数据
                    type="料";
                    initData(PatrolTaskId,type);

                }else if(channel.equals("法")){
                    //加载待审核数据
                    type="法";
                    initData(PatrolTaskId,type);

                }else if(channel.equals("环")){
                    //加载待审核数据
                    type="环";
                    initData(PatrolTaskId,type);

                }


            }
        });
        //设定默认被选中的选项卡为第一项
        if (!titleList.isEmpty()) {
            myRadioGroup.check(myRadioGroup.getChildAt(0).getId());
        }

    }

    /**
     * 将数据加载到ListView上
     *
     * @param data
     */
    private void showListView(List<ItemInfo> data) {
        //首先判断适配器是否为空，首次运行肯定是为空的
        if (adapter == null) {
            //查到ListView控件
            mList = (ListView) findViewById(R.id.standar_listview);
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
            adapter = new ItemAdapter(InspectionItemActivity.this,data,getIntent().getStringExtra("Status"));
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
    public void onLoadData(List<ItemInfo> data) {
        showListView(data);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                 intent=new Intent();
                setResult(UrlUtil.InspectionItemActivity_ResultCode,intent);
                finish();
                break;
            case R.id.tx_back:
                 intent=new Intent();
                setResult(UrlUtil.InspectionItemActivity_ResultCode,intent);
                finish();
                break;
            case R.id.title_back:
                 intent=new Intent();
                setResult(UrlUtil.InspectionItemActivity_ResultCode,intent);
                finish();
                break;

            case R.id.tx_close:
                showPopWindow();
                break;


            
            default:
            
            break;
        
        
        
        }
    }

    private void close() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "EditPatrolInspectionEnd");
        params.put("TenantId", SharedPreferencesTool.getMStool(InspectionItemActivity.this).getTenantId());
        params.put("PatrolTaskId", getIntent().getStringExtra("PatrolTaskId"));


        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditPatrolLineEnd");
            data.put("TenantId", SharedPreferencesTool.getMStool(InspectionItemActivity.this).getTenantId());
            data.put("PatrolTaskId", getIntent().getStringExtra("PatrolTaskId"));

            Log.e("***************",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(InspectionItemActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(InspectionItemActivity.this,"结束巡检",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(InspectionItemActivity.this,message,Toast.LENGTH_SHORT).show();
                    }




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

    private void showPopWindow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(InspectionItemActivity.this);
        View view = (LinearLayout) InspectionItemActivity.this.getLayoutInflater().inflate(R.layout.task, null);
        TextView tx_content=(TextView) view.findViewById(R.id.tx_content);
        tx_content.setText("是否关闭当前巡检任务");
        builder.setView(view);
        builder.setTitle("提醒");

        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                close();
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


    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 63:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        };
    };
}
