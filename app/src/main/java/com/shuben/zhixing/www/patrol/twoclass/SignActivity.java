package com.shuben.zhixing.www.patrol.twoclass;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
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
import com.shuben.zhixing.www.patrol.adapter.SignAdapter;
import com.shuben.zhixing.www.patrol.bean.SignInfo;
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

public class SignActivity extends BaseActvity implements View.OnClickListener{
    private LinearLayout lay_back;
    private GridView sign_gridview;
    private List<SignInfo> sign_list=new ArrayList<SignInfo>();
    private LoadingDailog dialog;//加载动画

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign;
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetUserCallIn");
        params.put("TenantId", SharedPreferencesTool.getMStool(SignActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));

        JSONObject myData=new JSONObject();

        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetUserCallIn");
            myData.put("TenantId", SharedPreferencesTool.getMStool(SignActivity.this).getTenantId());
            myData.put("RecordId", getIntent().getStringExtra("RecordId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*********",myData.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST,SharedPreferencesTool.getMStool(SignActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                // Log.e("KKKKKKKK", " " + jsonobj.to);
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    sign_list.clear();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);


                        String RecordId =jData.getString("RecordId");
                        String PartInUserId =jData.getString("PartInUserId");
                        String PartInDeptId =jData.getString("PartInDeptId");
                        String PartInDeptName =jData.getString("PartInDeptName");
                        String PartInUserName =jData.getString("PartInUserName");
                        String SignStatus =jData.getString("SignStatus");

                        SignInfo signInfo=new SignInfo(RecordId,PartInUserId,PartInDeptId,PartInDeptName,PartInUserName,SignStatus);
                        sign_list.add(signInfo);
                    }

                    SignAdapter signAdapter=new SignAdapter(SignActivity.this,sign_list);
                    sign_gridview.setAdapter(signAdapter);
                    new ScrollListview(sign_gridview);

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
        lay_back=(LinearLayout)findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        sign_gridview=(GridView)findViewById(R.id.sign_gridview);
        sign_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(sign_list.get(position).getItem06().equals("0")){
                    //Toast.makeText(SignActivity.this,"position:"+position,Toast.LENGTH_SHORT).show();
                    sign(sign_list.get(position).getItem02(),"1");
                }else  if(sign_list.get(position).getItem06().equals("1")){
                    sign(sign_list.get(position).getItem02(),"2");
                }else  if(sign_list.get(position).getItem06().equals("2")){
                    sign(sign_list.get(position).getItem02(),"3");
                }else  if(sign_list.get(position).getItem06().equals("3")){
                    sign(sign_list.get(position).getItem02(),"0");
                }
                //Toast.makeText(SignActivity.this,"position:"+position,Toast.LENGTH_SHORT).show();

            }
        });

        /*sign_gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(sign_list.get(position).getItem06().equals("0")){
                    Toast.makeText(SignActivity.this,"position:"+position,Toast.LENGTH_SHORT).show();
                    sign(sign_list.get(position).getItem02(),"1");
                }else  if(sign_list.get(position).getItem06().equals("1")){
                    sign(sign_list.get(position).getItem02(),"2");
                }
                Toast.makeText(SignActivity.this,"position:"+position,Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
        sign_gridview.setHorizontalSpacing(10);
        sign_gridview.setVerticalSpacing(10);

    }

    private void sign(String userId,String signStatus) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditUserSignIn");
        params.put("TenantId", SharedPreferencesTool.getMStool(SignActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("PartInUserId", userId);
        params.put("SignStatus", signStatus);


        JSONObject data =new JSONObject();

        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditUserSignIn");
            data.put("TenantId", SharedPreferencesTool.getMStool(SignActivity.this).getTenantId());
            data.put("RecordId", getIntent().getStringExtra("RecordId"));
            data.put("PartInUserId", userId);

            Log.e("***************",data.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalUserId = userId;
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(SignActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String  status=jsonobj.getString("status");
                    String  message=jsonobj.getString("message");
                    if("true".equals(status)){
                        Toast.makeText(SignActivity.this,"编辑签到成功",Toast.LENGTH_SHORT).show();
                        List<String> result =new ArrayList<String>();
                        initData();

                    }else{
                        Toast.makeText(SignActivity.this,message,Toast.LENGTH_SHORT).show();
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
