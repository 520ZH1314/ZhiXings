package com.shuben.zhixing.www.patrol.analysis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.util.DateUtil;
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

public class AnalysisFragment02 extends Fragment implements View.OnClickListener{
    private View view_layout;
    private Context context;
    private LinearLayout lay01,lay02;
    private RelativeLayout search_lay;
    private Intent intent;
    private TextView tx_date;
    private Button bnt_calendar;
    private String myDate;
    private LoadingDailog dialog;//加载动画

    private CombinedChart dataChart01;//图表
    private CombinedChart dataChart02;//图表

    private CombinedData data01;
    private CombinedData data02;
    private List<String> valuesX01;
    private List<String> valuesX02;
    private List<Integer> close01;
    private List<Integer> close02;
    private List<Integer> Unclose01;
    private List<Integer> Unclose02;
    private List<Float> rate01;
    private List<Float> rate02;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_layout = inflater.inflate(R.layout.fragment_analysis02,container,false);
        context = getActivity();
        Log.e("+++月度报告+++","月度报告");
        initView();
        initData();
        return view_layout;
    }

    private void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "GetProblemStatisticsByL2");
        params.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
        params.put("PatrolDate", myDate);

        JSONObject mData=new JSONObject();
        try {
            mData.put("AppCode", "LinePatrol");
            mData.put("ApiCode", "GetProblemStatisticsByL2");
            mData.put("TenantId", SharedPreferencesTool.getMStool(context).getTenantId());
            mData.put("PatrolDate", myDate);
            Log.e("******月报图表*******",mData.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(getActivity()).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray01=jsonobj.getJSONArray("ClassStatistics");
                    JSONArray jArray02=jsonobj.getJSONArray("WorkShopStatistics");
                    JSONObject jData01;
                    JSONObject jData02;
                    valuesX01=new ArrayList<String>();
                    valuesX02=new ArrayList<String>();
                    close01=new ArrayList<Integer>();
                    close02=new ArrayList<Integer>();

                    Unclose01=new ArrayList<Integer>();
                    Unclose02=new ArrayList<Integer>();
                    rate01=new ArrayList<Float>();
                    rate02=new ArrayList<Float>();
                    float max1=0;
                    float max2=0;
                    int long01=0;
                    int long02=0;
                    for (int i=0;i<jArray01.length();i++){
                        jData01=jArray01.getJSONObject(i);
                        String ItemType=jData01.getString("ItemType");
                        if(long01< ItemType.length()){
                            long01=ItemType.length();
                        }

                        int Total=jData01.getInt("Total");//总数
                        int Closed=  jData01.getInt("Closed");//已关闭
                        close01.add(Closed);
                        Unclose01.add(Total-Closed);
                        valuesX01.add(ItemType);
                        float f1=Closed;
                        float f2=Total;
                        if(Total>0){
                            float rr=(f1/f2);
                            rate01.add(rr);
                        }else{
                            rate01.add(0f);
                        }
                        if(max1<Total){
                            max1=Total;
                        }


                    }
                    Log.e("**图表1**",valuesX01.size()+"");
                    Log.e("**图表1**",rate01.size()+"");
                    Log.e("**图表1**",close01.size()+"");
                    Log.e("**图表1**",Unclose01.size()+"");
                   for (int j=0;j<jArray02.length();j++){
                        jData02=jArray02.getJSONObject(j);
                        String WorkShopName=jData02.getString("WorkShopName");
                       if(long02< WorkShopName.length()){
                           long02=WorkShopName.length();
                       }
                        int Total=jData02.getInt("Total");//总数
                        int Closed=  jData02.getInt("Closed");//已关闭
                        close02.add(Closed);
                        Unclose02.add(Total-Closed);
                        valuesX02.add(WorkShopName);
                       float f1=Closed;
                       float f2=Total;
                       if(Total>0){
                           float rr=(f1/f2);
                           rate02.add(rr);
                       }else{
                           rate02.add(0f);
                       }
                       if(max2<Total){
                           max2=Total;
                       }

                    }


                    showDataOnChart01(valuesX01,rate01,close01,Unclose01,max1,long01);
                    showDataOnChart02(valuesX02,rate02,close02,Unclose02,max2,long02);


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

        bnt_calendar=(Button) view_layout.findViewById(R.id.bnt_calendar);
        bnt_calendar.setOnClickListener(this);
        tx_date=(TextView) view_layout.findViewById(R.id.tx_date);
        tx_date.setText(DateUtil.getInstance().getDateOfToMonth());
        lay01=(LinearLayout)view_layout.findViewById(R.id.l_lay01);
        lay02=(LinearLayout)view_layout.findViewById(R.id.l_lay02);
        search_lay=(RelativeLayout) view_layout.findViewById(R.id.search_lay);
        lay01.setOnClickListener(this);
        lay02.setOnClickListener(this);
        search_lay.setOnClickListener(this);
        tx_date.setOnClickListener(this);

        dataChart01=(CombinedChart)view_layout.findViewById(R.id.lay01);
        dataChart02=(CombinedChart)view_layout.findViewById(R.id.lay02);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.l_lay01:
                intent=new Intent();
                intent.setClass(context,PerformActivity.class);
                intent.putExtra("tx_date",tx_date.getText().toString());
                intent.putExtra("myDate",myDate);
                context.startActivity(intent);

                break;
            case R.id.l_lay02:
                intent=new Intent();
                intent.setClass(context,ResultsActivity.class);
                intent.putExtra("tx_date",tx_date.getText().toString());
                intent.putExtra("myDate",myDate);
                context.startActivity(intent);

                break;
            case R.id.search_lay:
                intent=new Intent();
                intent.setClass(context,UnCloseActivity.class);
                intent.putExtra("tx_date",tx_date.getText().toString());
                intent.putExtra("myDate",myDate);
                context.startActivity(intent);
                break;

            case R.id.bnt_calendar:
                setDate();
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


    /**
     * 展示数据
     */
    private void showDataOnChart01(List<String> valuesX ,List<Float> lineData,List<Integer> barData01,List<Integer> barData02,float max,int long01) {
        //绘制图表数据
        data01 = new CombinedData();
        //设置折线图数据
        data01.setData(getLineData(lineData));
        //设置柱状图数据
        data01.setData(getBarData(barData01,barData02));
        dataChart01.setData(data01);
        //设置横坐标数据
        setAxisXBottom01(valuesX);

        //设置右侧纵坐标数据
        setAxisYRight01();
        //设置左侧纵坐标数据
        setAxisYLeft01(max);
        dataChart01.setTouchEnabled(false);
        dataChart01.getDescription().setEnabled(false);
        dataChart01.setDrawGridBackground(false);
        dataChart01.setDrawBarShadow(false);
        dataChart01.setHighlightFullBarEnabled(true);

        //获取图例
        Legend legend = dataChart01.getLegend();
        //是否开启设置图例
        legend.setEnabled(true);
        //设置图例文字大小
        legend.setTextSize(TypedValue.COMPLEX_UNIT_PX,12);
        //设置图例文字颜色
        legend.setTextColor(Color.BLUE);
        //如果设置为true，那么当图例过多或者过长一行显示不下的时候就会自适应换行
        legend.setWordWrapEnabled(true);
        //设置表格的最大相对大小，默认为0.95f(95%)
        legend.setMaxSizePercent(0.95f);
        //设置图例位置
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        //设置图例形状:如SQUARE、CIRCLE、LINE、DEFAULT
        legend.setForm(Legend.LegendForm.CIRCLE);
        //设置图例XY方向的间隔宽度
        legend.setXEntrySpace(20f);
        legend.setYEntrySpace(100f);
        //设置图例标签到文字之间的距离
        legend.setFormToTextSpace(20f);
        //设置文本包装
        legend.setWordWrapEnabled(true);
        dataChart01.setExtraBottomOffset(long01*10);
        dataChart01.animateX(2000);



    }
    /**
     * 展示数据
     */
    private void showDataOnChart02(List<String> valuesX ,List<Float> lineData,List<Integer> barData01,List<Integer> barData02,float max,int long02) {
        //绘制图表数据
        data02 = new CombinedData();
        //设置折线图数据
        data02.setData(getLineData(lineData));
        //设置柱状图数据
        data02.setData(getBarData(barData01,barData02));
        dataChart02.setData(data02);
        //设置横坐标数据
        setAxisXBottom02(valuesX);
        //设置右侧纵坐标数据
        setAxisYRight02();
        //设置左侧纵坐标数据
        setAxisYLeft02( max);
        dataChart02.setTouchEnabled(false);
        dataChart02.getDescription().setEnabled(false);
        dataChart02.setDrawGridBackground(false);
        dataChart02.setDrawBarShadow(false);
        dataChart02.setHighlightFullBarEnabled(true);
        //获取图例
        Legend legend = dataChart02.getLegend();
        //是否开启设置图例
        legend.setEnabled(true);
        //设置图例文字大小
        legend.setTextSize(TypedValue.COMPLEX_UNIT_PX,12);
        //设置图例文字颜色
        legend.setTextColor(Color.BLUE);
        //如果设置为true，那么当图例过多或者过长一行显示不下的时候就会自适应换行
        legend.setWordWrapEnabled(true);
        //设置表格的最大相对大小，默认为0.95f(95%)
        legend.setMaxSizePercent(1.2f);
        //设置图例位置
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        //设置图例形状:如SQUARE、CIRCLE、LINE、DEFAULT
        legend.setForm(Legend.LegendForm.CIRCLE);
        //设置图例XY方向的间隔宽度
        legend.setXEntrySpace(20f);
        legend.setYEntrySpace(100f);
        //设置图例标签到文字之间的距离
        legend.setFormToTextSpace(20f);
        //设置文本包装
        legend.setWordWrapEnabled(true);
        dataChart02.setExtraBottomOffset(long02*10);
        dataChart02.animateX(2000);
    }
    /**
     * 设置横坐标数据
     */
    private void setAxisXBottom01(List<String> valuesX ) {
       /* List<String> valuesX = new ArrayList<>();
        String date[] = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        for (int i = 0;i < date.length;i++){
            valuesX.add(date[i]);
        }*/
        XAxis bottomAxis = dataChart01.getXAxis();
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setCenterAxisLabels(true);
        bottomAxis.setValueFormatter(new IndexAxisValueFormatter(valuesX));
        bottomAxis.setAxisMinimum(data01.getXMin());
        bottomAxis.setAxisMaximum(data01.getXMax() + 0.5f);
        bottomAxis.setLabelCount(valuesX.size());
        bottomAxis.setAvoidFirstLastClipping(false);

        /**
         * 设置X轴文字顺时针旋转角度
         */
        bottomAxis.setTextSize(TypedValue.COMPLEX_UNIT_PX,12);
        bottomAxis.setLabelRotationAngle(-60);
    }
    /**
     * 设置横坐标数据
     */
    private void setAxisXBottom02(List<String> valuesX ) {
       /* List<String> valuesX = new ArrayList<>();
        String date[] = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        for (int i = 0;i < date.length;i++){
            valuesX.add(date[i]);
        }*/
        XAxis bottomAxis = dataChart02.getXAxis();
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setCenterAxisLabels(true);
        bottomAxis.setValueFormatter(new IndexAxisValueFormatter(valuesX));
        bottomAxis.setAxisMinimum(data02.getXMin());
        bottomAxis.setAxisMaximum(data02.getXMax() + 0.5f);
        bottomAxis.setLabelCount(valuesX.size());
        bottomAxis.setAvoidFirstLastClipping(false);

        /**
         * 设置X轴文字顺时针旋转角度
         */
        bottomAxis.setTextSize( TypedValue.COMPLEX_UNIT_PX,12);
        //bottomAxis.setl
        bottomAxis.setLabelRotationAngle(-60);
    }

    /**
     * 设置右侧纵坐标数据
     */
    private void setAxisYRight01() {
        YAxis right = dataChart01.getAxisRight();
        right.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat format = new DecimalFormat("0.00%");
                String rate=format.format(value);
                return rate;
            }
        });
        right.setAxisMaximum(1.01f);
        right.setAxisMinimum(0);
        right.setDrawGridLines(false);
    }
    /**
     * 设置右侧纵坐标数据
     */
    private void setAxisYRight02() {
        YAxis right = dataChart02.getAxisRight();
        right.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat format = new DecimalFormat("0.00%");
                String rate=format.format(value);
                return rate;
            }
        });
        right.setAxisMaximum(1.01f);
        right.setAxisMinimum(0);
        right.setDrawGridLines(false);
    }

    /**
     * 设置左侧纵坐标数据
     */
    private void setAxisYLeft01(float max) {
        YAxis left = dataChart01.getAxisLeft();
        /*left.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int value1= (int) value;
                return value1 + "";
            }
        });*/
        left.setAxisMaximum(max+1);
        left.setAxisMinimum(0);
        left.setDrawGridLines(true);
    }
    /**
     * 设置左侧纵坐标数据
     */
    private void setAxisYLeft02(float max) {
        YAxis left = dataChart02.getAxisLeft();
       /* left.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "";
            }
        });*/
        left.setAxisMaximum(max+1);
        left.setAxisMinimum(0);
        left.setDrawGridLines(true);
    }

    /**
     * 设置折线图绘制数据
     * 温度
     * @return
     */
    public LineData getLineData(List<Float> list) {
        LineData lineData = new LineData();
        List<Entry> customCounts = new ArrayList<>();
        //float[] data = {2.0f, 2.2f, 3.3f, 4.5f, 6.3f, 10.2f, 20.3f, 23.4f, 23.0f, 16.5f, 12.0f, 6.2f};
        //人数
        for (int i = 0; i < list.size(); i++) {
            customCounts.add(new Entry(i + 0.5f,list.get(i)));
        }
        LineDataSet lineDataSet = new LineDataSet(customCounts,"关闭率");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setColor(Color.parseColor("#35A0EE"));
        lineDataSet.setCircleColor(Color.parseColor("#35A0EE"));
        lineDataSet.setCircleRadius(2);
        lineDataSet.setLineWidth(1);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.parseColor("#35A0EE"));
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format;
                if(value<=0){
                    format= new DecimalFormat("0%");
                }else{
                    format = new DecimalFormat("0.00%");
                }

                String rate=format.format(value);
                return rate;
            }
        });

        lineData.addDataSet(lineDataSet);
        return lineData;
    }
    /**
     * 设置柱状图绘制数据
     *
     * @return
     */
    public BarData getBarData(List<Integer> z,List<Integer> j) {
        BarData barData = new BarData();
        //总量金额
        List<BarEntry> amounts = new ArrayList<>();
        //float z[] = {2.0f, 4.9f, 7.0f, 23.2f, 25.6f, 76.7f, 135.6f, 162.2f, 32.6f, 20.0f, 6.4f, 3.3f};
        //平均金额
        List<BarEntry> averages = new ArrayList<>();
        //float j[] = {2.6f, 5.9f, 9.0f, 26.4f, 28.7f, 70.7f, 175.6f, 182.2f, 48.7f, 18.8f, 6.0f, 2.3f};
        //添加数据
        for (int i = 0; i < z.size(); i++) {
            amounts.add(new BarEntry(i,z.get(i)));
            averages.add(new BarEntry(i,j.get(i)));
        }
        //设置总数的柱状图
        BarDataSet amountBar = new BarDataSet(amounts,"已关闭");
        amountBar.setAxisDependency(YAxis.AxisDependency.LEFT);
        amountBar.setColor(Color.parseColor("#00EE00"));
        //设置平均的柱状图
        BarDataSet averageBar = new BarDataSet(averages,"未关闭");
        averageBar.setAxisDependency(YAxis.AxisDependency.LEFT);
        averageBar.setColor(Color.parseColor("#FFAA00"));
        amountBar.setValueTextSize(10);
        averageBar.setValueTextSize(10);
        amountBar.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("0");
                String rate=format.format(value);
                return rate;
            }
        });
        averageBar.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat format = new DecimalFormat("0");
                String rate=format.format(value);
                return rate;
            }
        });


        barData.addDataSet(amountBar);
        barData.addDataSet(averageBar);
        //设置柱状图显示的大小
        float groupSpace = 0.06f;
        float barSpace = 0.02f;
        float barWidth = 0.45f;
        barData.setBarWidth(barWidth);
        barData.groupBars(0, groupSpace, barSpace);
        return barData;
    }



}

