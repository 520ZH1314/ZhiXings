package com.shuben.zhixing.www.patrol.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.shuben.zhixing.www.patrol.adapter.ClassGridAdpter;
import com.shuben.zhixing.www.patrol.analysis.AnalysisActivity;
import com.shuben.zhixing.www.patrol.bean.ClassInfo;
import com.shuben.zhixing.www.patrol.billboard.BillActivity;
import com.shuben.zhixing.www.patrol.management.AuditActivity;
import com.shuben.zhixing.www.patrol.management.GardenActivity;
import com.shuben.zhixing.www.patrol.management.MyQuestionActivity;
import com.shuben.zhixing.www.patrol.management.RecordActivity;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SysUtils;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.gudong.drawable.OneDrawable;

/**
 * Created by Geyan on 2018/3/12.
 */

public class PatrolFragment01 extends Fragment implements View.OnClickListener{
    private View view_layout;
    private TextView tx_user;
    private Activity context;
    private Button p_bnt01;
    private  Button p_bnt02,p_bnt03,p_bnt04,p_bnt05,p_bnt06,
            p_bnt07,p_bnt08,p_bnt09,p_bnt10,p_bnt11,p_bnt12,p_bnt13,p_bnt14;
    private Intent intent;
    private RelativeLayout lay_back;
    private GridView gridView;
    private List<ClassInfo> mlist=new ArrayList<ClassInfo>();
    private LoadingDailog dialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_patrol01,container,false);
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
        JSONObject mJoson=new JSONObject();
        try {
            mJoson.put("AppCode", "LinePatrol");
            mJoson.put("ApiCode", "GetLinePatrolClass");
            mJoson.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("****巡线类别****",mJoson.toString());


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
                    ClassGridAdpter adapter=new ClassGridAdpter(context,mlist);
                    changeGridView(gridView,mlist);
                    gridView.setAdapter(adapter);
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

    private void setDrawable(final Button button, Drawable icon) {
        button.setBackgroundDrawable(icon);
        button.setClickable(true);
    }
    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(getActivity())
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        lay_back=(RelativeLayout) view_layout.findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        tx_user=(TextView)view_layout.findViewById(R.id.tx_user);
        tx_user.setText("当前用户:"+SharedPreferencesTool.getMStool(context).getUserName());
        ImageView title_back= (ImageView) view_layout.findViewById(R.id.title_back);
        TextView tx_back= (TextView) view_layout.findViewById(R.id.tx_back);
        gridView=(GridView)view_layout.findViewById(R.id.grid);

        p_bnt05= (Button) view_layout.findViewById(R.id.p_bnt05);
        p_bnt06= (Button) view_layout.findViewById(R.id.p_bnt06);
        p_bnt07= (Button) view_layout.findViewById(R.id.p_bnt07);
        p_bnt08= (Button) view_layout.findViewById(R.id.p_bnt08);
        p_bnt09= (Button) view_layout.findViewById(R.id.p_bnt09);
        p_bnt10= (Button) view_layout.findViewById(R.id.p_bnt10);
        p_bnt11= (Button) view_layout.findViewById(R.id.p_bnt11);
        p_bnt12= (Button) view_layout.findViewById(R.id.p_bnt12);
        p_bnt13= (Button) view_layout.findViewById(R.id.p_bnt13);
        p_bnt14= (Button) view_layout.findViewById(R.id.p_bnt14);
        //setDrawable(p_bnt01, OneDrawable.createBgDrawable(context, R.mipmap.patrol01));
        //setDrawable(p_bnt02, OneDrawable.createBgDrawable(context, R.mipmap.patrol02));
        //setDrawable(p_bnt03, OneDrawable.createBgDrawable(context, R.mipmap.patrol03));
        //setDrawable(p_bnt04, OneDrawable.createBgDrawable(context, R.mipmap.patrol04));
        setDrawable(p_bnt05, OneDrawable.createBgDrawable(context, R.mipmap.patrol05));
        setDrawable(p_bnt06, OneDrawable.createBgDrawable(context, R.mipmap.patrol06));
        setDrawable(p_bnt07, OneDrawable.createBgDrawable(context, R.mipmap.patrol07));
        setDrawable(p_bnt08, OneDrawable.createBgDrawable(context, R.mipmap.patrol08));
        setDrawable(p_bnt09, OneDrawable.createBgDrawable(context, R.mipmap.patrol09));
        setDrawable(p_bnt10, OneDrawable.createBgDrawable(context, R.mipmap.patrol10));
        setDrawable(p_bnt11, OneDrawable.createBgDrawable(context, R.mipmap.patrol11));
        setDrawable(p_bnt12, OneDrawable.createBgDrawable(context, R.mipmap.patrol12));
        setDrawable(p_bnt13, OneDrawable.createBgDrawable(context, R.mipmap.patrol13));
        setDrawable(p_bnt14, OneDrawable.createBgDrawable(context, R.mipmap.patrol14));
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);


        p_bnt05.setOnClickListener(this);
        p_bnt06.setOnClickListener(this);
        p_bnt07.setOnClickListener(this);
        p_bnt08.setOnClickListener(this);
        p_bnt09.setOnClickListener(this);
        p_bnt10.setOnClickListener(this);
        p_bnt11.setOnClickListener(this);
        p_bnt12.setOnClickListener(this);
        p_bnt13.setOnClickListener(this);
        p_bnt14.setOnClickListener(this);
    }

    /**
     * 将GridView改成单行横向布局
     */
    private void changeGridView(GridView mContentGv,List<ClassInfo> datas) {
        // item宽度
        int itemWidth = dip2px(context, 200);

        itemWidth=(SysUtils.getScreenWidth(context) - SysUtils.Dp2Px(context, 100)) / 3;
        // item之间的间隔
        int itemPaddingH = dip2px(context, 50);
        int size = datas.size();
        // 计算GridView宽度
        int gridviewWidth = size * (itemWidth + itemPaddingH);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        mContentGv.setLayoutParams(params);
        mContentGv.setColumnWidth(itemWidth);
        mContentGv.setHorizontalSpacing(itemPaddingH);
        mContentGv.setStretchMode(GridView.NO_STRETCH);
        mContentGv.setNumColumns(size);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context  上下文
     * @param dpValue  dp值
     * @return px值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.lay_back:
                getActivity().finish();
                break;
            case R.id.title_back:
                getActivity().finish();
                break;
            case R.id.tx_back:
                getActivity().finish();
                break;



            case R.id.p_bnt05:
                intent=new Intent();
                intent.setClass(getActivity(), RecordActivity.class);
                startActivity(intent);

                break;
            case R.id.p_bnt06:
                intent=new Intent();
                intent.setClass(getActivity(), MyQuestionActivity.class);
                startActivity(intent);

                break;
            case R.id.p_bnt07:
                intent=new Intent();
                intent.setClass(getActivity(), AuditActivity.class);
                startActivity(intent);

                break;
            case R.id.p_bnt08:
                intent=new Intent();
                intent.setClass(getActivity(), GardenActivity.class);
                startActivity(intent);
                break;

            case R.id.p_bnt09:
                intent=new Intent();
                intent.setClass(getActivity(), BillActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);

                break;
            case R.id.p_bnt10:
                intent=new Intent();
                intent.setClass(getActivity(), BillActivity.class);
                intent.putExtra("index",1);
                startActivity(intent);

                break;
            case R.id.p_bnt11:
                intent=new Intent();
                intent.setClass(getActivity(), BillActivity.class);
                intent.putExtra("index",2);
                startActivity(intent);

                break;
            case R.id.p_bnt12:
                intent=new Intent();
                intent.setClass(getActivity(), AnalysisActivity.class);
                intent.putExtra("index",0);
                startActivity(intent);

                break;
            case R.id.p_bnt13:
                intent=new Intent();
                intent.setClass(getActivity(), AnalysisActivity.class);
                intent.putExtra("index",1);
                startActivity(intent);

                break;
            case R.id.p_bnt14:
                intent=new Intent();
               //  intent.setClass(getActivity(), CombinedChartActivity.class);
                intent.setClass(getActivity(), AnalysisActivity.class);
                intent.putExtra("index",2);
                startActivity(intent);

                break;

            default:
                break;
        }

    }
}