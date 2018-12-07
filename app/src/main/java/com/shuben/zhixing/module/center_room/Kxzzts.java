package com.shuben.zhixing.module.center_room;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.shuben.zhixing.module.center_room.compara.KxzztCompara;
import com.shuben.zhixing.module.center_room.custom.MyMarkerView;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.common.Common;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.TimeThread;
import com.base.zhixing.www.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Kxzzts extends BaseActvity {

    @Override
    public void process(Message msg) {
            switch (msg.what){
                case 2:
                    INDEX = (INDEX+1)%chartTs.size();
                    P.c("展示"+chartTs.get(INDEX).get("TITLE"));
                    loadData();
                    break;
                case 1:
                  Map<String,String> msp =   chartTs.get(INDEX%chartTs.size());
                    y_tag.setText(msp.get("TIP"));
                    title.setText(msp.get("TITLE"));
                    if(INDEX==2){
                        //特殊处理
                        addLimit("%");

                      //  Collections.sort(temps,new JfdclCompara());
                    }else{
                        addLimit("");

                    }
                    leftAxis.setValueFormatter(new IndexAxisValueFormatter(){
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            if(INDEX==2){
                                //百分比格式化
                                DecimalFormat format = new DecimalFormat("0%");
                                //这里和解析处理成对应关系
                                String rate=format.format(value/100);
                                return rate;

                            }else{
                                DecimalFormat format = new DecimalFormat("0");
                                //这里和解析处理成对应关系
                                String rate=format.format(value);
                                return rate;
                            }

                        }
                    });
                    if(temps.size()!=0){
                        Collections.sort(temps,new KxzztCompara());
                        P.c(temps.get(0)+"最大值");
                        //  xAxis.setAxisMaximum(values.get(0).getY());
                        P.c(months.size()+"==="+values.size());
                        xAxis.setLabelCount(temps.size()>15?15:temps.size());
                        xAxis.setAxisMaximum(temps.size());
                        leftAxis.setAxisMaximum(temps.get(0)<targetValue?targetValue:temps.get(0));

                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
//                            P.c("数据长度"+(int)value);
                                String result =null;
                                try {
                                    result  = months.get((int)value);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return "";
                                }
                                return result;
                            }
                        });
                        setData();
                        lineChart.invalidate();
                    }

                    P.c("countDownTimer"+(countDownTimer==null));
                    if(countDownTimer==null){
                        countDownTimer =new CountDownTimer(1000*3,1) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                countDownTimer = null;
                                getHandler().sendEmptyMessage(2);
                            }
                        };

                    }
                    //开启循环
                    countDownTimer.start();
                    break;
            }
    }
    private CountDownTimer countDownTimer;

    //设置最大最小标线
    private void addLimit(String tip){
        LimitLine ll2 = new LimitLine(targetValue, String.valueOf(targetValue+tip));
        ll2.setLineWidth(1);
        ll2.setLineColor(getResources().getColor(R.color.green));
        ll2.disableDashedLine();
//        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(TypedValue.COMPLEX_UNIT_PX,10f);
        ll2.setTextColor(getResources().getColor(R.color.green));

//        ll2.setTypeface(tf);

        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll2);
    }

    // get the legend (only possible after setting data)
    //比例图标识显示
    private  void addLegend(){
        Legend l = lineChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.NONE);
    }



    private LineChart lineChart;
    private YAxis leftAxis;
    private  XAxis xAxis;
    private TextView times;
    private TextView y_tag,title;
    @Override
    public void initLayout() {
        initChartT();
        setStatus(-1);
        times =findViewById(R.id.times);
        TimeThread timeThread = new TimeThread(times);
       // timeThread.setmHandler(getHandler());
        timeThread.start();
        title = findViewById(R.id.title);
        y_tag = findViewById(R.id.y_tag);
        lineChart = findViewById(R.id.line_chart);
//        lineChart.setOnChartGestureListener(gestureListener);
        lineChart.setOnChartValueSelectedListener(selectedListener);
        lineChart.setDrawGridBackground(false);//图表背景
        lineChart.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));//看板背景
        //是否显示边界
        lineChart.setDrawBorders(false);

//        lineChart.getDescription().setEnabled(false);
       Description des =  lineChart.getDescription();
        des.setText(Common.FACTORY_TAG);
        des.setTextColor(Color.WHITE);
      //  lineChart.setDescription(des);
        lineChart.setTouchEnabled(true);

        lineChart.setFitsSystemWindows(false);
        lineChart.setAutoScaleMinMaxEnabled(true);
        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
//        lineChart.setDragXEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(lineChart); // For bounds control
        lineChart.setMarker(mv); // Set the marker to the chart
        //设置X在下方
        xAxis = lineChart.getXAxis();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMinimum(-0.3f);
        xAxis.setGranularity(1f);

        xAxis.setTextColor(Color.WHITE);


        //制图
        leftAxis = lineChart.getAxisLeft();
