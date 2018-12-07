package com.shuben.zhixing.www.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.adapter.AnalysisAdapter;
import com.shuben.zhixing.www.data.AnalysisDate;
import com.shuben.zhixing.www.data.GetOrganizeList_Data;
import com.shuben.zhixing.www.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SpinerPopWindow;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/8/22.
 * 组织数据分析
 */

public class DataAnalysisActivity extends BaseActvity implements View.OnClickListener {
    private static String TAG="";
    private ImageView tetle_back;
    private TextView tetle_text,analysis_close,analysis_statistics,analysis_personage,title_day,title_month,title_year;
    private TextView neibu_sp2,neibu_sp3;
    private LinearLayout llayout1,llayout2,llayout3;
    private List<AnalysisDate> data;
    private ListView gognsi_data_list,bumen_data_list;
    private AnalysisAdapter adapter,adapter_bumen;

    private TextView date_tv1,date_tv2,date_tv3,date_tv4;
    private TextView examine_tv1,examine_tv2,examine_tv3,examine_tv4;
    private TextView three_tv1,three_tv2,three_tv3;
    private TextView four_tv1,four_tv2,four_tv3,four_tv4,four_tv5;
    private TextView tx_Avg01,tx_Avg02;


    private RequestQueue mQueue;
    private View  view01,view02,view03;

    private List<String> list=new ArrayList<String>() ;
    private String tagss = "";
    private String  orgIds="";

    private PieChart mChart01,mChart02,mChart03,mChart04;
    private ArrayList<PieEntry> entries01=new ArrayList<PieEntry>();
    private ArrayList<PieEntry> entries02=new ArrayList<PieEntry>();
    private ArrayList<PieEntry> entries03=new ArrayList<PieEntry>();
    private ArrayList<PieEntry> entries04=new ArrayList<PieEntry>();
    private Typeface tf;
    private List<GetOrganizeList_Data> GetOrganizeList_list_data;
    private GetOrganizeList_Data mGetOrganizeList_Data;
    private List<String> list_name01=new ArrayList<String>();
    private List<String> list_name02=new ArrayList<String>();
    private List<String> list_id01=new ArrayList<String>();
    private List<Integer> list_id02=new ArrayList<Integer>();
    private SpinerPopWindow<String> mSpinerPopWindow01;
    private SpinerPopWindow<String> mSpinerPopWindow02;
    private int  source=0;
    private int index01=0;
    private int index02=0;
    private String date="week";

    @Override
    public int getLayoutId() {
        return R.layout.activity_dataanalysis;
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
        mQueue = Volley.newRequestQueue(this);
        initData();//spiner假数据
        GetOrganizeListWebDate();
        init();
        //完全看不明白怎么使用这些接口

//        GetOrgTaskTypeBySourceWebDate();//2.2.20	我的组织->统计分析->任务分类统计->部门任务来源分析
//        GetOrgTaskTypeByClassWebDate();//2.2.21	我的组织->统计分析->任务分类统计->部门任务类型分析
        // GetOrgTaskCloseRateDataByTopWebDate(list_id01.get(index01),list_id02.get(index02)+"",date);//2.2.24	我的组织->统计分析->评价管理分析->部门龙虎榜
    }

