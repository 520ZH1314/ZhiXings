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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
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
import com.github.mikephil.charting.utils.MPPointF;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.adapter.AnalysisAdapter;
import com.shuben.zhixing.www.data.AnalysisDate;
import com.shuben.zhixing.www.util.DataChangeUtil;
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
 * 我的任务分析
 */

public class DataAnalysisTwoActivity extends BaseActvity implements View.OnClickListener {
    private static String TAG="";
    private ImageView tetle_back;
    private TextView tetle_text,analysis_close,analysis_statistics,analysis_personage,title_day,title_month,title_year;
    private Spinner fm_sp2,fm_sp3;
    private TextView date_tv1,date_tv2,date_tv3,date_tv4;
    private TextView examine_tv1,examine_tv2,examine_tv3,examine_tv4;
    private TextView three_tv1,three_tv2,three_tv3;
    private TextView four_tv1,four_tv2,four_tv3,four_tv4,four_tv5;
    private TextView  tx_Avg01,tx_Avg02,tx_Avg03,neibu_sp2;

    private LinearLayout llayout1,llayout2,llayout3;
    private List<AnalysisDate> data;
    private ListView gognsi_data_list;
    private AnalysisAdapter adapter;
    private RequestQueue mQueue;

    private List<String> list_name = new ArrayList<String>();
    private List<String> list1 = new ArrayList<String>();
    private List<Integer> list2 = new ArrayList<Integer>();

    private int color[] = new int[]{Color.rgb(200, 0, 0),Color.rgb(0, 200, 0),Color.rgb(0, 0, 200),Color.rgb(200, 200, 0)};//设备
    private int color2[] = new int[]{Color.rgb(200, 0, 0),Color.rgb(0, 200, 0),Color.rgb(0, 0, 200)};//设备
    private int color3[] = new int[]{Color.rgb(200, 0, 0),Color.rgb(0, 200, 0),Color.rgb(0, 0, 200),Color.rgb(200, 200, 0),Color.rgb(0, 200, 200)};//设备
    LinearLayout piechart01,piechart02,piechart03,piechart04;

    private int []source02={0,1,2,3,4};
    private ArrayAdapter<String> adapter1;
    private int id1=android.R.layout.simple_spinner_item;
    private int id2=android.R.layout.simple_spinner_dropdown_item;
    private int index01=0;
    private View  view01,view02,view03;

    private SpinerPopWindow<String> mSpinerPopWindow;
    private List<String> list ;

    private PieChart mChart01,mChart02,mChart03,mChart04;
    private ArrayList<PieEntry> entries01=new ArrayList<PieEntry>();
    private ArrayList<PieEntry> entries02=new ArrayList<PieEntry>();
    private ArrayList<PieEntry> entries03=new ArrayList<PieEntry>();
    private ArrayList<PieEntry> entries04=new ArrayList<PieEntry>();

    private String date="week";
    private Typeface tf;


    @Override
    public int getLayoutId() {
        return R.layout.activity_dataanalysis_two;
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
        init();
        initData();//spiner假数据
        mSpinerPopWindow = new SpinerPopWindow<String>(DataAnalysisTwoActivity.this, list,itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);
    }

