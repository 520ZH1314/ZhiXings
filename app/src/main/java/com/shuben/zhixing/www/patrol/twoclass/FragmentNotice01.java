package com.shuben.zhixing.www.patrol.twoclass;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Geyan on 2018/4/25.
 */

public class FragmentNotice01 extends Fragment {
    private View view_layout;
    private Context context;
    private String recordId;
    private TextView tx_name01,tx_name02,tx_name03,tx_date;
    private TextView tx_item01,tx_item02,tx_item03,tx_item04,tx_item05,
                      tx_item06,tx_item07,tx_item08,tx_item09,tx_item10,
                      tx_item11,tx_item12,tx_item13,tx_item14,tx_item15,
                      tx_item16,tx_item17,tx_item18,tx_item19,tx_item20;
    private LoadingDailog dialog;//加载动画



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_notice01,container,false);
        context = getActivity();
        initView();
        initData();
        return view_layout;
    }

    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetPartInSummary");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));

        JSONObject myData=new JSONObject();

        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetPartInSummary");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            myData.put("RecordId", getActivity().getIntent().getStringExtra("RecordId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
       Log.e("*****过程复盘******",myData.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("***********",jsonobj.toString());
                dialog.dismiss();

                try {
                    tx_item01.setText(jsonobj.getString("PartInRate"));//参与率
                    tx_item02.setText(jsonobj.getString("LeaveCount"));//请假人数
                    tx_item03.setText(jsonobj.getString("LeaveUsers"));//请假人员
                    tx_item04.setText(jsonobj.getString("AbsentCount"));//缺席人数
                    tx_item05.setText(jsonobj.getString("AbsentUsers"));//缺席人员
                    tx_item06.setText(jsonobj.getString("GoAwayCount"));//早退人数
                    tx_item07.setText(jsonobj.getString("GoAwayUsers"));//早退人员
                    tx_item09.setText(jsonobj.getString("RealPatrolTime"));//时间时长
                    tx_item10.setText(jsonobj.getString("PlanPatrolTime"));//计划时长
                    tx_item11.setText(jsonobj.getString("TimeOut"));//超时
                    tx_item12.setText(jsonobj.getString("RealPatrolAreaCount"));//实际区域
                    tx_item13.setText(jsonobj.getString("PlanPatrolAreaCount"));//计划区域
                    tx_item14.setText(jsonobj.getString("PatrolTaskCRate"));//达成率
                    tx_item16.setText(jsonobj.getString("FindProblemCount"));//发现问题数
                    tx_item17.setText(jsonobj.getString("FindHighLightCount"));//发现亮点数
                    tx_item18.setText(jsonobj.getString("AvgProblemCount"));//人均问题发现
                    tx_item19.setText(jsonobj.getString("AvgHighLightCount"));//人均亮点发现
                    tx_item20.setText(jsonobj.getString("LastFindProblemCount"));//上次巡线问题发现

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
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(context)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        tx_date=(TextView)view_layout.findViewById(R.id.tx_date);
        tx_date.setText(DateUtil.getInstance().getDateOfToDay());
        tx_name01=(TextView)view_layout.findViewById(R.id.tx_name01);
        tx_item01=(TextView)view_layout.findViewById(R.id.tx_item01);
        tx_name01.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tx_item01.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tx_item02=(TextView)view_layout.findViewById(R.id.tx_item02);
        tx_item03=(TextView)view_layout.findViewById(R.id.tx_item03);
        tx_item04=(TextView)view_layout.findViewById(R.id.tx_item04);
        tx_item05=(TextView)view_layout.findViewById(R.id.tx_item05);
        tx_item06=(TextView)view_layout.findViewById(R.id.tx_item06);
        tx_item07=(TextView)view_layout.findViewById(R.id.tx_item07);
        //tx_item08=(TextView)view_layout.findViewById(R.id.tx_item08);
        tx_item09=(TextView)view_layout.findViewById(R.id.tx_item09);
        tx_item10=(TextView)view_layout.findViewById(R.id.tx_item10);
        tx_item11=(TextView)view_layout.findViewById(R.id.tx_item11);
        tx_item12=(TextView)view_layout.findViewById(R.id.tx_item12);
        tx_item13=(TextView)view_layout.findViewById(R.id.tx_item13);
        tx_item14=(TextView)view_layout.findViewById(R.id.tx_item14);
        //tx_item15=(TextView)view_layout.findViewById(R.id.tx_item15);
        tx_item16=(TextView)view_layout.findViewById(R.id.tx_item16);
        tx_item17=(TextView)view_layout.findViewById(R.id.tx_item17);
        tx_item18=(TextView)view_layout.findViewById(R.id.tx_item18);
        tx_item19=(TextView)view_layout.findViewById(R.id.tx_item19);
        tx_item20=(TextView)view_layout.findViewById(R.id.tx_item20);


    }
}
