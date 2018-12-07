package com.shuben.zhixing.www.patrol.billboard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.SceneAdapter;
import com.shuben.zhixing.www.patrol.bean.SceneInfo;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Geyan on 2018/3/22.
 */

public class FragmentBill02 extends Fragment implements View.OnClickListener{
    private View view_layout;
    private Context context;
    private  TextView tx_date;
    private List<SceneInfo> list=new ArrayList<SceneInfo>();
    private ListView listview;
    private String myDate="";
    private LoadingDailog dialog;//加载动画

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_bill02,container,false);
        context = getActivity();
        initView();
        initData(myDate);
        return view_layout;
    }

    private void initData(String date) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetWorkShopPatrolScore");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("PatrolDate", date);

        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "LinePatrol");
            mJson.put("ApiCode", "GetWorkShopPatrolScore");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            mJson.put("PatrolDate", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("******龙虎榜美丽现场********",mJson.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp(),
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("***美丽现场***",jsonobj.toString());
                try {
                    dialog.dismiss();
                    JSONArray jArray=jsonobj.getJSONArray("rows");

                    JSONObject jData;
                    list.clear();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String WorkShopName=jData.getString("WorkShopName");//车间
                        String LeaderName=jData.getString("LeaderName");//责任人
                        String Scor=jData.getString("Score");
                        list.add(new SceneInfo(i+"",WorkShopName,LeaderName,Scor));
                    }
                    SceneAdapter adapter=new SceneAdapter(context,list);
                    listview.setAdapter(adapter);
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
        String star= DateUtil.getInstance().getDateOfMonthStart();
        String end=DateUtil.getInstance().getDateOfToDay();
        String createTime = "";
        try {
            createTime = URLEncoder.encode("到", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        myDate=star+createTime+end;
        Button bnt_calendar=(Button)view_layout.findViewById(R.id.bnt_calendar);
        bnt_calendar.setOnClickListener(this);
        tx_date=(TextView)view_layout.findViewById(R.id.tx_date);
        tx_date.setText(DateUtil.getInstance().getDateOfToMonth());
         listview=(ListView)view_layout.findViewById(R.id.listview_scene);
        View head01=getActivity().getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView tx01=(TextView)head01.findViewById(R.id.tx_item01);
        TextView tx02=(TextView)head01.findViewById(R.id.tx_item02);
        TextView tx03=(TextView)head01.findViewById(R.id.tx_item03);
        TextView tx04=(TextView)head01.findViewById(R.id.tx_item04);
        tx01.setText("排名");
        tx02.setText("区域");
        tx03.setText("责任人");
        tx04.setText("评分");
        listview.addHeaderView(head01);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                myDate= DateUtil.getInstance().getPeriodOfMonth(datePicker.getYear(),datePicker.getMonth() + 1);
                initData(myDate);
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
