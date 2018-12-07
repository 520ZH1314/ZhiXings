package com.shuben.zhixing.www.patrol.analysis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.adapter.ReportAdapter;
import com.shuben.zhixing.www.patrol.bean.SceneInfo;
import com.shuben.zhixing.www.util.ActionItem;
import com.shuben.zhixing.www.util.DateUtil;
import com.shuben.zhixing.www.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.TitlePopup;
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

import name.gudong.drawable.OneDrawable;

import static android.graphics.Color.GRAY;

/**
 * Created by Geyan on 2018/3/23.
 */

public class AnalysisFragment03 extends Fragment implements View.OnClickListener{
    private View view_layout;
    private Context context;
    private LinearLayout lay01,lay02,lay03;
    private RelativeLayout search_lay;
    private Intent intent;
    private TextView tx_date,tx_class01,tx_class02,tx_class03,t05;
    private Button bnt_calendar,bnt_menu;
    private ListView listview01;

    private List<SceneInfo> list01=new ArrayList<SceneInfo>();
    private String myDate="";
    private String classs ="一级巡线";
    private TitlePopup titlePopup;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_analysis03,container,false);
        context = getActivity();
        Log.e("+++个人报告+++","个人报告");
        initView();
        initData();
        return view_layout;
    }

    private void initView() {
        initPopWindow();
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

        bnt_calendar=(Button) view_layout.findViewById(R.id.bnt_calendar);
        bnt_calendar.setOnClickListener(this);






        tx_date=(TextView) view_layout.findViewById(R.id.tx_date);
        tx_date.setText(DateUtil.getInstance().getDateOfToMonth());
        search_lay=(RelativeLayout) view_layout.findViewById(R.id.search_lay);
        search_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(context,MyReportActivity.class);
                intent.putExtra("tx_date",tx_date.getText().toString());
                intent.putExtra("myDate",myDate);
                context.startActivity(intent);

            }
        });
        lay01=(LinearLayout)view_layout.findViewById(R.id.lay1);
        lay02=(LinearLayout)view_layout.findViewById(R.id.lay2);
        lay03=(LinearLayout)view_layout.findViewById(R.id.lay3);
        lay01.setOnClickListener(this);
        lay02.setOnClickListener(this);
        lay03.setOnClickListener(this);
        tx_class01=(TextView)view_layout.findViewById(R.id.tx_class01);
        tx_class02=(TextView)view_layout.findViewById(R.id.tx_class02);
        tx_class03=(TextView)view_layout.findViewById(R.id.tx_class03);
        lay01.setSelected(true);
        tx_class01.setTextColor(Color.rgb(57,176,255));
        //问题关闭统计

        listview01=(ListView)view_layout.findViewById(R.id.listview);
        View head01=getActivity().getLayoutInflater().inflate(R.layout.patrol_listview_title, null);
        TextView t01=(TextView)head01.findViewById(R.id.tx_item01);
        TextView t02=(TextView)head01.findViewById(R.id.tx_item02);
        TextView t03=(TextView)head01.findViewById(R.id.tx_item03);
        TextView t04=(TextView)head01.findViewById(R.id.tx_item04);
         t05=(TextView)head01.findViewById(R.id.tx_item05);

        bnt_menu=(Button) head01.findViewById(R.id.bnt_menu);
        setDrawable(bnt_menu, OneDrawable.createBgDrawable(context, R.mipmap.menu));
        bnt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlePopup.show(v);
            }
        });
        t01.setText("序号");
        t02.setText("岗位");
        t03.setText("责任人");
        t04.setText("发现问题数");
        t05.setText("未关闭问题数");
        listview01.addHeaderView(head01);




    }
    private void initData() {
        loadData01(myDate);//问题关闭统计
    }
    private void loadData01(String date) {
        {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            Map<String, String> params = new HashMap<String, String>();
            params.put("AppCode", "LinePatrol");
            params.put("ApiCode", "GetProblemByPatrolUser");
            params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            params.put("PatrolDate", date);
            params.put("PatrolUserId", SharedPreferencesTool.getMStool(context).getUserId());
            JSONObject mJson=new JSONObject();
            try {
                mJson.put("AppCode", "LinePatrol");
                mJson.put("ApiCode", "GetProblemByPatrolUser");
                mJson.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
                mJson.put("PatrolDate", date);
                mJson.put("PatrolUserId", SharedPreferencesTool.getMStool(context).getUserId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("*****管理分析巡线报告*****",mJson.toString());


            JsonObjectRequest newMissRequest = new JsonObjectRequest(

                    Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                    new JSONObject(params), new Response.Listener<JSONObject>() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONObject jobject) {

                    try {
                        JSONArray jArray=jobject.getJSONArray("rows");
                        Log.e("KKKKK",jArray.toString());
                        JSONObject jData01,jData02;
                        list01.clear();
                        for (int i=0;i<jArray.length();i++){
                            jData01=jArray.getJSONObject(i);
                            String ClassName=jData01.getString("ClassName");
                            if(classs.equals(ClassName)){
                                JSONArray Array=jData01.getJSONArray("PatrolProblem");
                                for (int j=0;j<Array.length();j++){
                                    jData02=Array.getJSONObject(j);
                                    Log.e("JJJJ",Array.toString());
                                    String PositionName=jData02.getString("PositionName");
                                    String UserName=jData02.getString("UserName");
                                    String Problems=jData02.getString("Problems");
                                    String UnClosed=jData02.getString("UnClosed");
                                    list01.add(new SceneInfo(PositionName,UserName,Problems,UnClosed));

                                }

                            }



                        }
                        ReportAdapter adapter01=new ReportAdapter(getActivity(),list01);
                        listview01.setAdapter(adapter01);
                        new  ScrollListview(listview01);

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.bnt_calendar:
                setDate();
                break;

            case R.id.lay1:
                tx_class01.setTextColor(Color.rgb(57,176,255));
                tx_class02.setTextColor(GRAY);
                tx_class03.setTextColor(GRAY);
                lay01.setSelected(true);
                lay02.setSelected(false);
                lay03.setSelected(false);
                classs ="一级巡线";
                loadData01(myDate);
                break;
            case R.id.lay2:
                tx_class01.setTextColor(GRAY);
                tx_class02.setTextColor(Color.rgb(57,176,255));
                tx_class03.setTextColor(GRAY);
                lay01.setSelected(false);
                lay02.setSelected(true);
                lay03.setSelected(false);
                classs ="二级巡线";
                loadData01(myDate);
                break;
            case R.id.lay3:
                tx_class01.setTextColor(GRAY);
                tx_class02.setTextColor(GRAY);
                tx_class03.setTextColor(Color.rgb(57,176,255));
                lay01.setSelected(false);
                lay02.setSelected(false);
                lay03.setSelected(true);
                classs ="三级巡线";
                loadData01(myDate);
                break;
            default:

            break;



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



    private void initPopWindow() {
        // 实例化标题栏弹窗
        titlePopup = new TitlePopup(context, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        titlePopup.setItemOnClickListener(onitemClick);
        // 给标题栏弹窗添加子类
        titlePopup.addAction(new ActionItem(context, "改善问题数量",
                R.drawable.icon_menu_group));
        titlePopup.addAction(new ActionItem(context,"问题关闭率",
                R.drawable.icon_menu_addfriend));
        titlePopup.addAction(new ActionItem(context, "巡线次数",
                R.drawable.icon_menu_sao));
        titlePopup.addAction(new ActionItem(context, "准时率",
                R.drawable.abv));
    }

    private TitlePopup.OnItemOnClickListener onitemClick = new TitlePopup.OnItemOnClickListener() {

        @Override
        public void onItemClick(ActionItem item, int position) {
            // mLoadingDialog.show();
            switch (position) {
                case 0:// 发起群聊
                    t05.setText("改善问题数量");
                    break;
                case 1:// 添加朋友
                    t05.setText("问题关闭率");
                    break;
                case 2:// 扫一扫
                    t05.setText("巡线次数");
                    break;
                case 3:// 收钱
                    t05.setText("准时率");
                    break;
                default:
                    break;
            }
        }
    };

    private void setDrawable(final Button button, Drawable icon) {
        button.setBackgroundDrawable(icon);
        button.setClickable(true);
    }


}

