package com.shuben.zhixing.www.mes;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;

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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;

import java.util.ArrayList;
import java.util.List;

public class ManagerChartActivity extends BaseActvity {
    private CombinedChart dataChart;//图表
    private CombinedData data;

    @Override
    public int getLayoutId() {
        return R.layout.activity_manager_chart;
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
        dataChart = (CombinedChart) findViewById(R.id.manager_chart);
        showDataOnChart();
        Legend legend = dataChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
    }

    /**
     * 展示数据
     */
    private void showDataOnChart() {
        //绘制图表数据
        data = new CombinedData();
        //设置折线图数据
        data.setData(getLineData());
        //设置柱状图数据
        data.setData(getBarData());
        dataChart.setData(data);
        //设置横坐标数据
        setAxisXBottom();
        //设置右侧纵坐标数据
        setAxisYRight();
        //设置左侧纵坐标数据
        setAxisYLeft();
        dataChart.setTouchEnabled(false);
        dataChart.getDescription().setEnabled(false);
        dataChart.setDrawGridBackground(false);
        dataChart.setDrawBarShadow(false);
        dataChart.setHighlightFullBarEnabled(true);
        dataChart.animateX(2000);
    }

    /**
     * 设置横坐标数据
     */
    private void setAxisXBottom() {
        List<String> valuesX = new ArrayList<>();
        String date[] = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        for (int i = 0;i < date.length;i++){
            valuesX.add(date[i]);
        }
        XAxis bottomAxis = dataChart.getXAxis();
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setCenterAxisLabels(true);
        bottomAxis.setValueFormatter(new IndexAxisValueFormatter(valuesX));
        bottomAxis.setAxisMinimum(data.getXMin());
        bottomAxis.setAxisMaximum(data.getXMax() + 0.5f);
        bottomAxis.setLabelCount(date.length);
        bottomAxis.setAvoidFirstLastClipping(false);
    }

    /**
     * 设置右侧纵坐标数据
     */
    private void setAxisYRight() {
        YAxis right = dataChart.getAxisRight();
        right.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "℃";
            }
        });
        right.setDrawGridLines(false);
    }

    /**
     * 设置左侧纵坐标数据
     */
    private void setAxisYLeft() {
        YAxis left = dataChart.getAxisLeft();
        left.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "ml";
            }
        });
        left.setDrawGridLines(false);
    }

    /**
     * 设置折线图绘制数据
     * 温度
     * @return
     */
    public LineData getLineData() {
        LineData lineData = new LineData();
        List<Entry> customCounts = new ArrayList<>();
        float[] data = {2.0f, 2.2f, 3.3f, 4.5f, 6.3f, 10.2f, 20.3f, 23.4f, 23.0f, 16.5f, 12.0f, 6.2f};
        //人数
        for (int i = 0; i < data.length; i++) {
            customCounts.add(new Entry(i + 0.5f,data[i]));
        }
        LineDataSet lineDataSet = new LineDataSet(customCounts,"平均温度");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineDataSet.setColor(Color.parseColor("#66A3AB"));
        lineDataSet.setCircleColor(Color.parseColor("#66A3AB"));
        lineDataSet.setCircleRadius(5);
        lineDataSet.setLineWidth(3);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.parseColor("#66A3AB"));
        lineData.addDataSet(lineDataSet);
        return lineData;
    }
    /**
     * 设置柱状图绘制数据
     *
     * @return
     */
    public BarData getBarData() {
        BarData barData = new BarData();
        //总量金额
        List<BarEntry> amounts = new ArrayList<>();
        float z[] = {2.0f, 4.9f, 7.0f, 23.2f, 25.6f, 76.7f, 135.6f, 162.2f, 32.6f, 20.0f, 6.4f, 3.3f};
        //平均金额
        List<BarEntry> averages = new ArrayList<>();
        float j[] = {2.6f, 5.9f, 9.0f, 26.4f, 28.7f, 70.7f, 175.6f, 182.2f, 48.7f, 18.8f, 6.0f, 2.3f};
        //添加数据
        for (int i = 0; i < z.length; i++) {
            amounts.add(new BarEntry(i,z[i]));
            averages.add(new BarEntry(i,j[i]));
        }
        //设置总数的柱状图
        BarDataSet amountBar = new BarDataSet(amounts,"蒸发量");
        amountBar.setAxisDependency(YAxis.AxisDependency.LEFT);
        amountBar.setColor(Color.parseColor("#C23531"));
        //设置平均的柱状图
        BarDataSet averageBar = new BarDataSet(averages,"降水量");
        averageBar.setAxisDependency(YAxis.AxisDependency.LEFT);
        averageBar.setColor(Color.parseColor("#2F4554"));
        amountBar.setValueTextSize(10);
        averageBar.setValueTextSize(10);
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

