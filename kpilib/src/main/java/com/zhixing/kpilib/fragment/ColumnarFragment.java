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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.google.gson.reflect.TypeToken;
import com.zhixing.kpilib.R;
import com.zhixing.kpilib.activity.TestBarData;
import com.zhixing.kpilib.httpbean.FristEntitys;
import com.zhixing.kpilib.httpbean.KpiEntitys;
import com.zhixing.kpilib.httpbean.MenuEntity;
import com.zhixing.kpilib.mphelper.chart.barchart.BarChartHelper;
import com.zhixing.kpilib.mphelper.chart.barchart.IBarData;
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

//柱状图的fragment
public class ColumnarFragment extends BaseFragment {
    private List<String> titles;

    private BarChart BarChart;
    private ACache aCache;
    private  List<MenuEntity.RowsBean>  menusListBean;
    private TextView tv_ColuMnar_Company;//单位


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         aCache = ACache.get(this.getContext());
        JSONArray menuJsonArray = aCache.getAsJSONArray(Contant.MENUDATA);
        Type mType = new TypeToken<List<MenuEntity.RowsBean> >(){}.getType();
        menusListBean = GsonUtil.getGson().fromJson(menuJsonArray.toString(), mType);
    }

    @Override
    public void process(Message msg) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_columnar, container, false);
        BarChart =view.findViewById(R.id.BarChart);
       tv_ColuMnar_Company=view.findViewById(R.id.tv_columnar_fragment_company);
        EventBus.getDefault().register(this);

        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true,priority = 100)
    public void Eventone(KpiEntitys messageEvent) {

        settingData(messageEvent.getRows(),messageEvent.getI());

    }

    private void settingData(List<KpiEntitys.RowsBean> rows, int i) {
        //模拟数据
        ArrayList<IBarData> entries = new ArrayList<>();

        for (KpiEntitys.RowsBean bean: rows) {

                entries.add(new TestBarData(bean.getData(), bean.getParameter()));

        }
        tv_ColuMnar_Company.setText("单位"+"("+menusListBean.get(i).getUnit()+")");

        //创建多条折线的图表
        new BarChartHelper.Builder()
                .setContext(this.getContext())
                .setBarChart(BarChart)
                .setBarData(entries)
                // 一页X轴显示个数
                .setDisplayCount(4)
                // 标签显示隐藏
                .setLegendEnable(true)
                // 标签文字大小
                .setLegendTextSize(11)
                // 是否显示右边Y轴
                .setyAxisRightEnable(false)
                // X,Y轴是否绘制网格线
                .setDrawGridLines(false)
                // 缩放
                .setScaleEnabled(true)
                // 是否可以通过双击屏幕放大图表
                .setDoubleTapToZoomEnabled(true)
                // 柱状图描述 图表右下角
                .setDescriptionEnable(false)
                // 按比例放缩柱状图
                .setPinchZoom(true)
                // 单柱状图 每个柱的宽度，只在单柱状图生效
                .setBarWidth(0.6f)
                // x,y轴动画时间和类型
                .setDurationMillis(2000)
                .setEasing(Easing.EasingOption.Linear)
                // 单柱状图颜色
                .setBarColor(Color.parseColor("#6CB0DF"))
                .setBaseData(menusListBean,i)//设置一些图表的单位数据
                // X轴是否显示自定义数据，在IBarData接口中定义
                .setXValueEnable(true)
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky=true,priority = 1)
    public void FristEventone(FristEntitys messageEvent) {
        FristsettingData(messageEvent);


    }

    private void FristsettingData(FristEntitys messageEvent) {

        //模拟数据
        ArrayList<IBarData> entries = new ArrayList<>();

        for (FristEntitys.RowsBean bean: messageEvent.getRows()) {

                entries.add(new TestBarData(bean.getData(), bean.getParameter()));


        }
        tv_ColuMnar_Company.setText("单位"+"("+menusListBean.get(0).getUnit()+")");
        //创建多条折线的图表
        new BarChartHelper.Builder()
                .setContext(this.getContext())
                .setBarChart(BarChart)
                .setBarData(entries)
                // 一页X轴显示个数
                .setDisplayCount(4)
                // 标签显示隐藏
                .setLegendEnable(true)
                // 标签文字大小
                .setLegendTextSize(11)
                // 是否显示右边Y轴
                .setyAxisRightEnable(false)
                // X,Y轴是否绘制网格线
                .setDrawGridLines(false)
                // 缩放
                .setScaleEnabled(true)
                // 是否可以通过双击屏幕放大图表
                .setDoubleTapToZoomEnabled(true)
                // 柱状图描述 图表右下角
                .setDescriptionEnable(true)
                // 按比例放缩柱状图
                .setPinchZoom(true)
                // 单柱状图 每个柱的宽度，只在单柱状图生效
                .setBarWidth(0.6f)
                // x,y轴动画时间和类型
                .setDurationMillis(2000)
                .setEasing(Easing.EasingOption.Linear)
                // 单柱状图颜色
                .setBarColor(Color.parseColor("#6CB0DF"))
                .setBaseData(menusListBean,0)
                // X轴是否显示自定义数据，在IBarData接口中定义
                .setXValueEnable(true)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
