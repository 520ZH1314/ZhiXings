package com.shuben.zhixing.www.patrol.threeclass;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

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
import com.shuben.zhixing.www.patrol.adapter.GroupTaskAdapter;
import com.shuben.zhixing.www.patrol.bean.GroupTaskInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SizeHelper;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskPerformActivity extends BaseActvity implements View.OnClickListener{
    private HorizontalScrollView hs_activity_tabbar;
    private RadioGroup myRadioGroup;
    private List<String> titleList;
    private LinearLayout ll_activity_tabbar_content,lay_back;
    private RelativeLayout search_lay;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private String channel="";
    private List<GroupTaskInfo> list_data=new ArrayList<GroupTaskInfo>();
    private ListView group_task_listview;
    private LoadingDailog dialog;//加载动画
    private String type="未处理";

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_perform;
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
        initData("0");
    }


    private void initData(String status) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetTaskGroupJob");
        params.put("TenantId", SharedPreferencesTool.getMStool(TaskPerformActivity.this).getTenantId());
        params.put("ParentRecordId", getIntent().getStringExtra("RecordId"));//责任人
        params.put("Status", status);//状态

        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetTaskGroupJob");
            myData.put("TenantId", SharedPreferencesTool.getMStool(TaskPerformActivity.this).getTenantId());
            myData.put("ParentRecordId", getIntent().getStringExtra("RecordId"));//责任人
            myData.put("Status", status);//状态

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("************",myData.toString());


        //Log.e("params", params.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(TaskPerformActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                // Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list_data.clear();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String RecordId=jData.getString("RecordId");//问题ID
                        String ClassId=jData.getString("ClassId");//记录ID
                        String WorkShopId=jData.getString("WorkShopId");//发现人ID
                        String LiableUserName=jData.getString("LiableUserName");//巡线发起人
                        String PatrolDate=jData.getString("PatrolDate").replaceAll("T"," ");//巡线时间
                        String SubjectName=jData.getString("SubjectName");//巡线主题
                        String WorkShopName=jData.getString("WorkShopName");//车间
                        String PatrolUserName=jData.getString("PatrolUserName");//组长
                        String GroupUsers=jData.getString("GroupUsers");//组员
                        int Status=jData.getInt("Status");
                        GroupTaskInfo info=new GroupTaskInfo(RecordId,ClassId,WorkShopId,LiableUserName,PatrolDate,SubjectName,WorkShopName,PatrolUserName,GroupUsers,type);
                        list_data.add(info);

                    }

                    GroupTaskAdapter adapter=new GroupTaskAdapter(TaskPerformActivity.this,list_data);
                    group_task_listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        titleList = new ArrayList<String>();
        titleList.add("未处理");
        titleList.add("进行中");
        titleList.add("已处理");
        hs_activity_tabbar = (HorizontalScrollView) this.findViewById(R.id.hs_activity_tabbar);
        ll_activity_tabbar_content = (LinearLayout) this.findViewById(R.id.ll_activity_tabbar_content);
        //选项卡布局
        myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        ll_activity_tabbar_content.addView(myRadioGroup);
        for (int i = 0; i < titleList.size(); i++) {
            channel = titleList.get(i);
            RadioButton radio = new RadioButton(this);
            radio.setButtonDrawable(android.R.color.transparent);
            radio.setBackgroundResource(R.drawable.radiobtn_selector);
            ColorStateList csl = getResources().getColorStateList(R.color.radiobtn_text_color);
            radio.setTextColor(csl);
            WindowManager manager = this.getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            int w = outMetrics.heightPixels;
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams((int) SizeHelper.dp2px(this, w / 6), ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setTextSize(15);
            radio.setGravity(Gravity.CENTER);
            radio.setText(channel);
            radio.setTag(channel);
            myRadioGroup.addView(radio);
            myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int radioButtonId = group.getCheckedRadioButtonId();
                    //根据ID获取RadioButton的实例
                    RadioButton rb = (RadioButton) findViewById(radioButtonId);
                    channel = (String) rb.getTag();
                    mCurrentCheckedRadioLeft = rb.getLeft();//更新当前按钮距离左边的距离
                    int width=(int) SizeHelper.dp2px(TaskPerformActivity.this, 150);
                    hs_activity_tabbar.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);
                    if(channel.equals("未处理")){
                        if(!type.equals("未处理")){
                            //加载未处理数据
                            type="未处理";
                            initData("0");
                        }



                    }else if(channel.equals("进行中")){
                        //加载待审核数据
                        type="进行中";
                        initData("2");

                    }else if(channel.equals("已处理")){
                        //加载已通过数据
                        type="已处理";
                        initData("5");

                    }


                }
            });
            //设定默认被选中的选项卡为第一项
            if (!titleList.isEmpty()) {
                myRadioGroup.check(myRadioGroup.getChildAt(0).getId());
            }

        }
        lay_back=(LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        group_task_listview=(ListView) findViewById(R.id.group_task_listview);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            default:

                break;



        }
    }
}
