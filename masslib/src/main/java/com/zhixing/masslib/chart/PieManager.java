package com.zhixing.masslib.chart;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.zhixing.masslib.R;
import com.zhixing.masslib.custom.MarkerViewPie;

import java.util.ArrayList;
import java.util.Random;

public class PieManager {
    private PieChart pieChart;
    private Context context;
    public PieManager(Context context,PieChart pieChart){
        this.context = context;
        this.pieChart = pieChart;
        pieChart.setNoDataText("暂无数据");
    }
    private void init(String txt){
        //设置成百分比
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterText(generateCenterSpannableText(txt));
        pieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setDrawEntryLabels(false);//设置饼状图的文字
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(10f);
        pieChart.setDrawCenterText(false);
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
//        pieChart.setDrawSliceText(false);//设置饼状图的文字
        pieChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart.setOnChartValueSelectedListener(selectedListener);

        //setData(4, 100);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

       /* Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);*/
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTextSize(10,10);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        MarkerViewPie mv = new MarkerViewPie(context, R.layout.custom_marker_view);
        mv.setChartView(pieChart); // For bounds control
        pieChart.setMarker(mv); // Set the marker to the chart
//        pieChart.setDrawMarkers(false);

    }
    //事件监听
    private OnChartValueSelectedListener selectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
//            P.c("这里。。。");
            if(e instanceof  PieEntry){
             //   P.c(((PieEntry) e).getLabel());
            }
        }

        @Override
        public void onNothingSelected() {

        }
    };
    //装载圆圈中心数据
    private SpannableString generateCenterSpannableText(String temp) {
        SpannableString s = new SpannableString(temp);
        return  s;

    }

    private String color(){
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        //生成随机对象
        Random random = new Random();
        //生成红色颜色代码
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成绿色颜色代码
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成蓝色颜色代码
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        //判断红色代码的位数
        red = red.length()==1 ? "0" + red : red ;
        //判断绿色代码的位数
        green = green.length()==1 ? "0" + green : green ;
        //判断蓝色代码的位数
        blue = blue.length()==1 ? "0" + blue : blue ;
        //生成十六进制颜色值
        String color = "#"+red+green+blue;
        return  color;
    }

    private void setData( String txt, ArrayList<PieEntry> entries){
        PieDataSet dataSet = new PieDataSet(entries, txt);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
       // dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        ArrayList<Integer> pa = new ArrayList<>();
        for(int i=0;i<entries.size();i++){
            pa.add(Color.parseColor(color()));
        }
        dataSet.setColors(pa);
//        dataSet.setUsingSliceColorAsValueLineColor(true);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

    }
    //展示饼状图
    public void showPieChart(String txt, ArrayList<PieEntry> entries){
        init(txt);
        setData(txt,entries);
        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }
}
