package com.zhixing.employlib.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.zhixing.www.BaseFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.zhixing.employlib.R;
import com.zhixing.employlib.model.TestLineData;
import com.zhixing.employlib.utils.linechart.ILineChartData;
import com.zhixing.employlib.utils.linechart.LineChartHelper;

import java.util.ArrayList;

/**
 *
 *@author zjq
 *create at 2019/3/5 下午2:28
 * 个人绩效的趋势
 */
public class PersonUporDownFragment  extends BaseFragment {

    private LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_person_upordown,container,false);

          lineChart=view.findViewById(R.id.lineChart_personl);
          initData();
        return view;
    }

    private void initData() {
        ArrayList<ILineChartData> entries = new ArrayList<>();

        entries.add(new TestLineData(4,"2019/3/1"));
        entries.add(new TestLineData(7,"2019/3/2"));
        entries.add(new TestLineData(4,"2019/3/3"));
        entries.add(new TestLineData(9,"2019/3/4"));
        entries.add(new TestLineData(6,"2019/3/5"));
        entries.add(new TestLineData(3,"2019/3/6"));
        entries.add(new TestLineData(4,"2019/3/7"));
        entries.add(new TestLineData(1,"2019/3/8"));
        entries.add(new TestLineData(9,"2019/3/9"));
        LineChartHelper barChartHelper = new LineChartHelper(lineChart);

        //创建多条折线的图表
        barChartHelper.showSingleLineChart(entries, R.color.blue, 4);

    }

    @Override
    public void process(Message msg) {

    }
}
