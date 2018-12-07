package com.shuben.zhixing.www.patrol.analysis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.AnanlysisAdapter;
import com.shuben.zhixing.www.patrol.bean.SceneInfo;
import com.shuben.zhixing.www.util.DateUtil;
import com.shuben.zhixing.www.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Geyan on 2018/3/23.
 */

public class AnalysisFragment01 extends Fragment implements View.OnClickListener{
    private View view_layout;
    private Context context;
    private  TextView tx_date;
    private List<SceneInfo> list01=new ArrayList<SceneInfo>();
    private List<SceneInfo> list02=new ArrayList<SceneInfo>();
    private List<SceneInfo> list03=new ArrayList<SceneInfo>();
    private List<SceneInfo> list04=new ArrayList<SceneInfo>();
    private String myDate="";
    private ListView listview01,listview02,listview03,listview04;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_analysis01,container,false);
        context = getActivity();
        Log.e("+++巡线总况+++","巡线总况");
        initView();
        initData();
        return view_layout;
    }

    private void initData() {
        String star=DateUtil.getInstance().getDateOfMonthStart();
        String end=DateUtil.getInstance().getDateOfToDay();
        String createTime = "";
        try {
            createTime = URLEncoder.encode("到", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        myDate=star+createTime+end;
        loadData01(myDate);//问题关闭统计
        loadData02(myDate);//巡线统计
        loadData03(myDate);//巡线各区域执行统计
        loadData04(myDate);//各区域问题关闭统计



    }

    private void loadData01(String date) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetProblemStatistics");
            params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            params.put("PatrolDate", date);
            params.put("Type", "PatrolClass");

            JSONObject mData=new JSONObject();
            try {
                mData.put("AppCode", "LinePatrol");
                mData.put("ApiCode", "GetProblemStatistics");
                mData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
                mData.put("PatrolDate", date);
                mData.put("Type", "PatrolClass");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("******问题关闭统计******",mData.toString());

            JsonObjectRequest newMissRequest = new JsonObjectRequest(

                    Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject jobject) {

                   try {
                       JSONArray jArray=jobject.getJSONArray("rows");

                        JSONObject jData;
                         list01.clear();
                        for (int i=0;i<jArray.length();i++){
                            jData=jArray.getJSONObject(i);
                            String ClassName=jData.getString("ClassName").substring(0,2);
                            String Total=jData.getString("Total");
                            String  Closed=jData.getString("Closed");
                            String rate="";
                            if(jData.getInt("Closed")==0||jData.getInt("Total")==0){
                                rate="0%";
                            }else{
                                float rr=(float)jData.getInt("Closed")/jData.getInt("Total");
                                DecimalFormat format = new DecimalFormat("0.00%");
                                rate=format.format(rr);
                            }

                            list01.add(new SceneInfo(ClassName,Total,Closed,rate));
                        }

                       AnanlysisAdapter adapter01=new AnanlysisAdapter(getActivity(),list01);
                       listview01.setAdapter(adapter01);

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

    }
    private void loadData02(String date) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetPatrolStatistics");
            params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            params.put("PatrolDate", date);
            params.put("Type", "PatrolClass");

            JSONObject mData=new JSONObject();
            try {
                mData.put("AppCode", "LinePatrol");
                mData.put("ApiCode", "GetPatrolStatistics");
                mData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
                mData.put("PatrolDate", date);
                mData.put("Type", "PatrolClass");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("******巡线统计******",mData.toString());


            JsonObjectRequest newMissRequest = new JsonObjectRequest(

                    Request.Method.POST,SharedPreferencesTool.getMStool(getActivity()).getIp()+UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject jobject) {

                    try {
                        JSONArray jArray=jobject.getJSONArray("rows");

                        JSONObject jData;
                        list02.clear();
                        for (int i=0;i<jArray.length();i++){
                            jData=jArray.getJSONObject(i);
                            String ClassName=jData.getString("ClassName").substring(0,2);
                            String Total=jData.getString("Total");
                            String  Closed=jData.getString("Closed");
                            String rate="";
                            if(jData.getInt("Closed")==0||jData.getInt("Total")==0){
                                rate="0%";
                            }else{
                                float rr=(float)jData.getInt("Closed")/jData.getInt("Total");
                                DecimalFormat format = new DecimalFormat("0.00%");
                                rate=format.format(rr);
                            }

                            list02.add(new SceneInfo(ClassName,Total,Closed,rate));
                        }

                        AnanlysisAdapter adapter02=new AnanlysisAdapter(getActivity(),list02);
                        listview02.setAdapter(adapter02);

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

    }
    private void loadData03(String date) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetProblemStatistics");
            params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            params.put("PatrolDate", date);
            params.put("Type", "WorkShop");

            JSONObject mData=new JSONObject();
            try {
                mData.put("AppCode", "LinePatrol");
                mData.put("ApiCode", "GetProblemStatistics");
                mData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
                mData.put("PatrolDate", date);
                mData.put("Type", "WorkShop");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("******各区域问题关闭统计******",mData.toString());



            JsonObjectRequest newMissRequest = new JsonObjectRequest(

                    Request.Method.POST,SharedPreferencesTool.getMStool(getActivity()).getIp()+UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject jobject) {

                    try {
                        JSONArray jArray=jobject.getJSONArray("rows");

                        JSONObject jData;
                        list03.clear();
                        for (int i=0;i<jArray.length();i++){
                            jData=jArray.getJSONObject(i);
                            String ClassName=jData.getString("WorkShopName");
                            String Total=jData.getString("Total");
                            String  Closed=jData.getString("Closed");
                            String rate="";
                            if(jData.getInt("Closed")==0||jData.getInt("Total")==0){
                                rate="0%";
                            }else{
                                float rr=(float)jData.getInt("Closed")/jData.getInt("Total");
                                DecimalFormat format = new DecimalFormat("0.00%");
                                rate=format.format(rr);
                            }

                            list03.add(new SceneInfo(ClassName,Total,Closed,rate));
                        }

                        AnanlysisAdapter adapter03=new AnanlysisAdapter(getActivity(),list03);
                        listview03.setAdapter(adapter03);

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

    }
    private void loadData04(String date) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetPatrolStatistics");
            params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            params.put("PatrolDate", date);
            params.put("Type", "WorkShop");

            JSONObject mData=new JSONObject();
            try {
                mData.put("AppCode", "LinePatrol");
                mData.put("ApiCode", "GetPatrolStatistics");
                mData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
                mData.put("PatrolDate", date);
                mData.put("Type", "WorkShop");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("******一级巡线各区域执行统计******",mData.toString());


            JsonObjectRequest newMissRequest = new JsonObjectRequest(

                    Request.Method.POST,SharedPreferencesTool.getMStool(getActivity()).getIp()+UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject jobject) {

                    try {
                        JSONArray jArray=jobject.getJSONArray("rows");

                        JSONObject jData;
                        list04.clear();
                        for (int i=0;i<jArray.length();i++){
                            jData=jArray.getJSONObject(i);
                            String ClassName=jData.getString("WorkShopName");
                            String Total=jData.getString("Total");
                            String  Closed=jData.getString("Closed");
                            String rate="";
                            if(jData.getInt("Closed")==0||jData.getInt("Total")==0){
                                rate="0%";
                            }else{
                                float rr=(float)jData.getInt("Closed")/jData.getInt("Total");
                                DecimalFormat format = new DecimalFormat("0.00%");
                                rate=format.format(rr);
                            }

                            list04.add(new SceneInfo(ClassName,Total,Closed,rate));
                        }

                        AnanlysisAdapter adapter04=new AnanlysisAdapter(getActivity(),list04);
                        listview04.setAdapter(adapter04);

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

    }


    private void initView() {
        Button bnt_calendar=(Button)view_layout.findViewById(R.id.bnt_calendar);
        bnt_calendar.setOnClickListener(this);
        tx_date=(TextView)view_layout.findViewById(R.id.tx_name);
        tx_date.setText(DateUtil.getInstance().getDateOfToMonth());
        //问题关闭统计

        listview01=(ListView)view_layout.findViewById(R.id.listview_statistical01);
        View head01=getActivity().getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView t01=(TextView)head01.findViewById(R.id.tx_item01);
        TextView t02=(TextView)head01.findViewById(R.id.tx_item02);
        TextView t03=(TextView)head01.findViewById(R.id.tx_item03);
        TextView t04=(TextView)head01.findViewById(R.id.tx_item04);
        t01.setText("类型");
        t02.setText("发现次数");
        t03.setText("关闭次数");
        t04.setText("关闭率");
        new ScrollListview(listview01);
        listview01.addHeaderView(head01);



        //巡线统计
        listview02=(ListView)view_layout.findViewById(R.id.listview_statistical02);
        View head02=getActivity().getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView tx01=(TextView)head02.findViewById(R.id.tx_item01);
        TextView tx02=(TextView)head02.findViewById(R.id.tx_item02);
        TextView tx03=(TextView)head02.findViewById(R.id.tx_item03);
        TextView tx04=(TextView)head02.findViewById(R.id.tx_item04);
        tx01.setText("类型");
        tx02.setText("计划次数");
        tx03.setText("实际次数");
        tx04.setText("完成率");
        new ScrollListview(listview02);
        listview02.addHeaderView(head02);



        //各区域问题关闭统计

        listview03=(ListView)view_layout.findViewById(R.id.listview_statistical03);
        View head03=getActivity().getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView txx01=(TextView)head03.findViewById(R.id.tx_item01);
        TextView txx02=(TextView)head03.findViewById(R.id.tx_item02);
        TextView txx03=(TextView)head03.findViewById(R.id.tx_item03);
        TextView txx04=(TextView)head03.findViewById(R.id.tx_item04);

        txx01.setText("区域");
        txx02.setText("发现次数");
        txx03.setText("关闭次数");
        txx04.setText("关闭率");
        new ScrollListview(listview03);
        listview03.addHeaderView(head03);

        //一级巡线各区域执行统计
         listview04=(ListView)view_layout.findViewById(R.id.listview_statistical04);
        View head04=getActivity().getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView txxx01=(TextView)head04.findViewById(R.id.tx_item01);
        TextView txxx02=(TextView)head04.findViewById(R.id.tx_item02);
        TextView txxx03=(TextView)head04.findViewById(R.id.tx_item03);
        TextView txxx04=(TextView)head04.findViewById(R.id.tx_item04);
        txxx01.setText("区域");
        txxx02.setText("计划次数");
        txxx03.setText("实际次数");
        txxx04.setText("完成率");
        new ScrollListview(listview04);
        listview04.addHeaderView(head04);

    }

    @Override
    public void onClick(View v) {
        {
            Intent intent;
            switch (v.getId()){
                case R.id.bnt_calendar:
                    //基础设置
                    setDate();
                    break;

            }
        }

    }

    private void setDate() {
        Calendar calendar= Calendar.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = (LinearLayout)getActivity(). getLayoutInflater().inflate(R.layout.date_dialog02, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);

        //设置日期简略显示 否则详细显示 包括:星期\周
        datePicker.setCalendarViewShown(false);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        //设置date布局
        hideDay(datePicker);
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("%d-%02d",
                        datePicker.getYear(),
                        datePicker.getMonth() + 1));
                tx_date.setText(sb);
               myDate=DateUtil.getInstance().getPeriodOfMonth(datePicker.getYear(),datePicker.getMonth() + 1);
                loadData01(myDate);//问题关闭统计
                loadData02(myDate);//巡线统计
                loadData03(myDate);//巡线各区域执行统计
                loadData04(myDate);//各区域问题关闭统计
                // Toast.makeText(context, myDate ,Toast.LENGTH_SHORT).show();
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

    private void hideDay(DatePicker mDatePicker) {
        try {
           /* 处理android5.0以上的特殊情况 */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                if (daySpinnerId != 0) {
                    View daySpinner = mDatePicker.findViewById(daySpinnerId);
                    if (daySpinner != null) {
                        daySpinner.setVisibility(View.GONE);
                    }
                }
            } else {
                Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
                for (Field datePickerField : datePickerfFields) {
                    if ("mDaySpinner".equals(datePickerField.getName()) || ("mDayPicker").equals(datePickerField.getName())) {
                        datePickerField.setAccessible(true);
                        Object dayPicker = new Object();
                        try {
                            dayPicker = datePickerField.get(mDatePicker);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        ((View) dayPicker).setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


