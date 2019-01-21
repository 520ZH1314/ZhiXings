package com.zhixing.tpmlib.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.base.zhixing.www.BaseFragment;
import com.base.zhixing.www.util.ACache;
import com.base.zhixing.www.util.GsonUtil;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.gson.reflect.TypeToken;
import com.luliang.shapeutils.DevShapeUtils;
import com.luliang.shapeutils.shape.DevShape;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;
import com.zhixing.tpmlib.adapter.TpmTableAdapt;
import com.zhixing.tpmlib.bean.ColumnarBean;
import com.zhixing.tpmlib.bean.StaticticalAnalAnalyEntity;
import com.zhixing.tpmlib.bean.TestBarData;
import com.zhixing.tpmlib.utils.BarChartHelper;
import com.zhixing.tpmlib.utils.IBarData;
import com.zhixing.tpmlib.view.MyBarChart.MyBarChart;
import com.zhixing.tpmlib.viewModel.ColumnarViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author zjq
 * create at 2018/12/28 下午3:53
 * 统计保养柱状图
 */
public class TpmColumnarFragment extends BaseFragment {


    @BindView(R2.id.btn_total1)
    Button btnTotal1;
    @BindView(R2.id.btn_total2)
    Button btnTotal2;
    @BindView(R2.id.btn_total3)
    Button btnTotal3;
    @BindView(R2.id.textView_replace)
    TextView textViewReplace;
    private MyBarChart BarChartTpm1;


    private MyBarChart BarChartTpm2;
    Unbinder unbinder;
    private ColumnarViewModel mColumnarViewModel;
    private TextView mTvType;
    private String Key;
    private ACache aCache;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tpm_columnar_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        BarChartTpm1 = view.findViewById(R.id.BarChart_tpm1);
        BarChartTpm2 = view.findViewById(R.id.BarChart_tpm2);
              mTvType=view.findViewById(R.id.tv_total_maintenance_type);

        aCache=ACache.get(getActivity());
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .gradientLinear(R.color.total1, R.color.total2)
                .orientation(DevShape.LEFT_RIGHT)
                .radius(15)
                .into(btnTotal1);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .gradientLinear(R.color.total3, R.color.total4)
                .orientation(DevShape.LEFT_RIGHT)
                .radius(15)
                .into(btnTotal2);
        DevShapeUtils
                .shape(DevShape.RECTANGLE)
                .gradientLinear(R.color.total5, R.color.total6)
                .orientation(DevShape.LEFT_RIGHT)
                .radius(15)
                .into(btnTotal3);
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
                .setLegendEnable(false)
                // 标签文字大小
                .setLegendTextSize(11)
                // 是否显示右边Y轴
                .setyAxisRightEnable(false)
                // X,Y轴是否绘制网格线
                .setDrawGridLines(false)
                // 缩放
                .setScaleEnabled(true)
                // 是否可以通过双击屏幕放大图表
                .setDoubleTapToZoomEnabled(false)
                // 柱状图描述 图表右下角
                .setDescriptionEnable(false)
                // 按比例放缩柱状图
                .setPinchZoom(true)
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

    private void setNext(List<IBarData> data) {


        new BarChartHelper.Builder()
                .setContext(this.getContext())
                .setBarChart(BarChartTpm2)
                .setmUsePercentValues(true)
                .setBarData(data)
                // 一页X轴显示个数
                .setDisplayCount(4)
                // 标签显示隐藏
                .setLegendEnable(false)
                // 标签文字大小
                .setLegendTextSize(11)
                // 是否显示右边Y轴
                .setyAxisRightEnable(false)
                // X,Y轴是否绘制网格线
                .setDrawGridLines(false)
                //设置百分比

                // 缩放
                .setScaleEnabled(true)
                // 是否可以通过双击屏幕放大图表
                .setDoubleTapToZoomEnabled(false)
                // 柱状图描述 图表右下角
                .setDescriptionEnable(false)
                // 按比例放缩柱状图
                .setPinchZoom(true)
                // 单柱状图 每个柱的宽度，只在单柱状图生效
                .setBarWidth(0.3f)
                // x,y轴动画时间和类型
                .setDurationMillis(2000)
                .setEasing(Easing.EasingOption.Linear)
                // 单柱状图颜色
                .setBarColor(Color.parseColor("#7a69fe"))
                // X轴是否显示自定义数据，在IBarData接口中定义
                .setXValueEnable(true)
                .build();
    }

    private void initData() {



        mColumnarViewModel.getNomalKey().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s!=null){
                    List<Integer> chartColors = new ArrayList<>();

                    chartColors.add(Color.parseColor("#2297f3"));
                    chartColors.add(Color.parseColor("#2271f3"));
                    Key=s;
                    mTvType.setText(Key);
                    String mapData = aCache.getAsString("mapData");
                    if (mapData!=null){
                        Type type = new TypeToken<Map<String, List<StaticticalAnalAnalyEntity>>>() {}.getType();
                        Map<String, List<StaticticalAnalAnalyEntity>> map2 = GsonUtil.getGson().fromJson(mapData, type);
                        List<StaticticalAnalAnalyEntity> staticticalAnalAnalyEntities = map2.get(Key);
                        List<String> names = new ArrayList<>();
                        names.add("总次数");
                        names.add("准时完成");
                        List<List<IBarData>> data = new ArrayList<>();
                        for (int i = 0; i < names.size(); i++) {
                            // 单个柱状图数据
                            ArrayList<IBarData> entries = new ArrayList<>();
                            if (names.get(i).equals("总次数")) {
                                for (StaticticalAnalAnalyEntity bean : staticticalAnalAnalyEntities) {
                                    entries.add(new TestBarData(Float.parseFloat(bean.getTotalSum()), bean.getMonth()));
                                }
                            }else{

                                for (StaticticalAnalAnalyEntity bean : staticticalAnalAnalyEntities) {
                                    entries.add(new TestBarData(Float.parseFloat(bean.getOKRecord()), bean.getMonth()));
                                }
                            }
                            data.add(entries);
                        }
                        setFrist(data,chartColors,names);
                        ArrayList<IBarData> entries = new ArrayList<>();
                        for (StaticticalAnalAnalyEntity bean : staticticalAnalAnalyEntities) {
                            entries.add(new TestBarData(bean.getOnTimeRate(), bean.getMonth()));
                        }
                        setNext(entries);
                    }






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

    @OnClick(R2.id.textView_replace)
    public void onViewClicked() {
        mColumnarViewModel.isReplace.setValue(true);

    }
}
