package com.shuben.zhixing.www.patrol.oneclass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.shuben.zhixing.www.patrol.adapter.FindAdapter;
import com.shuben.zhixing.www.patrol.bean.FindInfo;
import com.shuben.zhixing.www.util.DateUtil;
import com.shuben.zhixing.www.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.gudong.drawable.OneDrawable;

import static com.nightonke.boommenu.Util.setDrawable;

public class OneFindActivity extends BaseActvity implements View.OnClickListener{
    private Button bnt_add;
    LinearLayout lay_add;
    private TextView tx_back,tx_find01,tx_find02,tx_add,tx_close;
    private LinearLayout lay_back;
    private ImageView title_back;
    private TextView tx_item01,tx_item02,tx_item03,tx_item04,tx_item05;
    private boolean find=false;
    private  Intent intent;
    private ListView listview_find;
    private List<FindInfo> data_list=new ArrayList<FindInfo>();
    private LoadingDailog dialog;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private int mScreenWidth;
    private int mScreenHeight;
    private List <String >mList=new ArrayList<String>();
    private List <List >mList_list=new ArrayList<List>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_one_find;
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
        initListner();
        initData01();//发现问题
    }

    private void initData01() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetRecordProblem");
        params.put("TenantId", SharedPreferencesTool.getMStool(OneFindActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        JSONObject dat=new JSONObject();
        try {
            dat.put("AppCode", "LinePatrol");
            dat.put("ApiCode", "GetRecordProblem");
            dat.put("TenantId", SharedPreferencesTool.getMStool(OneFindActivity.this).getTenantId());
            dat.put("RecordId", getIntent().getStringExtra("RecordId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("****一级巡线发现问题*****",dat.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(OneFindActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK发现问题KKKK", " " + jsonobj.toString());
                dialog.dismiss();
                data_list.clear();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    JSONObject jFile;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String ProblemId=jData.getString("ProblemId");//问题描述
                        String ProblemDesc=jData.getString("ProblemDesc");//问题描述
                        String findUser=SharedPreferencesTool.getMStool(OneFindActivity.this).getUserName();//发现人
                        String LiableDeptName=jData.getString("LiableDeptName");//责任部门
                        double Severity=jData.getDouble("Severity");//严重程度
                        String LiableUserName=jData.getString("LiableUserName");//问题责任人
                        String DueDate=jData.getString("DueDate").replaceAll("T"," ");//问题要求完成时间
                            int Thumb=jData.getInt("Thumb");




                        JSONArray FileList=jData.getJSONArray("FileList");
                        String FilePath="";
                        for(int j=0;j<FileList.length();j++){
                            jFile=FileList.getJSONObject(j);

                            if (!FilePath.equals("")){
                                FilePath += "#";
                            }
                            FilePath += jFile.getString("FilePath"); ;

                        }
                        FindInfo f=new FindInfo(ProblemDesc,findUser,LiableDeptName,Severity,LiableUserName,DueDate,ProblemId,FilePath,Thumb,"发现问题");
                        data_list.add(f);
                    }
                    FindAdapter adapter=new FindAdapter(OneFindActivity.this,data_list,"问题",dialog);
                    listview_find.setAdapter(adapter);
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
    private void initData02() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetRecordHighlight");
        params.put("TenantId", SharedPreferencesTool.getMStool(OneFindActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        JSONObject dat=new JSONObject();


        try {
            dat.put("AppCode", "LinePatrol");
            dat.put("ApiCode", "GetRecordHighlight");
            dat.put("TenantId", SharedPreferencesTool.getMStool(OneFindActivity.this).getTenantId());
            dat.put("RecordId", getIntent().getStringExtra("RecordId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****一级巡线发现亮点****",dat.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(OneFindActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK发现亮点KKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    JSONObject jFile;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);

                        String HighlightId=jData.getString("HighlightId");//问题描述
                        String ProblemDesc=jData.getString("HighlightDesc");//问题描述
                        String findUser=SharedPreferencesTool.getMStool(OneFindActivity.this).getUserName();
                        //String WorkShopName=SharedPreferencesTool.getMStool(OneFindActivity.this).getUserName();//发现人
                        String LiableDeptName=jData.getString("ImproveDeptName");//改善部门
                        int Score=jData.getInt("Score");//严重程度
                        String ImproveUserName=jData.getString("ImproveUserName");//改善人
                        String DueDate=jData.getString("CreateTime").replaceAll("T"," ");//发现时间
                        int Thumb=jData.getInt("Thumb");

                        JSONArray FileList=jData.getJSONArray("FileList");
                        String FilePath="";
                        for(int j=0;j<FileList.length();j++){
                            jFile=FileList.getJSONObject(j);

                            if (!FilePath.equals("")){
                                FilePath += "#";
                            }
                            FilePath += jFile.getString("FilePath"); ;

                        }

                        FindInfo f=new FindInfo(ProblemDesc,findUser,LiableDeptName,Score,ImproveUserName,DueDate,HighlightId,FilePath,Thumb,"发现亮点");
                        data_list.add(f);
                    }
                    FindAdapter adapter=new FindAdapter(OneFindActivity.this,data_list,"亮点",dialog);
                    listview_find.setAdapter(adapter);

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
    private void initListner() {
        tx_find01.setOnClickListener(this);
        tx_find02.setOnClickListener(this);
        bnt_add.setOnClickListener(this);
        lay_add.setOnClickListener(this);

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
        mScreenWidth = dm.widthPixels;
        mScreenHeight=dm.heightPixels;

        options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.mipmap.ic_stub)
		.showImageForEmptyUri(R.mipmap.ic_empty)
		.showImageOnFail(R.mipmap.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();



        tx_close=(TextView)findViewById(R.id.tx_close);
        tx_close.setOnClickListener(this);

        tx_back= (TextView) findViewById(R.id.tx_back);
        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        title_back= (ImageView) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        tx_add=(TextView) findViewById(R.id.tx_add);
        tx_find01=(TextView) findViewById(R.id.tx_find01);
        tx_find02=(TextView) findViewById(R.id.tx_find02);
        bnt_add=(Button) findViewById(R.id.bnt_add);
        lay_add=(LinearLayout) findViewById(R.id.lay_add);

        tx_item01=(TextView) findViewById(R.id.tx_item01);
        tx_item02=(TextView) findViewById(R.id.tx_item02);
        tx_item03=(TextView) findViewById(R.id.tx_item03);
        tx_item04=(TextView) findViewById(R.id.tx_item04);
        tx_item05=(TextView) findViewById(R.id.tx_item05);
        tx_item01.setText("巡线单号:"+getIntent().getStringExtra("FormNo"));
        tx_item02.setText(getIntent().getStringExtra("WorkShopName"));
        tx_item03.setText(getIntent().getStringExtra("AreaName"));
        tx_item04.setText(getIntent().getStringExtra("LiableUserName"));
        String Countdown= DateUtil.getCountdown(getIntent().getStringExtra("Countdown"));
        tx_item05.setText(Countdown);
        DateUtil.setCountdownColor(getIntent().getStringExtra("Countdown"),"一级巡线",tx_item05);


        setDrawable(bnt_add, OneDrawable.createBgDrawable(OneFindActivity.this, R.mipmap.add));
        listview_find=(ListView)findViewById(R.id.listview_find);
        new ScrollListview(listview_find);

        if(getIntent().getStringExtra("Status").equals("已完成")){
            bnt_add.setVisibility(View.INVISIBLE);
            tx_add.setVisibility(View.INVISIBLE);
            tx_close.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        data_list.clear();

        if (null != data) {
            if (requestCode == UrlUtil.OneFindActivity_RequstCode01) {
                if (resultCode == UrlUtil.AddQuestionActivity_ResultCode) {
                    initData01();
                }
            }else  if (requestCode == UrlUtil.OneFindActivity_RequstCode02) {
                if (resultCode == UrlUtil.AddLightActivity_ResultCode) {
                    initData02();
                }
            }


        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_add:
                intent=new Intent();
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                intent.putExtra("WorkShopId",getIntent().getStringExtra("WorkShopId"));
                intent.putExtra("AreaId",getIntent().getStringExtra("AreaId"));
                intent.putExtra("LineId",getIntent().getStringExtra("LineId"));
                intent.putExtra("ClassId",getIntent().getStringExtra("ClassId"));
                if(find){
                    intent.setClass(OneFindActivity.this, AddLightActivity.class);
                    startActivityForResult(intent,UrlUtil.OneFindActivity_RequstCode02);
                }else{
                    intent.setClass(OneFindActivity.this, AddQuestionActivity.class);
                    startActivityForResult(intent,UrlUtil.OneFindActivity_RequstCode01);
                }
                break;
            case R.id.bnt_add:
                intent=new Intent();
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                intent.putExtra("WorkShopId",getIntent().getStringExtra("WorkShopId"));
                intent.putExtra("AreaId",getIntent().getStringExtra("AreaId"));
                intent.putExtra("LineId",getIntent().getStringExtra("LineId"));
                intent.putExtra("ClassId",getIntent().getStringExtra("ClassId"));
                if(find){
                    intent.setClass(OneFindActivity.this, AddLightActivity.class);
                    startActivityForResult(intent,UrlUtil.OneFindActivity_RequstCode02);
                }else{
                    intent.setClass(OneFindActivity.this, AddQuestionActivity.class);
                    startActivityForResult(intent,UrlUtil.OneFindActivity_RequstCode01);
                }
                break;
            case R.id.lay_back:
                finish();
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.tx_back:
                finish();
                break;

            case R.id.tx_find01://发现问题
                if(find){
                    tx_find01.setBackgroundResource(R.mipmap.data_leftclick);
                    tx_find02.setBackgroundResource(R.mipmap.data_right);
                    tx_add.setText("添加问题");
                    data_list.clear();
                    initData01();
                }
                find=false;

                break;
            case R.id.tx_find02://发现亮点
                if(!find){
                    tx_find01.setBackgroundResource(R.mipmap.data_left);
                    tx_find02.setBackgroundResource(R.mipmap.data_rightclick);
                    tx_add.setText("添加亮点");
                    data_list.clear();
                    initData02();
                }
                find=true;
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
        params.put("TenantId", SharedPreferencesTool.getMStool(OneFindActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));


        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditPatrolLineEnd");
            data.put("TenantId", SharedPreferencesTool.getMStool(OneFindActivity.this).getTenantId());
            data.put("RecordId", getIntent().getStringExtra("RecordId"));

            Log.e("***************",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(OneFindActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        intent = new Intent();
                        setResult(UrlUtil.OneFindActivity_ResultCode,intent);
                        finish();
                        Toast.makeText(OneFindActivity.this,"关闭巡线成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(OneFindActivity.this,message,Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(OneFindActivity.this);
        View view = (LinearLayout) OneFindActivity.this.getLayoutInflater().inflate(R.layout.task, null);
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