//        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0);
        leftAxis.setTextColor(Color.WHITE);

        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(false);

        lineChart.getAxisRight().setEnabled(false);

       // setData();
        lineChart.animateXY(2000,2000);
//        lineChart.animateX(2500);
        //mChart.invalidate();
        leftAxis.setDrawTopYLabelEntry(true);
        addLegend();
        loadData();

    }
    ArrayList<Entry> values = new ArrayList<Entry>();
    ArrayList<Float> temps = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();
    private void setData() {
      /*  for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range) + 3;
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));
        }*/
        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, null);
            set1.setDrawIcons(false);
//            set1.disableDashedLine();
            set1.setHighlightEnabled(true);
            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.WHITE);//线条颜色
            set1.setValueTextColor(Color.WHITE);//文字颜色
            set1.setCircleColor(Color.WHITE);//圆点颜色

            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);//渐变
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }

                    set1.setValueFormatter(new IValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

                            if(INDEX==2){
                                DecimalFormat format = new DecimalFormat("0.0%");
                                //这里和解析处理成对应关系
                                String  rate=format.format(value/100);
                                return  rate;
                            }else{
                                String   rate =  String.valueOf(value);
                                return  rate;
                            }


                        }
                    });

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            lineChart.setData(data);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.kxzzts_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private OnChartGestureListener gestureListener = new OnChartGestureListener() {
        @Override
        public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        }

        @Override
        public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        }

        @Override
        public void onChartLongPressed(MotionEvent me) {

        }

        @Override
        public void onChartDoubleTapped(MotionEvent me) {

        }

        @Override
        public void onChartSingleTapped(MotionEvent me) {

        }

        @Override
        public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

        }

        @Override
        public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

        }

        @Override
        public void onChartTranslate(MotionEvent me, float dX, float dY) {

        }
    };
    private OnChartValueSelectedListener selectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {

        }

        @Override
        public void onNothingSelected() {

        }
    };

    private volatile int INDEX = 2;//当前展示图标序号
    private ArrayList<Map<String,String>> chartTs  = new ArrayList<>();
    private void initChartT(){
        chartTs.clear();
        addT("GetTurnOverDay","天数","库存周转天数");
        addT("GetDeliveryWeekData","天数","交付周期");
        addT("GetDeliveryRateData","百分比","交付达成率");
    }
    private void addT(String tag,String tip,String title){
        Map<String,String> map = new HashMap<>();
        map.put("TAG",tag);
        map.put("TIP",tip);
        map.put("TITLE",title);
        chartTs.add(map);
    }
    private float targetValue;
    private void loadData(){
        //http://192.168.2.253:8086/api/Kanban/TurnOverDays/GetTurnOverDaysData?workShopCode=OL01SC&TenantId=00000000-0000-0000-0000-000000000001
        Map<String,String> params = new HashMap<>();
        params.put("AppCode","Kanban");
        params.put("ApiCode",chartTs.get(  INDEX).get("TAG"));
        params.put("workShopCode",SharedPreferencesTool.getMStool(Kxzzts.this).getString("workshop_code"));
        params.put("TenantId",SharedPreferencesTool.getMStool(Kxzzts.this).getTenantId());

            httpPostVolley(SharedPreferencesTool.getMStool(Kxzzts.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
                @Override
                public void success(JSONObject jsonObject) {
                    JSONArray jsonArray = null;
                    if(INDEX==2){
                        try {
                            jsonArray = jsonObject.getJSONArray("message");
                            int len = jsonArray.length();
                            targetValue = Float.parseFloat(jsonObject.getString("lstTargetValue"));
                            values.clear();
                            months.clear();
                            temps.clear();
                            for(int i=len-1;i>=0;i--){
                                int index = i;
                                JSONObject object = jsonArray.getJSONObject(index);
                              float  mubiao = Float.parseFloat(object.getString("Target"));
                              float val = Float.parseFloat(object.getString("Actual"))/mubiao*100;
                              P.c(object.getString("Month")+val);
                                values.add(new Entry(len-i-1, val, getResources().getDrawable(R.drawable.star)));
                                temps.add(val);
                                String t = object.getString("Month");
                                months.add(t.substring(t.indexOf("-")+1,t.lastIndexOf("-"))+"-"+object.getString("weekCode"));
                            }

                            getHandler().sendEmptyMessage(1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //特殊处理
                        return;
                    }
                    try {
                        jsonArray = jsonObject.getJSONArray("datatable");
                        int len = jsonArray.length();
                        values.clear();
                        months.clear();
                        temps.clear();


                            for(int i=0;i<len;i++){
                                JSONObject object  = jsonArray.getJSONObject(i);
                                targetValue = Float.parseFloat(object.getString("targetValue"));
                                values.add(new Entry(i, Float.parseFloat(object.getString("TurnDays")), getResources().getDrawable(R.drawable.star)));
                                months.add(object.getString("Date"));
                                temps.add(Float.parseFloat(object.getString("TurnDays")));
                            }


                        getHandler().sendEmptyMessage(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void error(VolleyError error) {

                }
            },false);
    }
}
