package com.shuben.zhixing.www.patrol.twoclass;

import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.FindAdapter;
import com.shuben.zhixing.www.patrol.bean.FindInfo;
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

public class FindQuestionActivity extends BaseActvity implements View.OnClickListener{
    private LoadingDailog dialog;
    private List<FindInfo> data_list=new ArrayList<FindInfo>();
    private ListView listview_find;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private int mScreenWidth;
    private int mScreenHeight;
    private List<String> list_url=new ArrayList<String>();
    private LinearLayout lay_back;

    @Override
    public int getLayoutId() {
        return R.layout.activity_find_question;
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
        initData();
    }


    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        listview_find=(ListView)findViewById(R.id.listview_find);


        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        mScreenWidth = dm.widthPixels;
        mScreenHeight=dm.heightPixels;
        lay_back=(LinearLayout)findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);

    }
    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetRecordProblem");
        params.put("TenantId", SharedPreferencesTool.getMStool(FindQuestionActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("StandardItemId", getIntent().getStringExtra("StandardItemId"));
        JSONObject dat=new JSONObject();
        try {
            dat.put("AppCode", "LinePatrol");
            dat.put("ApiCode", "GetRecordProblem");
            dat.put("TenantId", SharedPreferencesTool.getMStool(FindQuestionActivity.this).getTenantId());
            dat.put("RecordId", getIntent().getStringExtra("RecordId"));
            dat.put("StandardItemId", getIntent().getStringExtra("StandardItemId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*********",dat.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(FindQuestionActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
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
                        String findUser=SharedPreferencesTool.getMStool(FindQuestionActivity.this).getUserName();//发现人
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
                    FindAdapter adapter=new FindAdapter(FindQuestionActivity.this,data_list,"问题",dialog);
                    listview_find.setAdapter(adapter);
                    new ScrollListview(listview_find);
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
                finish();
                break;
            default:

            break;



        }
    }
}
