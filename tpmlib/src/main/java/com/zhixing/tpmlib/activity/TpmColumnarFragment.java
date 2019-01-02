package com.zhixing.tpmlib.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.zhixing.www.BaseFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.ColumnarBean;
import com.zhixing.tpmlib.bean.TestBarData;
import com.zhixing.tpmlib.utils.BarChartHelper;
import com.zhixing.tpmlib.utils.IBarData;
import com.zhixing.tpmlib.viewModel.ColumnarViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author zjq
 * create at 2018/12/28 下午3:53
 * 统计保养柱状图
 */
public class TpmColumnarFragment extends BaseFragment {


    private  BarChart BarChartTpm1;


    private BarChart BarChartTpm2;
    Unbinder unbinder;
    private ColumnarViewModel mColumnarViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tpm_columnar_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        BarChartTpm1= view.findViewById(R.id.BarChart_tpm1);
        BarChartTpm2= view.findViewById(R.id.BarChart_tpm2);

        mColumnarViewModel = ViewModelProviders.of(getActivity()).get(ColumnarViewModel.class);
        initData();


        return view;
    }
    private void setFrist(List<List<IBarData>> data, List<Integer> chartColors, List<String> names) {


        new BarChartHelper.Builder()
                .setContext(this.getContext())
                .setBarChart(BarChartTpm1)
                .setBarSetData(data)
                // 一页X轴显示个数
                .setDisplayCount(4)

                // 标签文字大小
                .setLegendTextSize(11)
                // 是否显示右边Y轴
                .setyAxisRightEnable(false)
                // X,Y轴是否绘制网格线
                .setDrawGridLines(false)
                // 缩放
                .setScaleEnabled(false)
                // 是否可以通过双击屏幕放大图表
                .setDoubleTapToZoomEnabled(false)
                // 柱状图描述 图表右下角
                .setDescriptionEnable(true)
                // 按比例放缩柱状图
                .setPinchZoom(false)
                // 单柱状图 每个柱的宽度，只在单柱状图生效
                .setBarWidth(0.6f)
                // x,y轴动画时间和类型
                .setDurationMillis(2000)
                .setEasing(Easing.EasingOption.Linear)
                .setLabels(names)
                // 单柱状图颜色
                .setBarColors(chartColors)
                // X轴是否显示自定义数据，在IBarData接口中定义
                .setXValueEnable(true)
                .build();
    }

    private void setNext(  List<IBarData> data) {


        new BarChartHelper.Builder()
                .setContext(this.getContext())
                .setBarChart(BarChartTpm2)
                .setBarData(data)
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
                .setScaleEnabled(false)
                // 是否可以通过双击屏幕放大图表
                .setDoubleTapToZoomEnabled(false)
                // 柱状图描述 图表右下角
                .setDescriptionEnable(true)
                // 按比例放缩柱状图
                .setPinchZoom(true)
                // 单柱状图 每个柱的宽度，只在单柱状图生效
                .setBarWidth(0.3f)
                // x,y轴动画时间和类型
                .setDurationMillis(2000)
                .setEasing(Easing.EasingOption.Linear)
                // 单柱状图颜色
                .setBarColor(Color.parseColor("#72d3b4"))
                // X轴是否显示自定义数据，在IBarData接口中定义
                .setXValueEnable(true)
                .build();
    }
    private void initData() {

        mColumnarViewModel.ColumnarValue.observe(getActivity(), new Observer<List<ColumnarBean>>() {
            @Override
            public void onChanged(@Nullable List<ColumnarBean> columnarBeans) {

                if (columnarBeans!=null){
                    String[] colors = getResources().getStringArray(R.array.chart_colors);
                    List<Integer> chartColors = new ArrayList<>();
                    chartColors.add(Color.parseColor(colors[1]));
                    chartColors.add(Color.parseColor(colors[2]));
                    List<String> names = new ArrayList<>();
                    names.add("总次数");
                    names.add("准时完成");
                    List<List<IBarData>> data = new ArrayList<>();
                    for (int i = 0; i < names.size(); i++) {
                        // 单个柱状图数据
                        ArrayList<IBarData> entries = new ArrayList<>();
                        if (names.get(i).equals("总次数")) {
                            for (ColumnarBean bean : columnarBeans) {
                                entries.add(new TestBarData(Float.parseFloat(bean.getTotal()), bean.getDate()));
                            }
                        } else {
                            for (ColumnarBean bean : columnarBeans) {
                                entries.add(new TestBarData(Float.parseFloat(bean.getOnTimeNum()), bean.getDate()));
                            }
                        }
                        data.add(entries);
                    }

                    setFrist(data,chartColors,names);

                    //模拟数据
                    ArrayList<IBarData> entries = new ArrayList<>();
                    for (ColumnarBean bean : columnarBeans) {
                        entries.add(new TestBarData(Float.parseFloat(bean.getPercent()), bean.getDate()));
                    }
                    setNext(entries);

                }




            }
        });


    }
    @Override
    public void process(Message msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
