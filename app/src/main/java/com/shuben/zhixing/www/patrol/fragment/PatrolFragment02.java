package com.shuben.zhixing.www.patrol.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.PatrolTaskActivity;
import com.shuben.zhixing.www.patrol.bean.ClassInfo;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Geyan on 2018/3/12.
 */

public class PatrolFragment02 extends Fragment implements View.OnClickListener{
    private View view_layout;
    private Context context;
    private List<ClassInfo> mlist=new ArrayList<ClassInfo>();
    private LoadingDailog dialog;
    private LinearLayout lay01,lay02,lay03;

    private static int[] title=new int[]{R.string.title01,R.string.title02,R.string.title03};
    private  Intent intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_patrol02,container,false);
        context = getActivity();

        initView();
        initData();
        return view_layout;
    }
    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetLinePatrolClass");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                //Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    mlist.clear();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String ClassId =jData.getString("ClassId");
                        String ClassCode =jData.getString("ClassCode");
                        String ClassName =jData.getString("ClassName");
                        //SharedPreferencesTool.getMStool(context).saveLinePatrolClassId(ClassName,ClassId);
                        ClassInfo classInfo=new ClassInfo(ClassId,ClassCode,ClassName,"");
                        mlist.add(classInfo);
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
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(getActivity())
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();

        lay01=view_layout.findViewById(R.id.lay01);
        lay02=view_layout.findViewById(R.id.lay02);
        lay03=view_layout.findViewById(R.id.lay03);
        lay01.setOnClickListener(this);
        lay02.setOnClickListener(this);
        lay03.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay01:
                intent=new Intent();
                intent.putExtra("ClassId",mlist.get(0).getItem01());
                intent.putExtra("ClassName",mlist.get(0).getItem03());
                intent.setClass(context, PatrolTaskActivity.class);
                context.startActivity(intent);
                break;
            case R.id.lay02:
                intent=new Intent();
                intent.putExtra("ClassId",mlist.get(1).getItem01());
                intent.putExtra("ClassName",mlist.get(1).getItem03());
                intent.setClass(context, PatrolTaskActivity.class);
                context.startActivity(intent);
                break;
            case R.id.lay03:
                intent=new Intent();
                intent.putExtra("ClassId",mlist.get(2).getItem01());
                intent.putExtra("ClassName",mlist.get(2).getItem03());
                intent.setClass(context, PatrolTaskActivity.class);
                context.startActivity(intent);
                break;

            default:

            break;



        }
    }
}


