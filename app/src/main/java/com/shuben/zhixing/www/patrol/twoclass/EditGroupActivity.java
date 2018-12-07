package com.shuben.zhixing.www.patrol.twoclass;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
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
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.wxx.net.HttpResult;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.push.PushMessageModel;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class EditGroupActivity extends BaseActvity implements View.OnClickListener{
    private LinearLayout lay_back;
    private   Intent intent;
    private List<String> s_list02;
    private List<String> s_listId02;
    private RelativeLayout lay_s01,lay_s02;
    private TextView bnt_commit,tx_leader,tx_member,tx_title;

    private NiceSpinner niceSpinner02;

    private Map<String,String> UserMap=new HashMap<String,String>();//组员
    private Map<String,String> LeaderMap=new HashMap<String,String>();//组长

    private int mPosition01=0;
    private int mPosition02=0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_group;
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

    private void initData() {
        loadWorkShop();//巡线车间
    }

    private void loadWorkShop() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetWorkShopList");
        params.put("TenantId", SharedPreferencesTool.getMStool(EditGroupActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");

        JSONObject myData=new JSONObject();

        try {
            myData.put("AppCode", "EPS");
            myData.put("ApiCode", "GetWorkShopList");
            myData.put("TenantId", SharedPreferencesTool.getMStool(EditGroupActivity.this).getTenantId());
            myData.put("page", "1");
            myData.put("rows", "20");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*********",myData.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(EditGroupActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                // Log.e("KKKKKKKK", " " + jsonobj.to);
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    s_list02.clear();
                    s_listId02.clear();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        s_list02.add(jData.getString("WorkShopName"));
                        s_listId02.add(jData.getString("WorkShopId"));



                    }
                    niceSpinner02.attachDataSource(s_list02);

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


    private void initView() {
        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        lay_s01=(RelativeLayout)findViewById(R.id.lay_s01);
        lay_s02=(RelativeLayout)findViewById(R.id.lay_s02);
        bnt_commit=(TextView) findViewById(R.id.bnt_commit);
        lay_s01.setOnClickListener(this);
        lay_s02.setOnClickListener(this);
        bnt_commit.setOnClickListener(this);

        tx_leader=(TextView) findViewById(R.id.tx_leader);
        tx_member=(TextView) findViewById(R.id.tx_member);
        tx_title=(TextView) findViewById(R.id.tx_title);
        tx_title.setText(getIntent().getStringExtra("SubjectName"));

        niceSpinner02 = (NiceSpinner) findViewById(R.id.nice_spinner02);
        niceSpinner02.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition02=position;
            }
        });



        niceSpinner02.setTextColor(Color.rgb(22,155,213));
        niceSpinner02.setTextSize(TypedValue.COMPLEX_UNIT_PX,24); //
        s_list02=new ArrayList<String>();
        s_listId02=new ArrayList<String>();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.lay_s01:
                intent=new Intent();
                intent.setClass(EditGroupActivity.this, SignUserActivity.class);
                intent.putExtra("Title", "候选组长");
                intent.putExtra("RecordId", getIntent().getStringExtra("RecordId"));
                startActivityForResult(intent,UrlUtil.EditGroupActivity_RequstCode01);
                break;
            case R.id.lay_s02:
                if(tx_leader.getText().toString().equals("")){
                    Toast.makeText(EditGroupActivity.this,"请先选择组长",Toast.LENGTH_SHORT).show();
                }else{
                    intent=new Intent();
                    intent.setClass(EditGroupActivity.this, SignUserActivity.class);
                    intent.putExtra("Title", "候选组员");
                    intent.putExtra("RecordId", getIntent().getStringExtra("RecordId"));
                    intent.putExtra("leaderId", LeaderMap.get(tx_leader.getText().toString()));
                    startActivityForResult(intent,UrlUtil.EditGroupActivity_RequstCode02);
                }

                break;
            case R.id.bnt_commit:
                if(tx_member.getText().toString().equals("")||tx_leader.getText().toString().equals("")){
                    Toast.makeText(EditGroupActivity.this,"请检查数据是否输入完整",Toast.LENGTH_SHORT).show();
                }else{
                    commit();
                }

                break;


            default:

            break;



        }
    }

    private void commit() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditGroup");
        params.put("TenantId", SharedPreferencesTool.getMStool(EditGroupActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("GroupId", "");
        params.put("WorkShopId", s_listId02.get(mPosition02));
        params.put("SubjectId", getIntent().getStringExtra("SubjectId"));

        JSONArray groupUsers=new JSONArray();
        JSONObject jsonUser=new JSONObject();
        try {
            jsonUser.put("UserId",LeaderMap.get(tx_leader.getText().toString()));
            jsonUser.put("IsGroupLeader",1);
            groupUsers.put(0,jsonUser);
            String member=UserMap.get(tx_member.getText().toString());
            List<String> result =new ArrayList<String>();

            StringTokenizer st=new StringTokenizer(member,",");
            while(st.hasMoreTokens()){
                result.add(st.nextToken());
            }


            for(int i=0;i<result.size();i++){
                JSONObject obj=new JSONObject();
                obj.put("UserId",result.get(i));
                obj.put("IsGroupLeader",0);
                groupUsers.put(i+1,obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("GroupUsers",groupUsers.toString());



        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditGroup");
            data.put("TenantId", SharedPreferencesTool.getMStool(EditGroupActivity.this).getTenantId());
            data.put("RecordId", getIntent().getStringExtra("RecordId"));
            data.put("GroupId", "");
            data.put("WorkShopId", s_listId02.get(mPosition02));
            data.put("SubjectId", getIntent().getStringExtra("SubjectId"));
            data.put("GroupUsers",groupUsers);

            Log.e("***************",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(EditGroupActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(EditGroupActivity.this,"添加分组成功",Toast.LENGTH_SHORT).show();
                        String  msg="人员分组";
                        String from=SharedPreferencesTool.getMStool(EditGroupActivity.this).getUserId();
                        String receivers=   LeaderMap.get(tx_leader.getText().toString());
                        PushMessageModel push=new PushMessageModel(msg,from,receivers);
                        push.getSource(new Function1<HttpResult<String>, Unit>() {
                            @Override
                            public Unit invoke(HttpResult<String> stringHttpResult) {
                                if (stringHttpResult.getSuccess()){
                                    //服务器响应成功
                                    stringHttpResult.getData();
                                }else {
                                    Toast.makeText(EditGroupActivity.this, stringHttpResult.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                return null;
                            }
                        });


                        intent=new Intent();

                        setResult(UrlUtil.AddGroupActivity_RequstCode,intent);
                        finish();
                    }else{
                        Toast.makeText(EditGroupActivity.this,message,Toast.LENGTH_SHORT).show();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (null != data) {
            String userName = data.getStringExtra("userName");
            String userId = data.getStringExtra("userId");
            //获取到客户的姓名和紧急催单的供应商
            if (requestCode == UrlUtil.EditGroupActivity_RequstCode01) {
                if (resultCode == UrlUtil.SignUserActivity_ResultCode) {
                    if (userName != null) {
                        LeaderMap.put(userName, userId);
                        tx_leader.setText(userName);
                    }
                }
            }else  if (requestCode == UrlUtil.EditGroupActivity_RequstCode02) {
                if (resultCode == UrlUtil.SignUserActivity_ResultCode) {
                    if (userName != null) {
                        UserMap.put(userName, userId);
                        tx_member.setText(userName);
                    }
                }
            }


        }


    }}
