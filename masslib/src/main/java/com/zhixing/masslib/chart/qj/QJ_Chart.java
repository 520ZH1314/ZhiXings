package com.zhixing.masslib.chart.qj;

import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.widget.XEditText;
import com.github.mikephil.charting.charts.CombinedChart;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.inter.SelectDoubleTime;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.ChangeDoubleTime;
import com.zhixing.masslib.R;
import com.zhixing.masslib.chart.CombinedChartManager;
import com.zhixing.masslib.inter.DialogClose;
import com.zhixing.masslib.util.Common;
import com.zhixing.masslib.widget.ShowMassPie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QJ_Chart extends BaseActvity {


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

                    ShowMassPie pie = new ShowMassPie(QJ_Chart.this,si,"0","1",startTime,endTime,type);
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
                    //总体不良柏拉图
                    if(situationArrt.size()!=0){
                        combineChartManager = new CombinedChartManager(QJ_Chart.this,getHandler(),mChart);
                        combineChartManager.showCombinedChart(situationArrt, countArrt, lvArrt,
                                "累计占比", "不良数量",Color.RED,Color.BLACK);
                    }

                    break;
            }
    }
    private CombinedChart mChart;

    private ImageView tetle_back;

    private TextView select_time,tetle_text,tetle_tv_ok,left_v,right_v;
    private String startTime,endTime;
    private XEditText edit;
    private int INDEX = -1;
    private String firstP,secondP,thridP;//解析代号
    private int type =0;//饼状图类型 0根据现象获取   1根据型号获取
    private void add(String p,String p1,String p2){
        this.firstP = p;
        this.secondP = p1;
        this.thridP = p2;
    }

    @Override
    public void initLayout() {
        setStatus(-1);
        edit = findViewById(R.id.edit);
        INDEX = getIntent().getIntExtra("index",-1);
        left_v = findViewById(R.id.left_v);
        right_v = findViewById(R.id.right_v);
        if(INDEX==0){
            edit.setVisibility(View.GONE);
            left_v.setText("不良数量");
            right_v.setText("累计占比");
            add("situationArrt","countArrt","lvArrt");
        }else if (INDEX==1){
            edit.setHint("请输入生产工单");
            edit.setVisibility(View.VISIBLE);
            left_v.setText("不良数量");
            right_v.setText("累计占比");
            add("situationArrt","countArrt","lvArrt");
        }else if (INDEX==2){
            edit.setHint("请输入产品型号");
            edit.setVisibility(View.VISIBLE);
            left_v.setText("不良数量");
            right_v.setText("累计占比");
            add("situationArrt","countArrt","lvArrt");
        }else if(INDEX==3){
            type= 1;
            left_v.setText("不良数量");
            right_v.setText("累计占比");
            edit.setVisibility(View.GONE);
            add("situationArrt","countArrt","lvArrt");
        }else if(INDEX==4){
            type= 1;
            edit.setVisibility(View.GONE);
            left_v.setText("不良率");
            right_v.setText("累计占比");
            add("situationArrt","noLvArrt","lvArrt");
        }





        tetle_tv_ok = findViewById(R.id.tetle_tv_ok);
        tetle_tv_ok.setVisibility(View.VISIBLE);
        tetle_tv_ok.setText("显示数据");
        tetle_text =  findViewById(R.id.tetle_text);
        tetle_text.setText(getIntent().getStringExtra("titleName"));
        mChart = findViewById(R.id.chart);
        select_time = findViewById(R.id.select_time);
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDoubleTime doubleTime = new ChangeDoubleTime(QJ_Chart.this);
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
        //x轴数据


        //名字集合
//        List<String> barNames = new ArrayList<>();
//        barNames.add("直方图一");
//        barNames.add("直方图二");
//        barNames.add("直方图三");
//        barNames.add("直方图四");
        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLACK);

        //竖状图管理类

//        List<String> lineNames = new ArrayList<>();
//        lineNames.add("折线图一");
//        lineNames.add("折线图二");
//        lineNames.add("折线图三");
//        lineNames.add("折线图四");


        //管理类

        tetle_tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });


//        loadData();
    }

    private ArrayList<String> situationArrt = new ArrayList<>();
    private ArrayList<Float> countArrt = new ArrayList<>();
    private ArrayList<Float> lvArrt = new ArrayList<>();
    //加载数据



    private  void loadData(){
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", Common.APPCODE);
        params.put("beginDate",startTime);
        params.put("endDate",endTime);
      switch (INDEX){
          case 0:
              params.put("ApiCode", "GetRandomPltoInfo");
              params.put("CheckType","1");//根据传入的类型判断是全检单还是抽检单
              break;

          case 1:
                  //生产工单
              params.put("ApiCode", "GetRandomPltoInfo");
              params.put("CheckType","1");//根据传入的类型判断是全检单还是抽检单
              params.put("workNo",edit.getTextEx().toString());

              break;
          case 2:
              params.put("ApiCode", "GetRandomPltoInfo");
              params.put("CheckType","1");//根据传入的类型判断是全检单还是抽检单
              params.put("number",edit.getTextEx().toString());

                // 产品型号
              break;
          case 3:
              //不良数据产品型号
              params.put("ApiCode", "GetAllCheckNgNumberInfo");
              params.put("CheckType","1");//根据传入的类型判断是全检单还是抽检单
              params.put("ProductType","0");
              params.put("TenantId",SharedPreferencesTool.getMStool(QJ_Chart.this).getTenantId());
              break;
          case 4:
              params.put("ApiCode", "GetCheckNgCodePercentPart");
              params.put("CheckType","1");//根据传入的类型判断是全检单还是抽检单
              params.put("ProductType","0");
              params.put("TenantId",SharedPreferencesTool.getMStool(QJ_Chart.this).getTenantId());
              break;
      }
//        params.put("TenantId",SharedPreferencesTool.getMStool(context).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(QJ_Chart.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                situationArrt.clear();
                countArrt.clear();
                lvArrt.clear();
                try {
                    JSONArray array0 = jsonObject.getJSONArray(firstP);
                    for(int i=0;i<array0.length();i++){
                        situationArrt.add(array0.getString(i));
                    }
                    JSONArray array1 = jsonObject.getJSONArray(secondP);
                    for(int i=0;i<array1.length();i++){
                        countArrt.add((float) array1.getInt(i));
                    }
                    JSONArray array2 = jsonObject.getJSONArray(thridP);
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
        },false);
    }



}
