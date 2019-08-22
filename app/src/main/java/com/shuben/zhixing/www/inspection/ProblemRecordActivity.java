package com.shuben.zhixing.www.inspection;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inspection.adapter.ProblemDetailAdapter;
import com.shuben.zhixing.www.inspection.bean.ProblemDetailInfo;
import com.shuben.zhixing.www.inspection.fragment.SolvePActivity;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProblemRecordActivity extends BaseActvity implements View.OnClickListener{
    private LoadingDailog dialog;//加载动画
    private List<ProblemDetailInfo> data;
    private ListView mList;
    private ProblemDetailAdapter adapter;
    private LinearLayout lay01;
    private Button bnt_commit;
    private String Status="";
    private String type="";
    private CheckBox checkBox01,checkBox02;
    private EditText editText2;
    private String IsSupervise="1";
    private RelativeLayout lay_back;
    private  ImageView title_back;
    private TextView tx_back;

    @Override
    public int getLayoutId() {
        return R.layout.activity_problem_record;
    }
    int des = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        des = getIntent().getIntExtra("des",0);
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
        tx_back= (TextView) findViewById(R.id.tx_back);
        lay_back= (RelativeLayout) findViewById(R.id.lay_back);
        title_back= (ImageView) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        lay01=(LinearLayout) findViewById(R.id.lay01);
        bnt_commit=(Button) findViewById(R.id.bnt_commit);
        bnt_commit.setOnClickListener(this);
        Status=getIntent().getStringExtra("Status");
        if(Status.equals("未开始")){
          /*  if(SharedPreferencesTool.getMStool(ProblemRecordActivity.this).getUserName().equals(getIntent().getStringExtra("LiableUserName"))){
                lay01.setVisibility(View.VISIBLE);

            }else{
                lay01.setVisibility(View.INVISIBLE);
            }*/
            P.c(des+"的值");
          if(des==1){
              lay01.setVisibility(View.INVISIBLE);
          }else{
              bnt_commit.setText("立即处理");
              lay01.setVisibility(View.VISIBLE);
          }

        }else {
            lay01.setVisibility(View.INVISIBLE);
        }
       // bnt_commit.setText("立即处理");

    }
    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "PatrolInspection");
        params.put("ApiCode", "GetRecordProblem");
        params.put("TenantId", SharedPreferencesTool.getMStool(ProblemRecordActivity.this).getTenantId());
        params.put("ProblemId", getIntent().getStringExtra("ProblemId"));
        final JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "PatrolInspection");
            myData.put("ApiCode", "GetRecordProblem");
            myData.put("TenantId", SharedPreferencesTool.getMStool(ProblemRecordActivity.this).getTenantId());
            myData.put("ProblemId", getIntent().getStringExtra("ProblemId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*******问题明细*******",myData.toString());



        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(ProblemRecordActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKK问题明细KKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    int total=jsonobj.getInt("total");
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    JSONObject jData01;
                    JSONObject jData02;
                    data=new ArrayList<ProblemDetailInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String ProblemNo=jData.getString("ProblemNo");//问题单号
                        String ProblemId=jData.getString("ProblemId");//问题单号
                        String PatrolTaskId=jData.getString("PatrolTaskId");//巡线任务记录ID
                        String ProblemDesc=jData.getString("ProblemDesc");//问题描述
                        String Measures=jData.getString("Measures"); //责任部门ID
                        String LiableDeptName=jData.getString("LiableDeptName");//责任部门名称
                        String LiableUserName=jData.getString("LiableUserName");//责任人ID
                        String PatrolUserName=jData.getString("PatrolUserName");//责任人ID
                        String ItemType=jData.getString("ItemType"); //责任人姓名
                        String DueDate=jData.getString("DueDate").replaceAll("T"," ");//要求完成时间
                        String CompleteDate=jData.getString("CompleteDate").replaceAll("T"," ");//实际完成时间
                        String ProductName="";//jData.getString("ProductName");
                        String Status=jData.getString("Status");//问题状态，－10//审核未通过，－5//未完成，0//未启动，2//进行中，5//已处理（待审核），10//已审核

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

                        ProblemDetailInfo info=new ProblemDetailInfo(ProblemNo,ProblemId,PatrolTaskId,ProblemDesc,Measures,LiableDeptName,LiableUserName,PatrolUserName,ItemType,DueDate,CompleteDate,Status,ProductName,FilePath01,FilePath02);
                        data.add(info);

                    }

                    //创建adpter数据
                    adapter = new ProblemDetailAdapter(ProblemRecordActivity.this,data);
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
            if (requestCode == UrlUtil.ProblemRecordActivity_RequstCode) {
                if (resultCode == UrlUtil.SolvePActivity_ResultCode) {
                    initData();
                    lay01.setVisibility(View.INVISIBLE);
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
            case R.id.tx_back:
                finish();
                break;
            case R.id.title_back:
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
            intent.setClass(ProblemRecordActivity.this,SolvePActivity.class);
            intent.putExtra("ProblemId",getIntent().getStringExtra("ProblemId"));
            startActivityForResult(intent,UrlUtil.ProblemRecordActivity_RequstCode);
        }else if(bnt_commit.getText().toString().equals("进行审核")){
            showPopWindow();
        }
    }
    private void showPopWindow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProblemRecordActivity.this);
        View view = (LinearLayout)ProblemRecordActivity.this. getLayoutInflater().inflate(R.layout.patrol_audit, null);
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
                Request.Method.POST, SharedPreferencesTool.getMStool(ProblemRecordActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    final String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        Toast.makeText(ProblemRecordActivity.this,"问题审核完成",Toast.LENGTH_SHORT).show();
                        lay01.setVisibility(View.INVISIBLE);
                        initData();


                    }else{
                        Toast.makeText(ProblemRecordActivity.this,message,Toast.LENGTH_SHORT).show();
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
