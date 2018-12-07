package com.shuben.zhixing.www.mes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.RoundProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MiniMesActivity extends BaseActvity implements View.OnClickListener{
    private RelativeLayout lay01,lay02,lay03,lay04;
    private RoundProgressBar roundProgressBar;
    private Intent intent;
    private TextView item01,item02,item03;
    private LoadingDailog dialog;//加载动画

    @Override
    public int getLayoutId() {
        return R.layout.activity_mini_mes;
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
        params.put("AppCode", "MiniMes");
        params.put("ApiCode", "GetHomeAPI");
        params.put("TenantId", SharedPreferencesTool.getMStool(MiniMesActivity.this).getTenantId());
        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "MiniMes");
            mJson.put("ApiCode", "GetHomeAPI");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(MiniMesActivity.this).getTenantId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****Mini首页****",mJson.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(MiniMesActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKMini首页KKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("DataTable");
                    JSONObject jData;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String Qty=jData.getString("Qty");
                        String PlanQty=jData.getString("PlanQty");
                        int Rate=jData.getInt("Rate");
                        roundProgressBar.setProgress(Rate);
                        item01.setText(PlanQty);
                        item02.setText(Qty);

                    }

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
        lay01=(RelativeLayout)findViewById(R.id.lay02);
        lay02=(RelativeLayout)findViewById(R.id.lay03);
        lay03=(RelativeLayout)findViewById(R.id.lay04);
        lay04=(RelativeLayout)findViewById(R.id.lay05);
        lay01.setOnClickListener(this);
        lay02.setOnClickListener(this);
        lay03.setOnClickListener(this);
        lay04.setOnClickListener(this);
        roundProgressBar=(RoundProgressBar) findViewById(R.id.progressBar);
        roundProgressBar.setMax(100);
        roundProgressBar.setProgress(80);
        roundProgressBar.setRoundProgressColor(Color.GREEN);
        roundProgressBar.setRoundColor(Color.GRAY);
        roundProgressBar.setRoundWidth(5f);
        item01= (TextView) findViewById(R.id.tx_item02);
        item02= (TextView) findViewById(R.id.tx_item03);
        item03= (TextView) findViewById(R.id.tx_item04);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay02:
                //计划达成
                intent=new Intent();
                intent.setClass(MiniMesActivity.this,PlanActivity.class);
                startActivity(intent);
                break;
            case R.id.lay03:
                //线体生产状态
                intent=new Intent();
                intent.setClass(MiniMesActivity.this,ProductionActivity.class);
                startActivity(intent);
                break;
            case R.id.lay04:
                //小时产量
                intent=new Intent();
                intent.setClass(MiniMesActivity.this,HoursActivity.class);
                startActivity(intent);
                break;
            case R.id.lay05:
                //损失工时
                intent=new Intent();
                intent.setClass(MiniMesActivity.this,LossActivity.class);
                startActivity(intent);
                break;
            default:

            break;
        }
    }
}
