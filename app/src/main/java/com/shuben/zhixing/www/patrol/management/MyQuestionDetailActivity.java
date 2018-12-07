package com.shuben.zhixing.www.patrol.management;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.RecordAdapter;
import com.shuben.zhixing.www.patrol.bean.RecordInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyQuestionDetailActivity extends BaseActvity implements View.OnClickListener{
    private LoadingDailog dialog;//加载动画
    private List<RecordInfo> data;
    private ListView mList;
    private RecordAdapter adapter;
    private TextView tx_back;
    private LinearLayout lay01;
    private Button bnt_commit;
    private String Status="";
    private String type="";
    private CheckBox checkBox01,checkBox02;
    private EditText editText2;
    private String IsSupervise="1";

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_question_detail;
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


        mList=(ListView)findViewById(R.id.record_listview);
        tx_back=(TextView) findViewById(R.id.tx_back);
        tx_back.setOnClickListener(this);
        lay01=(LinearLayout) findViewById(R.id.lay01);
        bnt_commit=(Button) findViewById(R.id.bnt_commit);
        bnt_commit.setOnClickListener(this);
        Status=getIntent().getStringExtra("Status");
        type=getIntent().getStringExtra("type");
        if(Status.equals("0")){
            lay01.setVisibility(View.VISIBLE);
            bnt_commit.setText("立即处理");
        }else if(Status.equals("5")&&type.equals("我的审核")){
            lay01.setVisibility(View.VISIBLE);
            bnt_commit.setText("进行审核");
        }


    }
    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetRecordProblem");
        params.put("TenantId", SharedPreferencesTool.getMStool(MyQuestionDetailActivity.this).getTenantId());
        params.put("ProblemId", getIntent().getStringExtra("ProblemId"));
        final JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetRecordProblem");
            myData.put("TenantId", SharedPreferencesTool.getMStool(MyQuestionDetailActivity.this).getTenantId());
            myData.put("ProblemId", getIntent().getStringExtra("ProblemId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("**************",myData.toString());



        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(MyQuestionDetailActivity.this).getIp()+UrlUtil.Url,
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
                                Severity,HandleResult,LiableDeptName,AuditDesc,LiableUserName,
                                DueDate,CompleteDate,Status,FilePath01,FilePath02,AuditResult,Thumb,"问题明细");
                        data.add(recordInfo);

                    }

                    //创建adpter数据
                    adapter = new RecordAdapter(MyQuestionDetailActivity.this,data);
                    //设置adapter
                    mList.setAdapter(adapter);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            if (requestCode == UrlUtil.MyQuestionDetailActivity_RequstCode) {
                if (resultCode == UrlUtil.SolveActivity_ResultCode) {
                    initData();
                    lay01.setVisibility(View.INVISIBLE);
                }
            }


        }

    }



    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.tx_back:
                finish();
                break;
            case R.id.bnt_commit:
                commit();
                break;

            default:

            break;



        }
    }

    private void commit() {
        Intent intent;
        if(bnt_commit.getText().toString().equals("立即处理")){
            intent=new Intent();
            intent.setClass(MyQuestionDetailActivity.this,SolveActivity.class);
            intent.putExtra("ProblemId",getIntent().getStringExtra("ProblemId"));
            startActivityForResult(intent,UrlUtil.MyQuestionDetailActivity_RequstCode);
        }else if(bnt_commit.getText().toString().equals("进行审核")){
            showPopWindow();
        }
    }
    private void showPopWindow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyQuestionDetailActivity.this);
        View view = (LinearLayout)MyQuestionDetailActivity.this. getLayoutInflater().inflate(R.layout.patrol_audit, null);
        builder.setView(view);
        builder.setTitle("我的审核");
         editText2=(EditText) view.findViewById(R.id.editText2);
         checkBox01=(CheckBox) view.findViewById(R.id.checkBox);
         checkBox02=(CheckBox) view.findViewById(R.id.checkBox2);
         checkBox01.setSelected(true);
        checkBox01.setChecked(true);
         checkBox01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox01.setSelected(true);
                checkBox02.setSelected(false);
                checkBox02.setChecked(false);
                IsSupervise="1";

            }
        });
        checkBox02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox01.setSelected(false);
                checkBox01.setChecked(false);
                checkBox02.setSelected(true);
                IsSupervise="0";
            }
        });

        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                audit(editText2.getText().toString(),IsSupervise);
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

    private void audit(String AuditDesc,String AuditResult) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditRecordProblemAudit");
        params.put("ProblemId", getIntent().getStringExtra("ProblemId"));
        params.put("AuditDesc", AuditDesc);//审核内容
        params.put("AuditResult", AuditResult);//审核结果

        JSONObject data=new JSONObject();
        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditRecordProblemAudit");
            data.put("ProblemId", getIntent().getStringExtra("ProblemId"));
            data.put("AuditDesc", AuditDesc);//审核内容
            data.put("AuditResult", AuditResult);//审核结果



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****问题审核******",data.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(MyQuestionDetailActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    final String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toast.makeText(MyQuestionDetailActivity.this,"问题审核完成",Toast.LENGTH_SHORT).show();
                        lay01.setVisibility(View.INVISIBLE);
                        initData();


                    }else{
                        Toast.makeText(MyQuestionDetailActivity.this,message,Toast.LENGTH_SHORT).show();
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
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        requestQueue.add(newMissRequest);
    }


}
