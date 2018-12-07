package com.shuben.zhixing.module.mass.chart.cj;

import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.P;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.shuben.zhixing.module.mass.bean.LineBean;
import com.shuben.zhixing.module.mass.bean.QC_Reason;
import com.shuben.zhixing.module.mass.chart.LineManager;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.dataBase.MassDB;
import com.base.zhixing.www.inter.SelectDoubleTime;
import com.shuben.zhixing.www.view.NiceSpinner;
import com.base.zhixing.www.widget.ChangeDoubleTime;
import com.shuben.zhixing.www.widget.XEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CjLineOk_Chart extends BaseActvity {
    private int INDEX;

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
            case 2:
                int size = qc_reasons.size();
                list.clear();
                for(int i=0;i<size;i++){
                    list.add(qc_reasons.get(i).getName());
                    P.c("不良"+qc_reasons.get(i).getName());
                }
                spi1.attachDataSource(list);
                list0.add("日");
                list0.add("周");
                list0.add("月");
                spi0.attachDataSource(list0);
                break;
        }
    }
    private LineChart chart;
    private LineManager lineManager;
    private String startTime,endTime;
    private TextView select_time,tetle_text,tetle_tv_ok,left_v,right_v;
    private NiceSpinner spi0,spi1;
    private XEditText edit;
    private ArrayList<QC_Reason> qc_reasons = new ArrayList<>();
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> list0 = new ArrayList<>();
    private void add(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                MassDB.getInstance().getQc_reasons(qc_reasons,0);
                getHandler().sendEmptyMessage(2);
            }
        }.start();
    }

    @Override
    public void initLayout() {
        setStatus(-1);
        INDEX = getIntent().getIntExtra("index",-1);
        chart = findViewById(R.id.chart);
        lineManager = new LineManager(chart,CjLineOk_Chart.this);
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
            edit.setVisibility(View.GONE);
            spi1.setVisibility(View.GONE);
            left_v.setText("合格率");
            right_v.setText("累计占比");
        }
        add();
        select_time = findViewById(R.id.select_time);
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDoubleTime doubleTime = new ChangeDoubleTime(CjLineOk_Chart.this);
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
    private ArrayList<LineBean> linnTemp = new ArrayList<>();
    //加载数据
    private void loadData(){
        Map<String,String> params  = new HashMap<>();
        params.put("AppCode", "QC");
        params.put("ApiCode", "GetRandomPicCheckList");
        params.put("workNO","");//WORK015149
        params.put("productCode","");
        params.put("picNO","");
        params.put("state","-1");//只要合格和不合格
        params.put("beginDate",startTime);
        params.put("endDate",endTime);
        params.put("TenantId",SharedPreferencesTool.getMStool(CjLineOk_Chart.this).getTenantId());

            httpPostSONVolley(SharedPreferencesTool.getMStool(CjLineOk_Chart.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
                @Override
                public void success(JSONObject jsonObject) {
                    //计算


                    JSONArray jsonArray = null;
                    try {
                        jsonArray = jsonObject.getJSONArray("rows");
                        int len = jsonArray.length();
                        Map<String,ArrayList<Integer>> temp = new HashMap<>();//统计所有
                        for(int i=0;i<len;i++){
                            //遍历数据
                            JSONObject o = jsonArray.getJSONObject(i);

                            String key =  o.getString("createTime").split("T")[0];
                            int oo= o.getInt("state");
                            if(temp.containsKey(key)){
                                //修改
                                temp.get(key).add(oo);
                            }else{
                                //新增
                                ArrayList<Integer> p = new ArrayList<>();
                                p.add(oo);
                                temp.put(key,p);
                            }
                        }
                        Set set =  temp.keySet();
                        Iterator<String> it = set.iterator();
                        Map<String,Float> real = new HashMap<>();
                        Map<String,Float> ok = new HashMap<>();
                        Map<String,Float> no = new HashMap<>();
                        while(it.hasNext()){
                            String key  =   it.next();
                            float tps[] = isColl(temp.get(key));
                            ok.put(key,tps[1]);
                            no.put(key,tps[0]);
                            P.c(key+"=="+"合格"+tps[1]+"不良"+tps[0]);
                            if(tps[1]==0){
                                real.put(key,0f);
                            }else{
                                real.put(key,tps[1]/(tps[0]+tps[1]));
                            }
                        }
                        getPerDaysByStartAndEndDate(startTime,endTime,"yyyy-MM-dd",real);

                       switch (spi0.getSelectedIndex()){
                           case 0:
                               //日

                               lineBeans = linnTemp;
                               break;
                           case 1:
                              // getPerDaysByStartAndEndDate(startTime,endTime,"yyyy-MM-dd",real);
                               processZ(ok,no,1);
                               break;
                           case 2:
                             //  getPerDaysByStartAndEndDate(startTime,endTime,"yyyy-MM-dd",real);
                               processZ(ok,no,2);
                               break;
                       }



                    }catch (JSONException e){

                    }

                    getHandler().sendEmptyMessage(1);

                   /* try {
                        JSONArray array0 = jsonObject.getJSONArray("situationArrt");
                        lineBeans.clear();
                        for(int i=0;i<array0.length();i++){
                            LineBean b = new LineBean();
                            b.setName(array0.getString(i));
                            lineBeans.add(b);
                        }

                        JSONArray array1 = jsonObject.getJSONArray("noLvArrt");
                        for(int i=0;i<array1.length();i++){
                            lineBeans.get(i).setValue(array1.getDouble(i));
                        }
                        getHandler().sendEmptyMessage(1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                }

                @Override
                public void error(VolleyError error) {

                }
            });
    }
    //合格和不合格
    private float[] isColl(ArrayList<Integer> it){
        float k = 0;
        float p = 0;
        float []t = new float[2];
        for(int i=0;i<it.size();i++){
            if(it.get(i)==0){
                k = k+1;//不良
            }else{
                p = p+1;//合格
;            }
        }
        t[0] = k;
        t[1] = p;
        return  t;
    }
    public   List<String> getPerDaysByStartAndEndDate(String startDate, String endDate, String dateFormat, Map<String,Float> real ) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        DateFormat fd = new SimpleDateFormat("dd");
        linnTemp.clear();
        try {
            Date sDate = format.parse(startDate);
            Date eDate = format.parse(endDate);
            long start = sDate.getTime();
            long end = eDate.getTime();
            if (start > end) {
                return null;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sDate);
            List<String> res = new ArrayList<String>();

            while (end >= start) {
                String day = fd.format(calendar.getTime());
                String date = format.format(calendar.getTime());
                res.add(day);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
//                calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
                start = calendar.getTimeInMillis();
                LineBean b = new LineBean();
                b.setTempName(date);
                b.setName(day);
                b.setValue(real.containsKey(date)?real.get(date)*100:0f);
                linnTemp.add(b);
            }

            return res;
        } catch (ParseException e) {

        }

        return null;
    }

    private void processZ( Map<String,Float> ok, Map<String,Float> no, int type){
        ArrayList<LineBean> lbs = new ArrayList<>();
        float a = 0,b=0;
        int zhou = 1;
        for(int i=0;i<linnTemp.size();i++){
            LineBean c = linnTemp.get(i);
            if(ok.containsKey(c.getTempName())){
                a = ok.get(c.getTempName())+a;

            }
            if(no.containsKey(c.getTempName())){
                b = no.get(c.getTempName())+b;
            }


            if(type==2){
                //月
                if(i%30==0){
                    P.c(c.getTempName()+"=="+a+"==="+b);
                    c.setName("第"+(zhou++)+"月");
                    c.setValue(FileUtils.formatFloat(a/(a+b)*100));

                    lbs.add(c);
                    a = 0;
                    b = 0;

                }
            }else if(type==1){
                //周
                if(i%7==0){
                    P.c(c.getTempName()+"=="+a+"==="+b);
                    c.setName("第"+(zhou++)+"周");
                    c.setValue(FileUtils.formatFloat(a/(a+b)*100));

                    lbs.add(c);
                    a = 0;
                    b = 0;

                }
            }

        }
        lineBeans.clear();
        lineBeans = lbs;
    }

}