    private void init() {
        mSpinerPopWindow01 = new SpinerPopWindow<String>(this, list_name01,itemClickListener);
        mSpinerPopWindow02 = new SpinerPopWindow<String>(this, list_name02,itemClickListener);
        mSpinerPopWindow01.setOnDismissListener(dismissListener);
        mSpinerPopWindow02.setOnDismissListener(dismissListener);


        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回
        tetle_back.setOnClickListener(this);
        tetle_text = (TextView) findViewById(R.id.tetle_text);//title

        tetle_text.setText("组织数据分析");

        analysis_close = (TextView) findViewById(R.id.analysis_close);//任务关闭率
        analysis_statistics = (TextView) findViewById(R.id.analysis_statistics);//任务分类统计
        analysis_personage = (TextView) findViewById(R.id.analysis_personage);//个人任务评价

        view01 = (View) findViewById(R.id.view01);//下划线
        view02 = (View) findViewById(R.id.view02);//下划线
        view03 = (View) findViewById(R.id.view03);//下划线

        title_day = (TextView) findViewById(R.id.title_day);//日
        title_month = (TextView) findViewById(R.id.title_month);//月
        title_year = (TextView) findViewById(R.id.title_year);//年

        neibu_sp2 = (TextView) findViewById(R.id.neibu_sp2);//spiner选择按钮
        neibu_sp3 = (TextView) findViewById(R.id.neibu_sp3);//spiner选择按钮

        llayout1 = (LinearLayout) findViewById(R.id.llayout1);//spiner选择按钮
        llayout2 = (LinearLayout) findViewById(R.id.llayout2);//spiner选择按钮
        llayout3 = (LinearLayout) findViewById(R.id.llayout3);//spiner选择按钮

     /*我负责的任务*/
       /* date_tv1 = (TextView) findViewById(R.id.date_tv1);//色块准时关闭
        date_tv2 = (TextView) findViewById(R.id.date_tv2);//色块延迟关闭
        date_tv3 = (TextView) findViewById(R.id.date_tv3);//色块正常进行
        date_tv4 = (TextView) findViewById(R.id.date_tv4);//色块延迟进行

        *//*我检查的任务*//*
        examine_tv1 = (TextView) findViewById(R.id.examine_tv1);//色块准时关闭
        examine_tv2 = (TextView) findViewById(R.id.examine_tv2);//色块延迟关闭
        examine_tv3 = (TextView) findViewById(R.id.examine_tv3);//色块正常进行
        examine_tv4 = (TextView) findViewById(R.id.examine_tv4);//色块延迟进行中*/

       /*任务来源*/

        /*three_tv1 = (TextView) findViewById(R.id.three_tv1);//色块高效会议
        three_tv2 = (TextView) findViewById(R.id.three_tv2);//色块工作交办
        three_tv3 = (TextView) findViewById(R.id.three_tv3);//色块紧急催单*/


         /*任务类型*/
       // four_tv1 = (TextView) findViewById(R.id.three_tv1);//色块人员相关任务
        four_tv2 = (TextView) findViewById(R.id.four_tv2);//色块机械设备相关任务
        four_tv3 = (TextView) findViewById(R.id.four_tv3);//色块物料相关任务
        four_tv4 = (TextView) findViewById(R.id.four_tv4);//色块作业方法相关任务
        four_tv5 = (TextView) findViewById(R.id.four_tv5);//色块环境相关任务


        tx_Avg01 = (TextView) findViewById(R.id.tx_Avg01);
        tx_Avg02 = (TextView) findViewById(R.id.tx_Avg02);
        setOnclick();

    }


