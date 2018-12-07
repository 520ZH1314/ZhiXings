package com.shuben.zhixing.www.patrol.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.management.AuditActivity;
import com.shuben.zhixing.www.patrol.management.GardenActivity;
import com.shuben.zhixing.www.patrol.management.MyQuestionActivity;
import com.shuben.zhixing.www.patrol.management.RecordActivity;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Geyan on 2018/3/12.
 */

public class PatrolFragment03 extends Fragment implements View.OnClickListener{
    private View view_layout;
    private TextView tx_item01,tx_item02,tx_item03,tx_item04;
    private Context context;
    private RelativeLayout m_set_lay01,m_set_lay02,m_set_lay03,m_set_lay04;
    private LoadingDailog dialog;//加载动画
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_patrol03,container,false);
        context = getActivity();
        initView();
        setOnclick();
        initData();
        return view_layout;
    }

    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetProblemSummary");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("LiableUserId", SharedPreferencesTool.getMStool(context).getUserId());
        JSONObject myData=new JSONObject();
        try {
            myData.put("AppCode", "LinePatrol");
            myData.put("ApiCode", "GetProblemSummary");
            myData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            myData.put("LiableUserId", SharedPreferencesTool.getMStool(context).getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*******巡线管理*******",myData.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());

                dialog.dismiss();
                try {
                    int Total=jsonobj.getInt("Total");
                    int OnGoing=jsonobj.getInt("OnGoing");
                    int UnTreated=jsonobj.getInt("UnTreated");
                    int UnAudit=jsonobj.getInt("UnAudit");
                    tx_item01.setText(Total+"");
                    tx_item02.setText(OnGoing+"");
                    tx_item03.setText(UnTreated+"");
                    tx_item04.setText(UnAudit+"");



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
        tx_item01=(TextView)view_layout.findViewById(R.id.tx_item01);
        tx_item02=(TextView)view_layout.findViewById(R.id.tx_item02);
        tx_item03=(TextView)view_layout.findViewById(R.id.tx_item03);
        tx_item04=(TextView)view_layout.findViewById(R.id.tx_item04);

        m_set_lay01=(RelativeLayout) view_layout.findViewById(R.id.m_set_lay01);
        m_set_lay02=(RelativeLayout) view_layout.findViewById(R.id.m_set_lay02);
        m_set_lay03=(RelativeLayout) view_layout.findViewById(R.id.m_set_lay03);
        m_set_lay04=(RelativeLayout) view_layout.findViewById(R.id.m_set_lay04);
    }

    private void setOnclick() {
        m_set_lay01.setOnClickListener(this);
        m_set_lay02.setOnClickListener(this);
        m_set_lay03.setOnClickListener(this);
        m_set_lay04.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.m_set_lay01:
                //巡线记录
                intent = new Intent(getActivity(), RecordActivity.class);
                startActivity(intent);
                break;

            case R.id.m_set_lay02:
                //我的问题
                intent = new Intent(getActivity(), MyQuestionActivity.class);
                startActivity(intent);
                break;

            case R.id.m_set_lay03:
                //我的审核
                intent = new Intent(getActivity(), AuditActivity.class);
                startActivity(intent);
                break;
            case R.id.m_set_lay04:
                //学习园地
                intent = new Intent(getActivity(), GardenActivity.class);
                startActivity(intent);
                break;



        }
    }
}
