package com.shuben.zhixing.www.patrol.analysis;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PerformActivity extends BaseActvity {
    private TextView tx_name01,tx_name02,tx_name03,tx_back;
    private TextView tx_item01,tx_item02,tx_item03,tx_item04,tx_item05,
            tx_item06,tx_item07,tx_item08,tx_item09,tx_item10,
            tx_item11,tx_item12,tx_item13,tx_item14,tx_item15,
            tx_item16,tx_item17,tx_item18,tx_item19,tx_item20;
    private String myDate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_perform;
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
        initData(getIntent().getStringExtra("myDate"));
    }

    private void initData(String date) {
        RequestQueue requestQueue = Volley.newRequestQueue(PerformActivity.this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetPatrolExcecute");
        params.put("TenantId", SharedPreferencesTool.getMStool(PerformActivity.this).getTenantId());
        params.put("PatrolDate",date);

        JSONObject MyData=new JSONObject();
        try {
            MyData.put("AppCode", "LinePatrol");
            MyData.put("ApiCode", "GetPatrolExcecute");
            MyData.put("TenantId", SharedPreferencesTool.getMStool(PerformActivity.this).getTenantId());
            MyData.put("PatrolDate",date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       Log.e("*****执行率*******",MyData.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(PerformActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {



                try {
                    Log.e("*********",jsonobj.toString());
                    DecimalFormat format = new DecimalFormat("0.00%");
                    JSONObject jData=jsonobj.getJSONObject("message");
                    String rate=format.format(jData.getDouble("AttendanceRate"));
                    JSONObject data01=jData.getJSONObject("PartakeRate");
                    JSONObject data02=jData.getJSONObject("PatrolEfficiency");
                    JSONObject data03=jData.getJSONObject("Discovered");
                    tx_item01.setText(rate);//参与率
                    tx_item05.setText(data01.getString("Absent"));//请假人数
                    tx_item06.setText(data01.getString("Midway"));//缺席人数
                    tx_item07.setText(data01.getString("Leave"));//早退人数

                    tx_item09.setText(data02.getString("PatrolTime"));//巡线总用时
                    tx_item10.setText(data02.getString("PlanTime"));//计划时长
                    tx_item11.setText(data02.getString("OverTime"));//超时
                    tx_item12.setText(data02.getString("PatrolArea"));//实际区域
                    tx_item13.setText(data02.getString("PlanArea"));//计划区域
                    tx_item14.setText(format.format(data02.getDouble("Efficiency")));//巡线完成率

                    tx_item16.setText(data03.getString("ProblemTotal"));//发现问题数
                    tx_item17.setText(data03.getString("HighlightTotal"));//发现亮点数
                    tx_item18.setText(data03.getString("PAU"));//人均问题发现
                    tx_item19.setText(data03.getString("HAU"));//人均亮点发现
                    tx_item20.setText(data03.getString("LastPatrolProblems"));//上次巡线问题发现




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

    private void initView() {
        tx_back=(TextView)findViewById(R.id.tx_back);
        tx_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tx_name01=(TextView)findViewById(R.id.tx_name01);
        tx_item01=(TextView)findViewById(R.id.tx_item01);
        tx_name01.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tx_item01.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tx_item02=(TextView)findViewById(R.id.tx_item09);
        tx_item03=(TextView)findViewById(R.id.tx_item03);
        tx_item04=(TextView)findViewById(R.id.tx_item04);
        tx_item05=(TextView)findViewById(R.id.tx_item05);
        tx_item06=(TextView)findViewById(R.id.tx_item06);
        tx_item07=(TextView)findViewById(R.id.tx_item07);
        //tx_item08=(TextView)findViewById(R.id.tx_item08);
        tx_item09=(TextView)findViewById(R.id.tx_item09);
        tx_item10=(TextView)findViewById(R.id.tx_item10);
        tx_item11=(TextView)findViewById(R.id.tx_item11);
        tx_item12=(TextView)findViewById(R.id.tx_item12);
        tx_item13=(TextView)findViewById(R.id.tx_item13);
        tx_item14=(TextView)findViewById(R.id.tx_item14);
        //tx_item15=(TextView)findViewById(R.id.tx_item15);
        tx_item16=(TextView)findViewById(R.id.tx_item16);
        tx_item17=(TextView)findViewById(R.id.tx_item17);
        tx_item18=(TextView)findViewById(R.id.tx_item18);
        tx_item19=(TextView)findViewById(R.id.tx_item19);
        tx_item20=(TextView)findViewById(R.id.tx_item20);


    }
}