    private void setchart01(PieChart mChart,List<PieEntry> entries) {
        mChart = (PieChart) findViewById(R.id.piechart01);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        //mChart.setCenterText(generateCenterSpannableText());

        mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(30f);
        mChart.setTransparentCircleRadius(30f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //mChart.setOnChartValueSelectedListener(DataAnalysisActivity.this);

        setData(entries, mChart);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

    }
    private void setData(List<PieEntry> entries,PieChart mChart) {



        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    private void setOnclick() {
        analysis_close.setOnClickListener(this);
        analysis_statistics.setOnClickListener(this);
        analysis_personage.setOnClickListener(this);
        title_day.setOnClickListener(this);
        title_month.setOnClickListener(this);
        title_year.setOnClickListener(this);
        analysis_close.setOnClickListener(this);
        neibu_sp2.setOnClickListener(this);
        neibu_sp3.setOnClickListener(this);
    }

    /**
     * spiner初始化假数据
     */
    private void initData() {
        list_name02 = new ArrayList<String>();
        list_name02.add("全部来源");
        list_name02.add("高效会议");
        list_name02.add("任务交办");
        list_name02.add("内部催单");
        list_name02.add("外部催单");
        list_id02.add(0);
        list_id02.add(1);
        list_id02.add(2);
        list_id02.add(3);
        list_id02.add(4);
    }

    private void getWebDate(String  orgIds,String source,String ByType,String SearchType) {

        //催单客户统计接口
        String Url = UrlUtil.GetOrgClosedRateChart(UrlUtil.IP, UrlUtil.GetOrgClosedRateChart, orgIds, source, ByType,SearchType,"");
        Log.e("我发起的任务统计", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        if(response.indexOf("\"")==0) response = response.substring(1,response.length());   //去掉第一个 "
                        if(response.lastIndexOf("\"")==(response.length()-1)) response = response.substring(0,response.length()-1);  //去掉最后一个 "
                        response=response.replaceAll("\"","");
                        response=response.replaceAll("\\\\","\"");

                        try {
                            JSONObject jsonObject;
                            JSONArray array = new JSONArray(response);
                            NumberFormat numberFormat = NumberFormat.getInstance();
                            numberFormat.setMaximumFractionDigits(2);
                            entries01.clear();
                            double[] data01;
                            String [] names01;

                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);

                                String  ClassName=jsonObject.getString("ClassName");
                                if(ClassName.equals("合计")){
                                    ClassName="全部来源";
                                }
                                int TotalCount=jsonObject.getInt("TotalCount");
                                int OnTimeCloseCount=jsonObject.getInt("OnTimeCloseCount");
                                int DelayCloseCount=jsonObject.getInt("DelayCloseCount");
                                int OnGoingCount=jsonObject.getInt("OnGoingCount");
                                int DelayOnGoingCount=jsonObject.getInt("DelayOnGoingCount");
                                Log.e("AAAA", list_name02.get(index02));
                                Log.e("BBBB", ClassName);
                                if(list_name02.get(index02).equals(ClassName)){
                                    Log.e("gg", jsonObject.toString());
                                    mChart01=(PieChart) findViewById(R.id.piechart01);

                                    names01= new String[]{"及时关闭","延期关闭","正常进行","延期进行"};
                                    data01=new double[]{OnTimeCloseCount,DelayCloseCount,OnGoingCount,DelayOnGoingCount};
                                    for(int j=0;j<data01.length;j++){
                                        entries01.add(new PieEntry((float) data01[j],
                                                names01[j],
                                                getResources().getDrawable(R.drawable.star)));
                                    }
                                    setchart01(mChart01,entries01);


                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }




    //2.2.19	我的组织->统计分析->任务关闭率->部门负责的任务
    private void GetOrgClosedRateChartWebDate(String  orgIds,String source,String ByType,String SearchType) {
        String Url = UrlUtil.GetOrgClosedRateChart(UrlUtil.IP, UrlUtil.GetOrgClosedRateCharts, orgIds, source, ByType,SearchType,"");
        Log.e("我负责的任务统计", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);

                        try {
                            JSONObject jsonData = new JSONObject(response);
                            JSONObject jsonObject;


//                            setDate(data_list);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }

    //2.2.20	我的组织->统计分析->任务分类统计->部门任务来源分析
    private void GetOrgTaskTypeBySourceWebDate() {
        String Url = UrlUtil.GetOrgClosedRateChart(UrlUtil.IP, UrlUtil.GetOrgTaskTypeBySource, "", "", "","");
        Log.e("获取新通知列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);

                        try {
                            JSONObject jsonData = new JSONObject(response);
                            JSONObject jsonObject;


//                            setDate(data_list);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }
    //2.2.21	我的组织->统计分析->任务分类统计->部门任务类型分析
    private void GetOrgTaskTypeByClassWebDate() {
        String Url = UrlUtil.GetOrgClosedRateChart(UrlUtil.IP, UrlUtil.GetOrgTaskTypeByClass, "", "", "","");
        Log.e("获取新通知列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);

                        try {
                            JSONObject jsonData = new JSONObject(response);
                            JSONObject jsonObject;


//                            setDate(data_list);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }


    //2.2.22	我的组织->统计分析->评价管理分析->获取平均分
    private void GetAvgScoreDataWebDate(String  orgIds,String source,String SearchType) {
        String Url = UrlUtil.GetAvgScoreDataChart(UrlUtil.IP, UrlUtil.GetAvgScoreDatas, SharedPreferencesTool.getMStool(this).getTenantId(),orgIds, source, SearchType,"");
        Log.e("获取平均分", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        if(response.indexOf("\"")==0) response = response.substring(1,response.length());   //去掉第一个 "
                        if(response.lastIndexOf("\"")==(response.length()-1)) response = response.substring(0,response.length()-1);  //去掉最后一个 "
                        response=response.replaceAll("\"","");
                        response=response.replaceAll("\\\\","\"");
                        Log.e("TAG", response);

                        try {
                            JSONObject jsonObject;
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);
                                String ClassName=jsonObject.getString("ClassName");
                                String ScorePercent=jsonObject.getString("ScorePercent");
                                if(ClassName.equals("公司平均得分")){
                                    tx_Avg01.setText(ScorePercent);
                                }else{
                                    tx_Avg02.setText(ScorePercent);
                                }



                            }



                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }


    //2.2.23	我的组织->统计分析->评价管理分析->公司龙虎榜
    private void GetCompanyTaskCloseRateDataByTopWebDate(String  orgIds,String source,String SearchType) {
        String Url = UrlUtil.GetCompanyTaskCloseRateDataByTop(UrlUtil.IP, UrlUtil.GetCompanyTaskCloseRateDataByTop, SharedPreferencesTool.getMStool(this).getTenantId(), source, SearchType, "");
        Log.e("公司龙虎榜列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);

                        try {
                            JSONObject jsonData = new JSONObject(response);
                            JSONObject jsonObject;


//                            setDate(data_list);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }


    //2.2.21	我的组织->统计分析->任务分类统计->部门任务类型分析
    private void GetOrgTaskCloseRateDataByTopWebDate() {
        String Url = UrlUtil.GetOrgTaskCloseRateDataByTopUrl(UrlUtil.IP, UrlUtil.GetOrgTaskCloseRateDataByTop, "", "", "","");
        Log.e("获取新通知列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);

                        try {
                            JSONObject jsonData = new JSONObject(response);
                            JSONObject jsonObject;


//                            setDate(data_list);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;

            case R.id.neibu_sp2:
                tagss = "one";
                //进行调用接口
                mSpinerPopWindow01.setWidth(neibu_sp2.getWidth());
                mSpinerPopWindow01.showAsDropDown(neibu_sp2);
                setTextImage(R.mipmap.icon_up);
                //进行调用接口
                //getWebDate("MyApply","week");
               // getWebDate("MyToDo","week");
                break;

            case R.id.neibu_sp3:
                tagss = "two";
                //进行调用接口
                mSpinerPopWindow02.setWidth(neibu_sp3.getWidth());
                mSpinerPopWindow02.showAsDropDown(neibu_sp3);
                setTextImage_two(R.mipmap.icon_up);
                //进行调用接口
                //getWebDate("MyApply","week");
                //getWebDate("MyToDo","week");
                break;

            case R.id.title_day:
                //年
                date="week";
                title_day.setBackgroundResource(R.mipmap.data_leftclick);
                title_month.setBackgroundResource(R.mipmap.data_mid);
                title_year.setBackgroundResource(R.mipmap.data_right);
                getWebDate(list_id01.get(index01),list_id02.get(index02)+"","MyApply",date);
                break;

            case R.id.title_month:
                //月
                date="month";
                title_day.setBackgroundResource(R.mipmap.data_left);
                title_month.setBackgroundResource(R.mipmap.data_midclick);
                title_year.setBackgroundResource(R.mipmap.data_right);
                getWebDate(list_id01.get(index01),list_id02.get(index02)+"","MyApply",date);


                break;

            case R.id.title_year:
                //日
                date="year";
                title_day.setBackgroundResource(R.mipmap.data_left);
                title_month.setBackgroundResource(R.mipmap.data_mid);
                title_year.setBackgroundResource(R.mipmap.data_rightclick);
                getWebDate(list_id01.get(index01),list_id02.get(index02)+"","MyApply",date);
                break;


            case R.id.analysis_close:
                //任务关闭率
                //再次下面添加接口方法，获取分析接口
                view01.setVisibility(View.VISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.INVISIBLE);
                analysis_close.setTextColor(android.graphics.Color.parseColor("#1C86EE"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_personage.setTextColor(android.graphics.Color.parseColor("#808080"));

                llayout1.setVisibility(View.VISIBLE);
                llayout2.setVisibility(View.GONE);
                llayout3.setVisibility(View.GONE);
                getWebDate(list_id01.get(index01),list_id02.get(index02)+"","MyApply",date);
                break;


            case R.id.analysis_statistics:
                //任务分类统计
                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.VISIBLE);
                view03.setVisibility(View.INVISIBLE);
                analysis_close.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#1C86EE"));
                analysis_personage.setTextColor(android.graphics.Color.parseColor("#808080"));
                llayout1.setVisibility(View.GONE);
                llayout2.setVisibility(View.VISIBLE);
                llayout3.setVisibility(View.GONE);
                break;


            case R.id.analysis_personage:
                //个人任务评价
                data = getDate();
                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.VISIBLE);
                analysis_close.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_personage.setTextColor(android.graphics.Color.parseColor("#1C86EE"));
                llayout1.setVisibility(View.GONE);
                llayout2.setVisibility(View.GONE);
                llayout3.setVisibility(View.VISIBLE);

                gognsi_data_list = (ListView) findViewById(R.id.gognsi_data_list);//公司龙虎榜
                adapter = new AnalysisAdapter(DataAnalysisActivity.this,data);
                gognsi_data_list.setAdapter(adapter);

                new ScrollListview(gognsi_data_list);
                bumen_data_list = (ListView) findViewById(R.id.bumen_data_list);//部门龙虎榜
                adapter_bumen = new AnalysisAdapter(DataAnalysisActivity.this,data);
                bumen_data_list.setAdapter(adapter_bumen);
                new ScrollListview(bumen_data_list);
                GetAvgScoreDataWebDate(list_id01.get(index01),list_id02.get(index02)+"",date);//2.2.22	我的组织->统计分析->评价管理分析->获取平均分---和我的任务里获取平局分一个接口

                GetCompanyTaskCloseRateDataByTopWebDate(list_id01.get(index01),list_id02.get(index02)+"",date);//2.2.23	我的组织->统计分析->评价管理分析->公司龙虎榜

                break;
        }
    }

    private List<AnalysisDate> getDate() {
        //假数据来源
        List<AnalysisDate> datas = new ArrayList<>();

        for (int i =0;i<6;i++){
            AnalysisDate nd = new AnalysisDate();
            nd.setRanking("排行"+1);
            nd.setName("田巧");
            nd.setSection("技术部");
            nd.setNumber("4.2");
            datas.add(nd);
        }
        return datas;
    }
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {


            if ("one".equals(tagss)){
                neibu_sp2.setText(list_name01.get(position));
                mSpinerPopWindow01.dismiss();
                index01=position;
            }else if("two".equals(tagss)){
                neibu_sp3.setText(list_name02.get(position));
                source= list_id02.get(position);
                mSpinerPopWindow02.dismiss();
                index02=position;
            }
            getWebDate(list_id01.get(index01),list_id02.get(index02)+"","MyApply",date);


        }
    };

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            if ("one".equals(tagss)){
                setTextImage(R.mipmap.icon_down);
                Log.i("Text","21111111111111111111111111111111");
            }else if("two".equals(tagss)){
                Log.i("Text","2222222222222222222222222222222");
                setTextImage_two(R.mipmap.icon_down);
            }
        }
    };

    /**
     * 给TextView右边设置图片
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        neibu_sp2.setCompoundDrawables(null, null, drawable, null);
    }

    private void setTextImage_two(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        neibu_sp3.setCompoundDrawables(null, null, drawable, null);
    }

    //2.2.15	我的组织->获取部门列表
    private void GetOrganizeListWebDate() {
        Log.i("获取部门列表", "111111111111111111111111111");
        //催单客户统计接口
        String Url = UrlUtil.GetOrganizeList(UrlUtil.IP, UrlUtil.GetOrganizeList, SharedPreferencesTool.getMStool(DataAnalysisActivity.this).getUserId());
        Log.e("获取部门列表", Url);
        Log.i("获取部门列表", "22222222222222222222222222222222");
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        GetOrganizeList_list_data = new ArrayList<>();
                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);
                        Log.i("获取部门列表", "33333333333333333333333333333333333");
                        GetOrganizeList_list_data.clear();
                        try {
                            JSONObject jsonObject;
                            JSONObject jsonData;
                            JSONArray arrayData = new JSONArray(response);
                            for(int i = 0; i < arrayData.length(); i++){
                                JSONArray array = new JSONArray(response);
                                for (int j = 0; j < array.length(); j++) {
                                    jsonData = array.getJSONObject(j);
                                    String id = jsonData.getString("id");//任务ID
                                    String text = jsonData.getString("text");//任务内容
                                    mGetOrganizeList_Data = new GetOrganizeList_Data(id, text);
                                    GetOrganizeList_list_data.add(mGetOrganizeList_Data);
                                    Log.e("部门1",GetOrganizeList_list_data.get(i).getText());
                                }
                            }
                            for (int i=0;i<GetOrganizeList_list_data.size();i++){
                                list_name01.add(GetOrganizeList_list_data.get(i).getText());
                                list_id01.add(GetOrganizeList_list_data.get(i).getId());
                                Log.e("部门2",GetOrganizeList_list_data.get(i).getText());
                            }
                            getWebDate(list_id01.get(index01),list_id02.get(index02)+"","MyApply",date);//2.2.18	我的组织->统计分析->任务关闭率->部门发起的任务
                            GetOrgClosedRateChartWebDate(list_id01.get(index01),list_id02.get(index02)+"","MyApply",date);//2.2.19	我的组织->统计分析->任务关闭率->部门负责的任务

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(stringRequest);
    }
    }




