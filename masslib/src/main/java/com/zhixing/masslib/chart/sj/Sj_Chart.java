package com.zhixing.masslib.chart.sj;

import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.SelectDoubleTime;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.ChangeDoubleTime;
import com.base.zhixing.www.widget.XEditText;
import com.github.mikephil.charting.charts.CombinedChart;
import com.zhixing.masslib.R;
import com.zhixing.masslib.chart.CombinedChartManager;
import com.zhixing.masslib.chart.cj.Cj_Chart;
import com.zhixing.masslib.chart.qj.QJ_Chart;
import com.zhixing.masslib.inter.DialogClose;
import com.zhixing.masslib.util.Common;
import com.zhixing.masslib.widget.ShowMassPie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sj_Chart extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.sj_cahrt;
    }
    private    CombinedChartManager combineChartManager;
    @Override
    public void process(Message msg) {
        switch (msg.what){
            case 0:
                int in = msg.arg1;
                String si = situationArrt.get(in);

                ShowMassPie pie = new ShowMassPie(Sj_Chart.this,si,"0","1",startTime,endTime,type);
                pie.setClose(new DialogClose() {
                    @Override
                    public void close() {
                        if(combineChartManager!=null){
                            combineChartManager.setCanClick(true);
                        }
                    }
                });
                pie.showSheet();
                break;
            case 1:
                if(situationArrt.size()!=0){

                    combineChartManager = new CombinedChartManager(Sj_Chart.this,getHandler(),mChart);
                    combineChartManager.type(type);
                    combineChartManager.showCombinedChart(situationArrt, countArrt, lvArrt,
                            "累计占比", "不良数量",Color.RED,Color.BLACK);
                }

                break;
        }

    }
    private String startTime,endTime;
    private XEditText edit;
    private int INDEX = -1;
    private TextView select_time,tetle_text,tetle_tv_ok,left_v,right_v;
    private CombinedChart mChart;
    private int type =0;//饼状图类型 0根据现象获取   1根据型号获取
    @Override
    public void initLayout() {
        mChart = findViewById(R.id.chart);
        edit = findViewById(R.id.edit);
        INDEX = getIntent().getIntExtra("index",-1);
        left_v = findViewById(R.id.left_v);
        right_v = findViewById(R.id.right_v);
        if(INDEX==0){
            edit.setVisibility(View.GONE);
            left_v.setText("不良率");
            right_v.setText("累计占比");


        }
        tetle_tv_ok = findViewById(R.id.tetle_tv_ok);
        tetle_tv_ok.setVisibility(View.VISIBLE);
        tetle_tv_ok.setText("显示数据");
        tetle_text =  findViewById(R.id.tetle_text);
        tetle_text.setText(getIntent().getStringExtra("titleName"));
        select_time = findViewById(R.id.select_time);
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDoubleTime doubleTime = new ChangeDoubleTime(Sj_Chart.this);
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
    private ArrayList<String> situationArrt = new ArrayList<>();
    private ArrayList<Float> countArrt = new ArrayList<>();
    private ArrayList<Float> lvArrt = new ArrayList<>();
    private void loadData(){
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("ApiCode", "GetCheckPartNumber");
        params.put("startDate",startTime);
        params.put("endDate",endTime);
        httpPostSONVolley(SharedPreferencesTool.getMStool(Sj_Chart.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                situationArrt.clear();
                countArrt.clear();
                lvArrt.clear();
                try {
                    JSONArray array0 = jsonObject.getJSONArray("situationArrt");
                    for(int i=0;i<array0.length();i++){
                        situationArrt.add(array0.getString(i));
                    }
                    JSONArray array1 = jsonObject.getJSONArray("noLvArrt");
                    for(int i=0;i<array1.length();i++){
                        countArrt.add((float) array1.getInt(i));
                    }
                    JSONArray array2 = jsonObject.getJSONArray("lvArrt");
                    for(int i=0;i<array2.length();i++){
                        lvArrt.add((float) array2.getInt(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getHandler().sendEmptyMessage(1);
            }

            @Override
            public void error(VolleyError error) {

            }
        });
    }
}
