package com.shuben.zhixing.www.mes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.gudong.drawable.OneDrawable;

import static com.nightonke.boommenu.Util.setDrawable;

public class ProductionActivity extends BaseActvity implements View.OnClickListener{
    private LinearLayout lay_back;
    private Button bnt_date;
    private TextView tx_date;
    private String myDate;
    private NiceSpinner niceSpinner;
    private List<String> list;
    private List<String>  listId;
    private LoadingDailog dialog;//加载动画
    private int index=0;
    private TextView tx_item01,tx_item02,tx_item03,tx_item04,tx_item05,tx_item06,
                      tx_item07, tx_item08,tx_item09,tx_item10,tx_item11,tx_item12,
                      tx_item13,tx_item14,tx_item15,tx_item16,tx_item17;

    private CombinedChart dataChart01;//图表

    private CombinedData data01;
    private List<String> valuesX01;
    private List<Integer> close01;
    private List<Integer> Unclose01;
    private List<Float> rate01;


    @Override
    public int getLayoutId() {
        return R.layout.activity_production;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {
        initView();
        initLine();
    }

    private void initLine() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetLineList");
        params.put("TenantId", SharedPreferencesTool.getMStool(ProductionActivity.this).getTenantId());
        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "EPS");
            mJson.put("ApiCode", "GetLineList");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(ProductionActivity.this).getTenantId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****加载线体****",mJson.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(ProductionActivity.this).getIp(),
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                //Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list=new ArrayList<String>();
                    listId=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        int Seq=jData.getInt("Seq");
                        if(Seq>0){
                            list.add(jData.getString("LineName"));
                            listId.add(jData.getString("LineCode"));
                        }

                    }

                       if(listId.size()>0){
                           initData(myDate,listId.get(index));
                           initData02(listId.get(index));
                       }

                        niceSpinner.attachDataSource(list);

                    //niceSpinner.attachDataSource(list);

                    //loadPatrolTask(listId.get(0));
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
    private void initData(String Date,String LineCode) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "MiniMes");
        params.put("ApiCode", "GetCurDataAPI");
        params.put("TenantId", SharedPreferencesTool.getMStool(ProductionActivity.this).getTenantId());
        params.put("Date", Date);
        params.put("LineCode", LineCode);

        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "MiniMes");
            mJson.put("ApiCode", "GetCurDataAPI");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(ProductionActivity.this).getTenantId());
            mJson.put("Date", Date);
            mJson.put("LineCode", LineCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****生产状态****",mJson.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(ProductionActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKK生产状态KKK", " " + jsonobj.toString());
                dialog.dismiss();

                try {
                    JSONArray jArray=jsonobj.getJSONArray("DataTable");
                    Log.e("&&jArray&&",jArray.toString());
                    JSONObject jData;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String ProductModel=jData.getString("ProductModel");//产品型号
                        String PlanOutput=jData.getString("PlanOutput");//计划产量
                        String Qty=jData.getString("Qty");//产量
                        String Differ=jData.getString("Differ");//差异
                        String Rate=jData.getString("Rate");//达成率
                        String PlanPerson=jData.getString("PlanPerson");//计划人数
                        String CurPerson=jData.getString("CurPerson");//实际人数
                        String DifferPerson=jData.getString("DifferPerson");//差异人数
                        String PlanCT=jData.getString("PlanCT");//计划节拍
                        String CurCT=jData.getString("CurCT");//实际节拍
                        String PlanUPH=jData.getString("PlanUPH");//计划UPH
                        String CurUPH=jData.getString("CurUPH");//实际UPH
                        tx_item01.setText(list.get(index));
                        tx_item02.setText(PlanPerson);
                        tx_item03.setText(DifferPerson);
                        tx_item04.setText("");
                        tx_item05.setText(CurPerson);

                        tx_item06.setText("");
                        tx_item07.setText(PlanOutput);
                        tx_item08.setText(Differ);
                        tx_item09.setText("");
                        tx_item10.setText(Qty);
                        tx_item11.setText(Rate);

                        tx_item12.setText(PlanCT);
                        tx_item13.setText(PlanUPH);
                        tx_item14.setText("");
                        tx_item15.setText(CurCT);
                        tx_item16.setText(CurUPH);
                        tx_item17.setText("");




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
    private void initData02(String LineCode) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "MiniMes");
        params.put("ApiCode", "GetChartDataAPI");
        params.put("TenantId", SharedPreferencesTool.getMStool(ProductionActivity.this).getTenantId());
        params.put("LineCode", LineCode);

        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "MiniMes");
            mJson.put("ApiCode", "GetChartDataAPI");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(ProductionActivity.this).getTenantId());
            mJson.put("LineCode", LineCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****生产状态图表****",mJson.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(ProductionActivity.this).getIp(),
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKK生产状态图表KKK", " " + jsonobj.toString());
                dialog.dismiss();

                try {
                    JSONArray jArray=jsonobj.getJSONArray("DataTable");
                    Log.e("&&jArray&&",jArray.toString());
                    JSONObject jData;
                    valuesX01=new ArrayList<String>();
                    close01=new ArrayList<Integer>();
                    Unclose01=new ArrayList<Integer>();
                    rate01=new ArrayList<Float>();
                    float max1=0;
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        int Qty=jData.getInt("Qty");//产量
                        int PlanQty=jData.getInt("PlanQty");//差异
                        String Rate=jData.getString("Rate");//达成率
                        String Station=jData.getString("Station");//计划人数
                        valuesX01.add(Station);
                        close01.add(Qty);
                        Unclose01.add(PlanQty);
                        if(PlanQty>0){
                            float rr=(Qty/PlanQty);
                            rate01.add(rr);
                        }else{
                            rate01.add(0f);
                        }
                        if(max1<PlanQty){
                            max1=PlanQty;
                        }




                    }
                    showDataOnChart01(valuesX01,rate01,close01,Unclose01,max1);

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
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        myDate=DateUtil.getInstance().getDateOfToDay();
        lay_back=(LinearLayout)findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        bnt_date=(Button)findViewById(R.id.bnt_date);
        bnt_date.setOnClickListener(this);
        setDrawable(bnt_date, OneDrawable.createBgDrawable(this, R.mipmap.ic_arrow_drop_down_black_24dp));
        tx_date=(TextView) findViewById(R.id.tx_date);
        tx_date.setText(DateUtil.getInstance().getDateOfToDay());

        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner01);
        niceSpinner.setTextColor(Color.rgb(22,155,213));
        niceSpinner.setTextSize(18);
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index=position;
                initData(myDate,listId.get(index));
                initData02(listId.get(index));
            }
        });
        tx_item01= (TextView) findViewById(R.id.tx_item01);
        tx_item02= (TextView) findViewById(R.id.tx_item02);
        tx_item03= (TextView) findViewById(R.id.tx_item03);
        tx_item04= (TextView) findViewById(R.id.tx_item04);
        tx_item05= (TextView) findViewById(R.id.tx_item05);
        tx_item06= (TextView) findViewById(R.id.tx_item06);
        tx_item07= (TextView) findViewById(R.id.tx_item07);
        tx_item08= (TextView) findViewById(R.id.tx_item08);
        tx_item09= (TextView) findViewById(R.id.tx_item09);
        tx_item10= (TextView) findViewById(R.id.tx_item10);
        tx_item11= (TextView) findViewById(R.id.tx_item11);
        tx_item12= (TextView) findViewById(R.id.tx_item12);
        tx_item13= (TextView) findViewById(R.id.tx_item13);
        tx_item14= (TextView) findViewById(R.id.tx_item14);
        tx_item15= (TextView) findViewById(R.id.tx_item15);
        tx_item16= (TextView) findViewById(R.id.tx_item16);
        tx_item17= (TextView) findViewById(R.id.tx_item17);


        dataChart01=(CombinedChart)findViewById(R.id.lay01);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.bnt_date:
                setDate();
                break;
            default:

                break;
        }
    }
    private void setDate() {
        Calendar calendar= Calendar.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductionActivity.this);
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.date_dialog02, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);

        //设置日期简略显示 否则详细显示 包括:星期\周
        datePicker.setCalendarViewShown(false);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        //设置date布局
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("%d-%02d-%02d",
                        datePicker.getYear(),
                        datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth())
                );
                tx_date.setText(sb);
                myDate= sb.toString();
                initData(myDate,listId.get(index));
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

    /**
     * 展示数据
     */
    private void showDataOnChart01(List<String> valuesX ,List<Float> lineData,List<Integer> barData01,List<Integer> barData02,float max) {
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
       /* Legend legend = dataChart01.getLegend();
        legend.setEnabled(false);*/
        dataChart01.animateX(2000);



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
    public BarData getBarData(List<Integer> z, List<Integer> j) {
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
