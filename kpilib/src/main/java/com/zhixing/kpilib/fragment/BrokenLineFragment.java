package com.zhixing.kpilib.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.zhixing.www.BaseFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.reflect.TypeToken;
import com.zhixing.kpilib.R;
import com.zhixing.kpilib.activity.TestLineData;
import com.zhixing.kpilib.httpbean.FristEntitys;
import com.zhixing.kpilib.httpbean.KpiEntitys;
import com.zhixing.kpilib.httpbean.MenuEntity;
import com.zhixing.kpilib.mphelper.chart.linechart.ILineChartData;
import com.zhixing.kpilib.mphelper.chart.linechart.LineChartHelper;
import com.base.zhixing.www.util.ACache;
import com.zhixing.kpilib.utils.Contant;
import com.zhixing.kpilib.utils.GsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//折线图的fragment
public class BrokenLineFragment extends BaseFragment {


    private LineChart lineChart;
    private ACache mCache;
    private TextView tvBroken_Company;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         mCache = ACache.get(this.getContext());
    }

    @Override
    public void process(Message msg) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_broken_line, container, false);
        lineChart = view.findViewById(R.id.lineChart);
        tvBroken_Company=view.findViewById(R.id.tv_broken_fragment_company);
        EventBus.getDefault().register(this);
        return view;
    }


    private void settingData(List<KpiEntitys.RowsBean> rowsBeans, int i) {
        ArrayList<ILineChartData> entries = new ArrayList<>();
        JSONArray menuJsonArray = mCache.getAsJSONArray(Contant.MENUDATA);
        Type mType = new TypeToken<List<MenuEntity.RowsBean> >(){}.getType();
        List<MenuEntity.RowsBean> menusListBean = GsonUtil.getGson().fromJson(menuJsonArray.toString(), mType);
        MenuEntity.RowsBean menuRowsBean = menusListBean.get(i);
        //设置数据
        for (KpiEntitys.RowsBean bean : rowsBeans) {

                entries.add(new TestLineData(bean.getData(), bean.getParameter()));

        }
        LineChartHelper barChartHelper = new LineChartHelper(lineChart);
        //颜色填充
        String[] colors = getResources().getStringArray(R.array.chart_colors);
        tvBroken_Company.setText("单位"+"("+menuRowsBean.getUnit()+")");
        //创建多条折线的图表
        barChartHelper.showSingleLineChart(entries, Color.parseColor(colors[0]), 5,menuRowsBean.getTypeName(),menuRowsBean.getTargetValue(),menuRowsBean.getCycleCode(),menuRowsBean.getUnit());


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true,priority = 100)
    public void Event(KpiEntitys messageEvent) {

        settingData(messageEvent.getRows(),messageEvent.getI());

    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void fristEvent(FristEntitys messageEvent) {
        fristSettingData(messageEvent);
    }

    private void fristSettingData(FristEntitys messageEvent) {
        ArrayList<ILineChartData> entries = new ArrayList<>();
        JSONArray menuJsonArray = mCache.getAsJSONArray(Contant.MENUDATA);
        Type mType = new TypeToken<List<MenuEntity.RowsBean> >(){}.getType();
        List<MenuEntity.RowsBean> menusListBean = GsonUtil.getGson().fromJson(menuJsonArray.toString(), mType);
        MenuEntity.RowsBean menuBean = menusListBean.get(0);

        //设置数据
        for (FristEntitys.RowsBean bean : messageEvent.getRows()) {
            String parameter = bean.getParameter();

                entries.add(new TestLineData(bean.getData(), parameter));



        }

        LineChartHelper barChartHelper = new LineChartHelper(lineChart);
        //颜色填充
        String[] colors = getResources().getStringArray(R.array.chart_colors);
        tvBroken_Company.setText("单位"+"("+menuBean.getUnit()+")");
        //创建多条折线的图表
        barChartHelper.showSingleLineChart(entries, Color.parseColor(colors[0]), 5,menuBean.getTypeName(),menuBean.getTargetValue(),menuBean.getCycleCode(),menuBean.getUnit());


    }

}



