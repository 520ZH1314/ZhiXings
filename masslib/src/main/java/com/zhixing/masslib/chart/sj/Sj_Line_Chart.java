package com.zhixing.masslib.chart.sj;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.SelectDoubleTime;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.view.Toasty;
import com.base.zhixing.www.widget.ChangeDoubleTime;
import com.base.zhixing.www.widget.XEditText;
import com.base.zhixing.www.widget.nicespinner.NiceSpinner;
import com.github.mikephil.charting.charts.LineChart;
import com.zhixing.masslib.R;
import com.zhixing.masslib.bean.LineBean;
import com.zhixing.masslib.bean.QC_Reason;
import com.zhixing.masslib.chart.LineManager;
import com.zhixing.masslib.chart.qj.QJ_Line_Chart;
import com.zhixing.masslib.util.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sj_Line_Chart extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.qj_line_chart;
    }

    @Override
    public void process(Message msg) {
            switch (msg.what){

                case 1:
                    lineManager.showLineChart(lineBeans,left_v.getText().toString(),R.color.green);
                    break;

            }
    }
    private int INDEX;
    private LineChart chart;
    private LineManager lineManager;
    private String startTime,endTime;
    private TextView select_time,tetle_text,tetle_tv_ok,left_v,right_v;
    private NiceSpinner spi0,spi1;
    private XEditText edit;
    private ArrayList<QC_Reason> qc_reasons = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> list0 = new ArrayList<>();
    private ArrayList<String> list0V = new ArrayList<>();
    @Override
    public void initLayout() {
        INDEX = getIntent().getIntExtra("index",-1);
        chart = findViewById(R.id.chart);
        lineManager = new LineManager(chart,Sj_Line_Chart.this);
        edit = findViewById(R.id.edit);
        spi0 = findViewById(R.id.spi0);//年月日
        spi1 = findViewById(R.id.spi1);//不良现象
        left_v = findViewById(R.id.left_v);
        right_v = findViewById(R.id.right_v);
        tetle_text =  findViewById(R.id.tetle_text);
        tetle_text.setText(getIntent().getStringExtra("titleName"));
        tetle_tv_ok = findViewById(R.id.tetle_tv_ok);
        tetle_tv_ok.setVisibility(View.VISIBLE);
        tetle_tv_ok.setText("显示数据");
        right_v.setVisibility(View.GONE);
        if(INDEX==1){
            spi1.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            left_v.setText("不良数量");
            right_v.setText("累计占比");
            list0.add("日");
            list0.add("周");
            list0.add("月");
            list0.add("年");
            list0V.add("day");
            list0V.add("week");
            list0V.add("month");
            list0V.add("year");
            spi0.attachDataSource(list0);
        }
        select_time = findViewById(R.id.select_time);
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDoubleTime doubleTime = new ChangeDoubleTime(Sj_Line_Chart.this);
                doubleTime.setSelect(new SelectDoubleTime() {
                    @Override
                    public void select(String start, String end, long stt, long ed) {
                        startTime = start;
                        endTime = end;
                        select_time.setText(start+"至"+end );
                        loadData();
                    }
                });
                doubleTime.showSheet();
            }
        });
    }
    private ArrayList<LineBean> lineBeans = new ArrayList<>();
    private void loadData(){
        Map<String,String> params = new HashMap<>();
        params.put("ApiCode", "GetCheckNGPercent");
        params.put("AppCode", Common.APPCODE);
        params.put("startDate",startTime);
        params.put("endDate",endTime);
        params.put("CountType",list0V.get(spi0.getSelectedIndex()));
        httpPostSONVolley(SharedPreferencesTool.getMStool(Sj_Line_Chart.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    JSONArray array0 = jsonObject.getJSONArray("CountTypes");
                    lineBeans.clear();
                    if(array0.length()==0){
                        Toasty.INSTANCE.showToast(Sj_Line_Chart.this,"暂无数据");
                        return;
                    }
                    for(int i=0;i<array0.length();i++){
                        LineBean b = new LineBean();
                        b.setName(array0.getString(i));
                        lineBeans.add(b);
                    }

                    JSONArray array1 = jsonObject.getJSONArray("Value");
                    for(int i=0;i<array1.length();i++){
                        lineBeans.get(i).setValue(array1.getDouble(i));
                    }
                    getHandler().sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(VolleyError error) {

            }
        });
    }
}
