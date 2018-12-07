
package com.shuben.zhixing.www.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ThreeBarCharView01 {
	  
    private static int margins[] = new int[] {40, 100, 0,50}; //{上左下右} 
    private static String[] titles = new String[] { "", "" ,""};  
    private List<double[]> values = new ArrayList<double[]>();  
    //private static int[] colors = new int[] { Color.rgb(240, 75, 54), Color.GREEN };  
    private static int[] colors = new int[] { Color.rgb(240, 75, 54), Color.rgb(253, 170, 31),Color.rgb(47, 186, 242) };
    private XYMultipleSeriesRenderer renderer;
    private Context mContext;  
    private String mTitle;  
    private List<String> option;  
    private boolean isSingleView = false;  
    public ThreeBarCharView01(Context context, boolean isSingleView) {  
        this.mContext = context;  
        this.isSingleView = isSingleView;  
        this.renderer = new XYMultipleSeriesRenderer();
    }  
    public void initData(double[] firstAnswerPercent, double[] lastAnswerPercent,double[] ThirdAnswerPercent, List<String> option, String title) {  
        this.values.add(firstAnswerPercent);  
        if (!isSingleView) {  
            this.values.add(lastAnswerPercent);
            this.values.add(ThirdAnswerPercent);
        }  
        this.mTitle = title;  
        this.option = option;  
  
    }  
  
    public View getBarChartView(int w,double h,int weigh,double max) {  
        buildBarRenderer(weigh/(2*w-1));  
        setChartSettings(renderer, mTitle, "", "", 0, w, 0, max, Color.BLACK, Color.LTGRAY);  
        renderer.getSeriesRendererAt(0).setDisplayChartValues(true);  
        if(!isSingleView){  
             renderer.getSeriesRendererAt(1).setDisplayChartValues(true);  
        }  
        int size =  option.size();  
        for (int i = 1; i < size; i++) {  
            renderer.addXTextLabel(i, option.get(i)); 
        }  
        renderer.setMargins(margins);  
        renderer.setMarginsColor(0x00ffffff);  
        renderer.setPanEnabled(false, false);  
        renderer.setZoomEnabled(false, false);// 设置x，y方向都不可以放大或缩�?  
        renderer.setZoomRate(2.0f);  
        renderer.setInScroll(false);  
        renderer.setBackgroundColor(0x00ffffff);  
        renderer.setApplyBackgroundColor(false);  
        View view = ChartFactory.getBarChartView(mContext, buildBarDataset(titles, values), renderer, Type.DEFAULT); // Type.STACKED
        view.setBackgroundColor(0x00ffffff);  
        return view;  
    }  
  
    private XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = isSingleView ? (titles.length - 1) : titles.length;  
        for (int i = 0; i < length; i++) {  
            CategorySeries series = new CategorySeries(titles[i]);
            double[] v = values.get(i);  
            int seriesLength = v.length;  
            for (int k = 0; k < seriesLength; k++) {  
                series.add(v[k]);  
            }  
            dataset.addSeries(series.toXYSeries());  
        }  
        return dataset;  
    }  
  
    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle,
                                    double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
        renderer.setChartTitle(title);  
        renderer.setXTitle(xTitle);  
        renderer.setYTitle(yTitle);  
        renderer.setXAxisMin(xMin);  
        renderer.setXAxisMax(xMax);  
        renderer.setYAxisMin(yMin);  
        renderer.setYAxisMax(yMax);  
        renderer.setAxesColor(axesColor);  
        renderer.setLabelsColor(labelsColor);  
        renderer.setXLabels(0);  //设置X轴点个数
        renderer.setYLabels(5); //设置Y轴点个数
        renderer.setLabelsTextSize(25);
        //renderer.setLabelFormat(NumberFormat.getPercentInstance());//设置成百分比
        renderer.setYLabelsAlign(Align.RIGHT);  
        renderer.setXLabelsAlign(Align.CENTER);
        renderer.setShowLegend(false);//设置是否显示图例
        renderer.setShowGrid(true);//设置是否显示网格
        renderer.setGridColor(Color.GRAY);
        // renderer.setXLabelsColor(0xff000000);//设置X轴上的字体颜�?  
        // renderer.setYLabelsColor(0,0xff000000);//设置Y轴上的字体颜�?  
    }  
  
    /*  
     * 初始化柱子风�?  
     */  
    protected void buildBarRenderer(int weigh) {  
        if (null == renderer) {  
            return;  
        }  
        renderer.setBarWidth(40);//柱子宽度
        //renderer.setBarSpacing(20); 
        renderer.setBarSpacing(5); 
        renderer.setAxisTitleTextSize(26);  
        renderer.setChartTitleTextSize(20);  
        renderer.setLabelsTextSize(25);  
        //renderer.setLegendTextSize(15);//图例大小  
        renderer.setLegendHeight(0);
        int length = isSingleView ? (colors.length - 1) : colors.length;  
       
        for (int i = 0; i < length; i++) {  
            SimpleSeriesRenderer ssr = new SimpleSeriesRenderer();
            //设置柱状图值是否显示、显示位置、字体大小
            ssr.setChartValuesTextAlign(Align.CENTER);
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMinimumFractionDigits( 2 ); 
    		//numberFormat.setMaximumFractionDigits(4);
            // ssr.setChartValuesFormat(numberFormat);// 设置百分比
            ssr.setChartValuesTextSize(25);  
            ssr.setDisplayChartValues(true);  
            ssr.setColor(colors[i]);  
            renderer.addSeriesRenderer(ssr); 
            
            
        }  
    }  


}