    private void init() {
        mQueue = Volley.newRequestQueue(this);
        data = getDate();
        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回
        tetle_back.setOnClickListener(this);
        tetle_text = (TextView) findViewById(R.id.tetle_text);//title

        tetle_text.setText("任务数据分析");

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

        //fm_sp3 = (Spinner) findViewById(R.id.fm_sp3);//spiner选择按钮
        llayout1 = (LinearLayout) findViewById(R.id.llayout1);//spiner选择按钮
        llayout2 = (LinearLayout) findViewById(R.id.llayout2);//spiner选择按钮
        llayout3 = (LinearLayout) findViewById(R.id.llayout3);//spiner选择按钮

        /*我负责的任务*/
        /*date_tv1 = (TextView) findViewById(R.id.date_tv1);//色块准时关闭
        date_tv2 = (TextView) findViewById(R.id.date_tv2);//色块延迟关闭
        date_tv3 = (TextView) findViewById(R.id.date_tv3);//色块正常进行
        date_tv4 = (TextView) findViewById(R.id.date_tv4);//色块延迟进行*/

        /*我检查的任务*/
        /*examine_tv1 = (TextView) findViewById(R.id.examine_tv1);//色块准时关闭
        examine_tv2 = (TextView) findViewById(R.id.examine_tv2);//色块延迟关闭
        examine_tv3 = (TextView) findViewById(R.id.examine_tv3);//色块正常进行
        examine_tv4 = (TextView) findViewById(R.id.examine_tv4);//色块延迟进行中*/

       /*任务来源*/

      /*  three_tv1 = (TextView) findViewById(R.id.three_tv1);//色块高效会议
        three_tv2 = (TextView) findViewById(R.id.three_tv2);//色块工作交办
        three_tv3 = (TextView) findViewById(R.id.three_tv3);//色块紧急催单*/


         /*任务类型*/
       // four_tv1 = (TextView) findViewById(R.id.three_tv1);//色块人员相关任务
        four_tv2 = (TextView) findViewById(R.id.four_tv2);//色块机械设备相关任务
        four_tv3 = (TextView) findViewById(R.id.four_tv3);//色块物料相关任务
        four_tv4 = (TextView) findViewById(R.id.four_tv4);//色块作业方法相关任务
        four_tv5 = (TextView) findViewById(R.id.four_tv5);//色块环境相关任务

        tx_Avg01=(TextView)findViewById(R.id.tx_Avg01);
        tx_Avg02=(TextView)findViewById(R.id.tx_Avg02);
        tx_Avg03=(TextView)findViewById(R.id.tx_Avg03);



         /*piechart01=(LinearLayout) findViewById(R.id.piechart01);
         piechart02=(LinearLayout) findViewById(R.id.piechart02);
         piechart03=(LinearLayout) findViewById(R.id.piechart03);
         piechart04=(LinearLayout) findViewById(R.id.piechart04);*/

         getWebDate01(index01,"MyApply",date);//任务关闭(我发起的任务)
         getWebDate02(index01,"MyToDo",date);//任务关闭(我负责的任务)


        setOnclick();
    }
    /**
     * spiner初始化假数据    private String []source01={"全部来源","高效会议","任务交办","内部催单","内部催单"};
     */
    private void initData() {
        list = new ArrayList<String>();
        list.add("全部来源");
        list.add("高效会议");
        list.add("任务交办");
        list.add("内部催单");
        list.add("紧急催单");
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


    private void setOnclick() {
        analysis_close.setOnClickListener(this);
        analysis_statistics.setOnClickListener(this);
        analysis_personage.setOnClickListener(this);
        title_month.setOnClickListener(this);
        title_year.setOnClickListener(this);
        title_day.setOnClickListener(this);
        analysis_close.setOnClickListener(this);
        neibu_sp2.setOnClickListener(this);
    }


    private void getWebDate01(final int index, final String  ByType, String SearchType) {
        //接口方法
        {
            {
                String  source="";
                // TODO Auto-generated method stub
                if (source02[index]==0) {
                    source="";
                }else{
                    source=""+source02[index];
                }
                String Url = UrlUtil.GetClosedRateChartUrl(UrlUtil.IP, UrlUtil.GetClosedRateChart,source,SharedPreferencesTool.getMStool(DataAnalysisTwoActivity.this).getUserId(),ByType,SearchType,"");
                Log.e("任务关闭率", Url);
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
                                        Log.e("AAAA", DataChangeUtil.getInstance().getSource(source02[index01]));
                                        Log.e("BBBB", ClassName);
                                        if(DataChangeUtil.getInstance().getSource(source02[index01]).equals(ClassName)){
                                            Log.e("gg", jsonObject.toString());
                                                mChart01=(PieChart) findViewById(R.id.piechart01);

                                                names01= new String[]{"及时关闭","延期关闭","正常进行","延期进行"};
                                                data01=new double[]{OnTimeCloseCount,DelayCloseCount,OnGoingCount,DelayOnGoingCount};
                                                for(int j=0;j<data01.length;j++){
                                                    entries01.add(new PieEntry((float) data01[j],
                                                            names01[j],
                                                            getResources().getDrawable(R.drawable.star)));
                                                }
                                                setchart(mChart01,entries01);


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

        }


    }
    private void getWebDate02(final int index, final String  ByType, String SearchType) {
        //接口方法
        {
            {
                String  source="";
                // TODO Auto-generated method stub
                if (source02[index]==0) {
                    source="";
                }else{
                    source=""+source02[index];
                }
                String Url = UrlUtil.GetClosedRateChartUrl(UrlUtil.IP, UrlUtil.GetClosedRateChart,source,SharedPreferencesTool.getMStool(DataAnalysisTwoActivity.this).getUserId(),ByType,SearchType,"");
                Log.e("任务关闭率", Url);
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
                                    double[] data02;
                                    String [] names02;
                                    entries02.clear();
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
                                        Log.e("AAAA", DataChangeUtil.getInstance().getSource(source02[index01]));
                                        Log.e("BBBB", ClassName);
                                        if(DataChangeUtil.getInstance().getSource(source02[index01]).equals(ClassName)){
                                            Log.e("gg", jsonObject.toString());
                                                mChart02=(PieChart) findViewById(R.id.piechart02);
                                                names02= new String[]{"及时关闭","延期关闭","正常进行","延期进行"};
                                                data02=new double[]{OnTimeCloseCount,DelayCloseCount,OnGoingCount,DelayOnGoingCount};
                                                for(int j=0;j<data02.length;j++){
                                                    entries02.add(new PieEntry((float) data02[j],
                                                            names02[j],
                                                            getResources().getDrawable(R.drawable.star)));
                                                }
                                                setchart(mChart02,entries02);
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

        }


    }


    private void getSourceDate(int index, String SearchType) {
        //接口方法
        {
            {
                String  source="";
                // TODO Auto-generated method stub
                if (source02[index]==0) {
                    source="";
                }else{
                    source=""+source02[index];
                }

                // TODO Auto-generated method stub
                String Url = UrlUtil.GetTaskTypeBySourceChartUrl(UrlUtil.IP, UrlUtil.GetTaskTypeBySourceChart,source,SharedPreferencesTool.getMStool(DataAnalysisTwoActivity.this).getUserId(),SearchType,"");
                Log.e("任务分类统计", Url);
                StringRequest stringRequest = new StringRequest(Url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                List<String> list_name=new ArrayList<String>();
                                List<Double> list_percent=new ArrayList<Double>();
                                 double[] percent=new double[3];
                                 String name[] = new String[3];
                                response = XmlUtil.getDataByXml(response, "string", TAG);
                                if(response.indexOf("\"")==0) response = response.substring(1,response.length());   //去掉第一个 "
                                if(response.lastIndexOf("\"")==(response.length()-1)) response = response.substring(0,response.length()-1);  //去掉最后一个 "
                                response=response.replaceAll("\"","");
                                response=response.replaceAll("\\\\","\"");
                                entries03.clear();
                                try {
                                    JSONObject jsonObject;
                                    JSONArray array = new JSONArray(response);
                                    NumberFormat numberFormat = NumberFormat.getInstance();
                                    numberFormat.setMaximumFractionDigits(2);
                                    for (int i = 0; i < array.length(); i++) {
                                        jsonObject = array.getJSONObject(i);

                                        String  ClassName=jsonObject.getString("ClassName");
                                        double Percent=jsonObject.getInt("Percent")/100;
                                        list_name.add(ClassName);
                                        list_percent.add(Percent);
                                            }

                                    if(list_name.size()>0){
                                       for(int i=0;i<list_name.size();i++){
                                            name[i] =list_name.get(i);
                                            percent[i]=list_percent.get(i);
                                        }
                                        /*name= new String[]{"高效会议","任务交办","紧急催单"};
                                        percent=new double[]{1,1,10};
                                        Log.e("gg1", list_name.toString()); Log.e("gg2", list_percent.toString());*/

                                    }else {
                                         name= new String[]{"高效会议","任务交办","紧急催单"};
                                         percent=new double[]{0,0,0};
                                    }
                                   mChart03=(PieChart) findViewById(R.id.piechart03);
                                    for(int i=0;i<percent.length;i++){
                                        entries03.add(new PieEntry((float) percent[i],
                                                name[i],
                                                getResources().getDrawable(R.drawable.star)));
                                    }
                                    setchart(mChart03,entries03);

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


    }


    private void getTypeDate(int index, String SearchType) {
        //接口方法
        {
            {
                String  source="";
                // TODO Auto-generated method stub
                if (source02[index]==0) {
                    source="";
                }else{
                    source=""+source02[index];
                }
                // TODO Auto-generated method stub
                String Url = UrlUtil.GetTaskTypeBySourceChartUrl(UrlUtil.IP, UrlUtil.GetTaskTypeChartByClass,source,SharedPreferencesTool.getMStool(DataAnalysisTwoActivity.this).getUserId(),SearchType,"");
                Log.e("任务分类统计", Url);
                StringRequest stringRequest = new StringRequest(Url,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                List<String> list_name=new ArrayList<String>();
                                List<Double> list_percent=new ArrayList<Double>();
                                double[] percent=new double[5];
                                String name[] = new String[5];
                                response = XmlUtil.getDataByXml(response, "string", TAG);
                                if(response.indexOf("\"")==0) response = response.substring(1,response.length());   //去掉第一个 "
                                if(response.lastIndexOf("\"")==(response.length()-1)) response = response.substring(0,response.length()-1);  //去掉最后一个 "
                                response=response.replaceAll("\"","");
                                response=response.replaceAll("\\\\","\"");
                                entries04.clear();
                                try {
                                    JSONObject jsonObject;
                                    JSONArray array = new JSONArray(response);
                                    NumberFormat numberFormat = NumberFormat.getInstance();
                                    numberFormat.setMaximumFractionDigits(2);
                                    for (int i = 0; i < array.length(); i++) {
                                        jsonObject = array.getJSONObject(i);

                                        String  ClassName=jsonObject.getString("ClassName");
                                        double Percent=jsonObject.getInt("Percent")/100;
                                        list_name.add(ClassName);
                                        list_percent.add(Percent);
                                    }

                                    if(list_name.size()>0){
                                       for(int i=0;i<list_name.size();i++){
                                            name[i] =list_name.get(i);
                                            percent[i]=list_percent.get(i);
                                        }
                                       /* name= new String[]{"人","机","料","法","环"};
                                        percent=new double[]{0,0,0,0,0};*/


                                    }else {
                                        name= new String[]{"人","机","料","法","环"};
                                        percent=new double[]{0,0,0,0,0};
                                        mChart04=(PieChart) findViewById(R.id.piechart04);
                                        for(int i=0;i<percent.length;i++){
                                            entries04.add(new PieEntry((float) percent[i],
                                                    name[i],
                                                    getResources().getDrawable(R.drawable.star)));
                                        }
                                        setchart(mChart04,entries04);
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

        }


    }

    private void getAvgScoreData(int index,String SearchType) {
        //接口方法
        {
            {
                String  source="";
                // TODO Auto-generated method stub
                if (source02[index]==0) {
                    source="";
                }else{
                    source=""+source02[index];
                }
                // TODO Auto-generated method stub
                String Url = UrlUtil.GetAvgScoreDataUrl(UrlUtil.IP, UrlUtil.GetAvgScoreData,SharedPreferencesTool.getMStool(DataAnalysisTwoActivity.this).getTenantId(),SharedPreferencesTool.getMStool(DataAnalysisTwoActivity.this).getUserId(),source,SearchType,"");
                Log.e("获取平均分", Url);
                StringRequest stringRequest = new StringRequest(Url,
                        new Response.Listener<String>() {
                            List<String> list_name=new ArrayList<String>();
                            List<Double> list_percent=new ArrayList<Double>();
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
                                    for (int i = 0; i < array.length(); i++) {
                                        jsonObject = array.getJSONObject(i);
                                        String  ClassName=jsonObject.getString("ClassName");
                                        double AvgScore=jsonObject.getInt("AvgScore");
                                        list_name.add(ClassName);
                                        list_percent.add(AvgScore);

                                    }

                                    if(list_name.size()>0){
                                        tx_Avg01.setText(""+list_percent.get(0));
                                        tx_Avg02.setText(""+list_percent.get(1));
                                        tx_Avg03.setText(""+list_percent.get(2));
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

        }


    }

    private void getBill(int index,String SearchType) {
        //接口方法
        {
            {
                String  source="";
                // TODO Auto-generated method stub
                if (source02[index]==0) {
                    source="";
                }else{
                    source=""+source02[index];
                }
                // TODO Auto-generated method stub
                String Url = UrlUtil.GetTaskCloseRateDataByTopUrl(UrlUtil.IP, UrlUtil.GetTaskCloseRateDataByTop,SharedPreferencesTool.getMStool(DataAnalysisTwoActivity.this).getTenantId(),source,SearchType,"");
                Log.e("获取平均分", Url);
                StringRequest stringRequest = new StringRequest(Url,
                        new Response.Listener<String>() {
                            List<String> list_name=new ArrayList<String>();
                            List<Double> list_percent=new ArrayList<Double>();
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
                                    for (int i = 0; i < array.length(); i++) {
                                        jsonObject = array.getJSONObject(i);
                                        String  ClassName=jsonObject.getString("ClassName");
                                        double AvgScore=jsonObject.getInt("AvgScore");
                                        list_name.add(ClassName);
                                        list_percent.add(AvgScore);

                                    }

                                    if(list_name.size()>0){
                                        tx_Avg01.setText(""+list_percent.get(0));
                                        tx_Avg02.setText(""+list_percent.get(1));
                                        tx_Avg03.setText(""+list_percent.get(2));
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

        }


    }
    private void setchart(PieChart mChart,List<PieEntry> entries ) {
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

    private void setData( List<PieEntry> entries,PieChart pieChart) {


        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
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

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);
        data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }
    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.2f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }







    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;

            case R.id.neibu_sp2:
                //进行调用接口
                mSpinerPopWindow.setWidth(neibu_sp2.getWidth());
                mSpinerPopWindow.showAsDropDown(neibu_sp2);
                setTextImage(R.mipmap.icon_up);
                //进行调用接口

                break;


            case R.id.title_day:
                //年
                date="week";
                getWebDate01(index01,"MyApply",date);//任务关闭(我发起的任务)
                getWebDate02(index01,"MyToDo",date);//任务关闭(我负责的任务)
                getSourceDate(index01,date);//任务分类统计（任务来源）
                getTypeDate(index01,date);//任务分类统计（任务类型）
                getAvgScoreData(index01,date);
                title_day.setBackgroundResource(R.mipmap.data_leftclick);
                title_month.setBackgroundResource(R.mipmap.data_mid);
                title_year.setBackgroundResource(R.mipmap.data_right);
                break;

            case R.id.title_month:
                //月
                date="month";
                getWebDate01(index01,"MyApply",date);//任务关闭(我发起的任务)
                getWebDate02(index01,"MyToDo",date);//任务关闭(我负责的任务)
                getSourceDate(index01,date);//任务分类统计（任务来源）
                getTypeDate(index01,date);//任务分类统计（任务类型）
                getAvgScoreData(index01,date);
                title_day.setBackgroundResource(R.mipmap.data_left);
                title_month.setBackgroundResource(R.mipmap.data_midclick);
                title_year.setBackgroundResource(R.mipmap.data_right);

                break;

            case R.id.title_year:
                //日
                date="year";
                getWebDate01(index01,"MyApply",date);//任务关闭(我发起的任务)
                getWebDate02(index01,"MyToDo",date);//任务关闭(我负责的任务)
                getSourceDate(index01,date);//任务分类统计（任务来源）
                getTypeDate(index01,date);//任务分类统计（任务类型）
                getAvgScoreData(index01,date);
                title_day.setBackgroundResource(R.mipmap.data_left);
                title_month.setBackgroundResource(R.mipmap.data_mid);
                title_year.setBackgroundResource(R.mipmap.data_rightclick);

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
                getWebDate01(index01,"MyApply",date);//任务关闭(我发起的任务)
                getWebDate02(index01,"MyToDo",date);//任务关闭(我负责的任务)
                break;


            case R.id.analysis_statistics:
                //任务分类统计
                //再次下面添加接口方法，获取分析接口
                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.VISIBLE);
                view03.setVisibility(View.INVISIBLE);


                analysis_close.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#1C86EE"));
                analysis_personage.setTextColor(android.graphics.Color.parseColor("#808080"));
                llayout1.setVisibility(View.GONE);
                llayout2.setVisibility(View.VISIBLE);
                llayout3.setVisibility(View.GONE);
                getSourceDate(index01,date);//任务分类统计（任务来源）
                getTypeDate(index01,date);//任务分类统计（任务类型）

                break;


            case R.id.analysis_personage:
                //个人任务评价
                //再次下面添加接口方法，获取分析接口
                data = getDate();//假数据
                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.VISIBLE);
                analysis_close.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_personage.setTextColor(android.graphics.Color.parseColor("#1C86EE"));

                llayout1.setVisibility(View.GONE);
                llayout2.setVisibility(View.GONE);
                llayout3.setVisibility(View.VISIBLE);

                //假数据
                gognsi_data_list = (ListView) findViewById(R.id.gognsi_data_list);//spiner选择按钮
                adapter = new AnalysisAdapter(DataAnalysisTwoActivity.this,data);
                gognsi_data_list.setAdapter(adapter);

                new ScrollListview(gognsi_data_list);
                getAvgScoreData(index01,date);
                break;
        }



    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            index01=position;
            mSpinerPopWindow.dismiss();

            getWebDate01(position,"MyApply",date);//任务关闭(我发起的任务)
            getWebDate02(position,"MyToDo",date);//任务关闭(我负责的任务)
            getSourceDate(position,date);//任务分类统计（任务来源）
            //getTypeDate(position,date);//任务分类统计（任务类型）

            neibu_sp2.setText(list.get(position));
        }
    };

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setTextImage(R.mipmap.icon_down);
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
}
