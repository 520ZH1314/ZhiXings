package com.shuben.zhixing.www.patrol.twoclass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.StandardAdapter;
import com.shuben.zhixing.www.patrol.bean.StandardInfo;
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

public class TwoFindActivity extends BaseActvity implements View.OnClickListener{
    private HorizontalScrollView hs_activity_tabbar;
    private RadioGroup myRadioGroup;
    private List<String> titleList;
    private LinearLayout ll_activity_tabbar_content;
    private RelativeLayout search_lay;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private String channel;
    private LinearLayout lay_back;
    private ListView standar_listview;
    private LoadingDailog dialog;//加载动画
    private String  type="人";
    private List<StandardInfo> list_info=new ArrayList<StandardInfo>();
    private List<String> list_url=new ArrayList<String>();

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private int screenWidth = 0; //屏幕宽度

    private TextView tx_date,tx_title,tx_workShop,tx_leader,tx_close,tx_user;


    @Override
    public int getLayoutId() {
        return R.layout.activity_two_find;
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
        initGroup();
        initData();
    }

    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(TwoFindActivity.this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetRecordDetail");
        params.put("TenantId", SharedPreferencesTool.getMStool(TwoFindActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("ItemType", type);

        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetRecordDetail");
            myData.put("TenantId", SharedPreferencesTool.getMStool(TwoFindActivity.this).getTenantId());
            myData.put("RecordId", getIntent().getStringExtra("RecordId"));
            myData.put("ItemType", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("**************",myData.toString());



        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(TwoFindActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    tx_date.setText(jsonobj.getString("PatrolDate").replaceAll("T"," "));//巡线时间
                    tx_title.setText(jsonobj.getString("SubjectName")); //巡线主题
                    tx_workShop.setText(jsonobj.getString("WorkShopName"));//巡线车间

                    tx_leader.setText(jsonobj.getString("PatrolUserName"));//巡线组长
                    tx_user.setText(jsonobj.getString("LocaleManagerName"));
                    String RecordId=getIntent().getStringExtra("RecordId");
                    String WorkShopId=jsonobj.getString("WorkShopId");
                    JSONArray jArray=jsonobj.getJSONArray("StandardItem ");
                    JSONObject jData;
                    JSONObject jphoto;
                    list_info.clear();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String ClassId=getIntent().getStringExtra("ClassId");
                        //String SubjectId=jData.getString("SubjectId");
                        //String CreateTime=jData.getString("CreateTime");
                        // String TenantId=jData.getString("TenantId");
                        //String ClassName=jData.getString("ClassName");
                        //String SubjectName=jData.getString("SubjectName");
                        String StandardItemId=jData.getString("StandardItemId");
                        String ItemName=jData.getString("ItemName");
                        String ItemType=jData.getString("ItemType");
                        String ProblemCount=jData.getString("ProblemCount");
                        String HighLightCount=jData.getString("HighLightCount");
                        JSONArray StandardPhotos=jData.getJSONArray("StandardPhotos");
                        String FilePath="";
                        for(int j=0;j<StandardPhotos.length();j++){
                            jphoto=StandardPhotos.getJSONObject(j);
                            if (!FilePath.equals("")){
                                FilePath += "#";
                            }
                            FilePath += jphoto.getString("FilePath");
                        }

                        StandardInfo standardInfo=new StandardInfo(RecordId,StandardItemId,ItemName,ProblemCount, HighLightCount,ItemType,FilePath,WorkShopId,ClassId);
                        list_info.add(standardInfo);

                    }

                    StandardAdapter adapter=new StandardAdapter(TwoFindActivity.this,list_info);
                    standar_listview.setAdapter(adapter);
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
        list_info.clear();
        requestQueue.add(newMissRequest);
    }
    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();

        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        screenWidth = dm.widthPixels;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        titleList = new ArrayList<String>();
        titleList.add("人");
        titleList.add("机");
        titleList.add("料");
        titleList.add("法");
        titleList.add("环");
        standar_listview=(ListView) findViewById(R.id.standar_listview);
        lay_back=(LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        tx_date=(TextView)findViewById(R.id.tx_date);
        tx_title=(TextView)findViewById(R.id.tx_item03);
        tx_workShop=(TextView)findViewById(R.id.tx_item04);
        tx_leader=(TextView)findViewById(R.id.tx_item05);
        tx_close=(TextView)findViewById(R.id.tx_close);
        tx_close.setOnClickListener(this);
        tx_user=(TextView)findViewById(R.id.tx_user);





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
            int w = SysUtils.getScreenWidth(TwoFindActivity.this)/5;
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
                int width=(int) SizeHelper.dp2px(TwoFindActivity.this, 150);
                hs_activity_tabbar.smoothScrollTo((int) mCurrentCheckedRadioLeft - width, 0);
                if(channel.equals("人")){
                    //加载未处理数据
                    if(!type.equals("人")){
                        type="人";
                        list_info.clear();
                        list_url.clear();
                        initData();
                    }

                }else if(channel.equals("机")){
                    //加载待审核数据
                    type="机";
                    list_info.clear();
                    list_url.clear();
                    initData();

                } else if(channel.equals("料")){
                    //加载待审核数据
                    type="料";
                    list_info.clear();
                    list_url.clear();
                    initData();

                }else if(channel.equals("法")){
                    //加载待审核数据
                    type="法";
                    list_info.clear();
                    list_url.clear();
                    initData();

                }else if(channel.equals("环")){
                    //加载待审核数据
                    type="环";
                    list_info.clear();
                    list_url.clear();
                    initData();

                }


            }
        });
        //设定默认被选中的选项卡为第一项
        if (!titleList.isEmpty()) {
            myRadioGroup.check(myRadioGroup.getChildAt(0).getId());
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            if (requestCode == UrlUtil.TwoFindActivity_RequstCode01) {
                if (resultCode == UrlUtil.AddQuestionActivity_ResultCode) {
                    initData();
                }
            }else  if (requestCode == UrlUtil.TwoFindActivity_RequstCode02) {
                if (resultCode == UrlUtil.AddLightActivity_ResultCode) {
                    initData();
                }
            }


        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
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
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditPatrolLineEnd");
        params.put("TenantId", SharedPreferencesTool.getMStool(TwoFindActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));


        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditPatrolLineEnd");
            data.put("TenantId", SharedPreferencesTool.getMStool(TwoFindActivity.this).getTenantId());
            data.put("RecordId", getIntent().getStringExtra("RecordId"));

            Log.e("***************",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(TwoFindActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(TwoFindActivity.this,"关闭巡线成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(TwoFindActivity.this,message,Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(TwoFindActivity.this);
        View view = (LinearLayout) TwoFindActivity.this.getLayoutInflater().inflate(R.layout.task, null);
        TextView tx_content=(TextView) view.findViewById(R.id.tx_content);
        tx_content.setText("是否关闭当前巡线任务");
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






}
