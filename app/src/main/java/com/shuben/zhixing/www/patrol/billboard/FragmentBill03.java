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
import com.base.zhixing.www.util.UrlUtil;

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

public class FragmentBill03  extends Fragment implements View.OnClickListener{
    private View view_layout;
    private Context context;
    private  TextView tx_date;
    private List<SceneInfo> list01=new ArrayList<SceneInfo>();
    private List<SceneInfo> list02=new ArrayList<SceneInfo>();
    private String myDate="";
    private  ListView listview01,listview02;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_bill03,container,false);
        context = getActivity();

        return view_layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initData(myDate);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData(myDate);
    }

    private void initData(String myDate) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetThumbSequenceByUser");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("PatrolDate", myDate);
        JSONObject mData=new JSONObject();
        try {
            mData.put("AppCode", "LinePatrol");
            mData.put("ApiCode", "GetThumbSequenceByUser");
            mData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            mData.put("PatrolDate", myDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("****人气榜****",mData.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                try {
                    JSONArray jArray01=jsonobj.getJSONArray("SingleThumbSequence");
                    JSONArray jArray02=jsonobj.getJSONArray("UserThumbSequence");
                    Log.e("****A***",jArray01.toString());
                    Log.e("****B***",jArray02.toString());
                    JSONObject jData01,jData02;
                    list01.clear();
                    list02.clear();
                    for (int i=0;i<jArray01.length();i++){
                        jData01=jArray01.getJSONObject(i);
                        String WorkShopName=jData01.getString("UserName");//改善人
                        String LeaderName=jData01.getString("ItemType");//改善类型
                        if(LeaderName==null||LeaderName.equals("null")){
                            LeaderName="其它";
                        }
                        String Scor=jData01.getInt("Thumb")+"";//获赞数
                        list01.add(new SceneInfo(i+"",WorkShopName,LeaderName,Scor));
                    }
                    for (int i=0;i<jArray02.length();i++){
                        jData02=jArray02.getJSONObject(i);
                        String WorkShopName=jData02.getString("UserName");//人员名称
                        String LeaderName=jData02.getString("OrganizeName");//部门
                        String Scor=jData02.getInt("Thumb")+"";//获赞数
                        list02.add(new SceneInfo(i+"",WorkShopName,LeaderName,Scor));
                    }
                    SceneAdapter adapter01=new SceneAdapter(context,list01);
                    SceneAdapter adapter02=new SceneAdapter(context,list02);
                    listview01.setAdapter(adapter01);
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




    private void initView() {
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
        listview01=(ListView)view_layout.findViewById(R.id.listview_scene01);
        View head01=getActivity().getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView tx01=(TextView)head01.findViewById(R.id.tx_item01);
        TextView tx02=(TextView)head01.findViewById(R.id.tx_item02);
        TextView tx03=(TextView)head01.findViewById(R.id.tx_item03);
        TextView tx04=(TextView)head01.findViewById(R.id.tx_item04);
        tx01.setText("排名");
        tx02.setText("改善人");
        tx03.setText("改善项目");
        tx04.setText("点赞数");
        listview01.addHeaderView(head01);

        listview02=(ListView)view_layout.findViewById(R.id.listview_scene02);
        View head02=getActivity().getLayoutInflater().inflate(R.layout.listview_scene_title, null);
        TextView t01=(TextView)head02.findViewById(R.id.tx_item01);
        TextView t02=(TextView)head02.findViewById(R.id.tx_item02);
        TextView t03=(TextView)head02.findViewById(R.id.tx_item03);
        TextView t04=(TextView)head02.findViewById(R.id.tx_item04);
        t01.setText("排名");
        t02.setText("姓名");
        t03.setText("所属部门");
        t04.setText("总点赞数");
        listview02.addHeaderView(head02);
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
