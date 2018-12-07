package com.shuben.zhixing.www.patrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.twoclass.AddGroupActivity;
import com.shuben.zhixing.www.patrol.twoclass.CallActivity;
import com.shuben.zhixing.www.patrol.twoclass.NotificationActivity;
import com.shuben.zhixing.www.patrol.twoclass.TaskActivity;
import com.shuben.zhixing.www.util.CalendarUtil;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoClassActivity extends BaseActvity implements View.OnClickListener{
    private TextView tx_back,tx_patrolDate,tx_workShop,tx_custown,tx_date,tx_Subject;
    private   Intent intent;
    private  EditText ed_place;
    private Button bnt_set,bnt_date01;
    private RelativeLayout m_set_lay01;
    private RelativeLayout m_set_lay02;
    private RelativeLayout m_set_lay03;
    private RelativeLayout m_set_lay04;
    private List<String> s_list01;
    private List<String> s_listId01;
    private NiceSpinner niceSpinner01;
    private  int mPosition01=0;
    private String mSubjectName;
    private String mSubjectId;
    private RelativeLayout lay_back;
    private  ImageView title_back;

    @Override
    public int getLayoutId() {
        return R.layout.activity_two_class;
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
    }

    private void initListner() {
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        bnt_set.setOnClickListener(this);
        m_set_lay01.setOnClickListener(this);
        m_set_lay02.setOnClickListener(this);
        m_set_lay03.setOnClickListener(this);
        m_set_lay04.setOnClickListener(this);

    }

    private void initView() {
          mSubjectName=getIntent().getStringExtra("SubjectName");
          mSubjectId=getIntent().getStringExtra("SubjectId");
         tx_back= (TextView) findViewById(R.id.tx_back);
         lay_back= (RelativeLayout) findViewById(R.id.lay_back);
         title_back= (ImageView) findViewById(R.id.title_back);
         bnt_set=(Button) findViewById(R.id.bnt_set);
        tx_patrolDate= (TextView) findViewById(R.id.tx_patrolDate);
        tx_custown= (TextView) findViewById(R.id.tx_custown);
        tx_workShop= (TextView) findViewById(R.id.tx_workShop);
        tx_Subject= (TextView) findViewById(R.id.tx_Subject);
        tx_patrolDate.setText(getIntent().getStringExtra("PatrolDate").substring(5,getIntent().getStringExtra("PatrolDate").length()));
        tx_custown.setText(getIntent().getStringExtra("Countdown"));
        tx_workShop.setText(getIntent().getStringExtra("MeetingPlace"));
        tx_Subject.setText(getIntent().getStringExtra("SubjectName"));
        m_set_lay01=(RelativeLayout) findViewById(R.id.m_set_lay01);
        m_set_lay02=(RelativeLayout) findViewById(R.id.m_set_lay02);
        m_set_lay03=(RelativeLayout) findViewById(R.id.m_set_lay03);
        m_set_lay04=(RelativeLayout) findViewById(R.id.m_set_lay04);
    }
    private void loadTitle() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetSubjectList");
        params.put("TenantId", SharedPreferencesTool.getMStool(TwoClassActivity.this).getTenantId());

        JSONObject myData=new JSONObject();

        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetSubjectList");
            myData.put("TenantId", SharedPreferencesTool.getMStool(TwoClassActivity.this).getTenantId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*********",myData.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(TwoClassActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    s_list01=new ArrayList<String>();
                    s_listId01=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        s_list01.add(jData.getString("SubjectName"));
                        s_listId01.add(jData.getString("SubjectId"));



                    }
                    niceSpinner01.attachDataSource(s_list01);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_back:
                setResult(UrlUtil.TwoClassActivity_ResultCode,intent);
                finish();
                break;
            case R.id.title_back:
                setResult(UrlUtil.TwoClassActivity_ResultCode,intent);
                finish();
                break;
            case R.id.tx_back:
                setResult(UrlUtil.TwoClassActivity_ResultCode,intent);
                finish();
                break;


            case R.id.m_set_lay01:
                intent=new Intent();
                intent.setClass(TwoClassActivity.this, CallActivity.class);
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                startActivity(intent);
                break;
            case R.id.m_set_lay02:
                intent=new Intent();
                intent.setClass(TwoClassActivity.this, AddGroupActivity.class);
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                intent.putExtra("SubjectId",mSubjectId);
                intent.putExtra("SubjectName",mSubjectName);
                startActivity(intent);
                break;
            case R.id.m_set_lay03:
                intent=new Intent();
                intent.setClass(TwoClassActivity.this,TaskActivity.class);
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                startActivity(intent);
                break;
            case R.id.m_set_lay04:
                intent=new Intent();
                intent.setClass(TwoClassActivity.this, NotificationActivity.class);
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                intent.putExtra("class","two");
                startActivity(intent);
                break;
            case R.id.bnt_set:
                showPopWindow();
                break;

            default:
                break;
        }
    }

    private void showPopWindow() {

        AlertDialog.Builder builder = new AlertDialog.Builder(TwoClassActivity.this);
        View view = (LinearLayout)TwoClassActivity.this. getLayoutInflater().inflate(R.layout.patrol_class_set, null);
        builder.setView(view);
        builder.setTitle("设置");
        tx_date=(TextView) view.findViewById(R.id.tx_date01);
        bnt_date01=(Button) view.findViewById(R.id.bnt_calendar01);
        ed_place=(EditText)view.findViewById(R.id.ed_place);
        ed_place.setText(tx_workShop.getText().toString());
        tx_date.setText(DateUtil.getInstance().getDateOfToDay02());
        bnt_date01=(Button) view.findViewById(R.id.bnt_calendar01);
        bnt_date01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        niceSpinner01= (NiceSpinner) view.findViewById(R.id.nice_spinner01);
        niceSpinner01.setTextColor(Color.rgb(22,155,213));
        //Toast.makeText(PatrolTaskActivity.this,R.dimen.x12+"",Toast.LENGTH_SHORT).show();

        //niceSpinner01.setTextSize(TypedValue.COMPLEX_UNIT_PX,24); //
        niceSpinner01.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition01=position;
            }
        });
        loadTitle();

        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                dialog.cancel();
                set(tx_date.getText().toString(),ed_place.getText().toString(),s_listId01.get(mPosition01),s_list01.get(mPosition01));



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

    private void set(final String date, final String palce,final String SubjectId,final String SubjectName) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditPatrolSetting");
        params.put("TenantId", SharedPreferencesTool.getMStool(TwoClassActivity.this).getTenantId());
        params.put("RecordId",getIntent().getStringExtra("RecordId"));
        params.put("PatrolDate", date);//开始时间
        params.put("MeetingPlace", palce);//集合地点
        params.put("SubjectId", SubjectId);//主题
        JSONObject data=new JSONObject();
        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditPatrolSetting");
            data.put("TenantId", SharedPreferencesTool.getMStool(TwoClassActivity.this).getTenantId());
            data.put("RecordId",getIntent().getStringExtra("RecordId"));
            data.put("PatrolDate", date);//开始时间
            data.put("MeetingPlace", palce);//集合地点
            data.put("SubjectId", SubjectId);//主题

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****巡线设置*****",data.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(TwoClassActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status=jsonobj.getString("status");
                    final String message=jsonobj.getString("message");
                    if(status.equals("true")){
                        tx_patrolDate.setText(date);
                        tx_custown.setText(DateUtil.getCountdown(date));
                        tx_workShop.setText(palce);
                        tx_Subject.setText(SubjectName);
                        mSubjectName=SubjectName;
                        mSubjectId=SubjectId;
                        Toast.makeText(TwoClassActivity.this,"提交问题成功",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(TwoClassActivity.this,message,Toast.LENGTH_SHORT).show();
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

    private void setDate() {
        CalendarUtil.getInstance().setDate(TwoClassActivity.this,tx_date);
    }
}
