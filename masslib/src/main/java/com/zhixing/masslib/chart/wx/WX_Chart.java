package com.zhixing.masslib.chart.wx;

import android.graphics.Color;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.base.zhixing.www.widget.XEditText;
import com.github.mikephil.charting.charts.CombinedChart;
import com.base.zhixing.www.inter.SelectDoubleTime;
import com.base.zhixing.www.widget.ChangeDoubleTime;
import com.zhixing.masslib.R;
import com.zhixing.masslib.bean.WxBean;
import com.zhixing.masslib.chart.CombinedChartManager;
import com.zhixing.masslib.inter.DialogClose;
import com.zhixing.masslib.widget.ShowMassPie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WX_Chart extends BaseActvity {


    @Override
    public int getLayoutId() {
        return R.layout.wx_cahrt;
    }
    private CombinedChartManager combineChartManager;
    @Override
    public void process(Message msg) {
            switch (msg.what){
                case 0:
                    int in = msg.arg1;
                    String si = situationArrt.get(in);

                    ShowMassPie pie = new ShowMassPie(WX_Chart.this,si,"0","1",startTime,endTime,type);
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
                        combineChartManager = new CombinedChartManager(WX_Chart.this,getHandler(),mChart);
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

    private int type =0;//饼状图类型 0根据现象获取   1根据型号获取

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
            type = 0;
        }else if (INDEX==1){
            edit.setHint("请输入生产工单");
            edit.setVisibility(View.VISIBLE);
            left_v.setText("不良数量");
            right_v.setText("累计占比");
            type = 0;
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
                ChangeDoubleTime doubleTime = new ChangeDoubleTime(WX_Chart.this);
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
        params.put("AppCode", "QC");
        params.put("ApiCode", "GetRapairInfo");
        params.put("WorkNo",edit.getTextEx().intern());//WORK015149
        params.put("productCode","");
        params.put("picNO","");
        params.put("ExceptionName","");//不良现象
        params.put("RepairName","");//不良原因
        params.put("RapairDate","");
        params.put("beginDate",startTime);
        params.put("endDate",endTime);
        params.put("TenantId",SharedPreferencesTool.getMStool(WX_Chart.this).getTenantId());
//        params.put("TenantId",SharedPreferencesTool.getMStool(context).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(WX_Chart.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                situationArrt.clear();
                countArrt.clear();
                lvArrt.clear();




                JSONArray jsonArray = null;
                try {
                    JSONObject object = jsonObject.getJSONObject("repairproductList");
                    jsonArray = object.getJSONArray("rows");
                    int len = jsonArray.length();
                    Map<String,Integer> temp = new HashMap<>();//统计所有
                    float total = 0;
                    for(int i=0;i<len;i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                       String key =  o.getString("ExceptionName");
                        int oo= o.getInt("RepairCount");
                        total = total+oo;
                       if(temp.containsKey(key)){
                          //修改
//                           temp.get(key).add(oo);
                           temp.put(key,temp.get(key)+oo);
                       }else{
                           //新增

                           temp.put(key,oo);
                       }
                    }
                   Set set =  temp.keySet();
                    Iterator<String> it = set.iterator();

                    ArrayList<WxBean> its = new ArrayList<>();

                    while(it.hasNext()){
                        String key  =   it.next();
                        Integer c = temp.get(key);
                        P.c(key+"=="+c.toString());
                        WxBean b = new WxBean();
                        b.setName(key);
                        b.setCount(c);
                        its.add(b);
                    }


                    //排序
//                    int sel = 1;
                    Collections.sort(its,new Co());
                    for(int i=0;i<its.size();i++){
                        float cco=0;
                        for(int j=0;j<1+i;j++){
                            cco = cco+its.get(j).getCount();
                        }
                        WxBean b = its.get(i);
                        situationArrt.add(b.getName());
                        countArrt.add(Integer.valueOf(b.getCount()).floatValue());
                        lvArrt.add(cco/total*100);

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
    class Co implements Comparator {

        @Override
        public int compare(Object o, Object t1) {
            WxBean c = (WxBean) o;
            WxBean c1 = (WxBean) t1;
            if( c.getCount()>c1.getCount()){
                return -1;
            }
            return 1;
        }
    }
    /**
     * 检验是否留下数据
     * @param it
     * @return
     */
    private int isColl(ArrayList<Integer> it){
        int k = 0;
        for(int i=0;i<it.size();i++){
            if(it.get(i)==0){
                k = k+1;
            }
        }
        return  k;
    }


}
