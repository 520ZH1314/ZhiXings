package com.shuben.zhixing.www.reminder;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
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
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.adapter.AnalysisAdapter;
import com.shuben.zhixing.www.adapter.NeiBuAnalysisAdapter;
import com.shuben.zhixing.www.data.GetCustomerAnalysis_Data;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SpinerPopWindow;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/8/22.
 * 个人资料
 */
public class CuiDanAnalysisTwoActivity  extends BaseActvity implements View.OnClickListener {

    private static String TAG="CuiDanAnalysisTwoActivity";

    private ImageView tetle_back;
    private TextView tetle_text,analysis_close,analysis_statistics,analysis_personage,title_day,title_month,title_year;
    private TextView neibu_sp2,gognsi_fen,me_fen;
    private TextView date_tv1,date_tv2,date_tv3,date_tv4;
    private TextView examine_tv1,examine_tv2,examine_tv3,examine_tv4;
    private TextView three_tv1,three_tv2,three_tv3;
    private TextView four_tv1,four_tv2,four_tv3,four_tv4,four_tv5;

    private LinearLayout llayout1,llayout2,llayout3;

    //催单客户统计
    private List<GetCustomerAnalysis_Data> data_list;
    private GetCustomerAnalysis_Data n_data;


    private ListView gognsi_data_list,bumen_data_list;
    private AnalysisAdapter adapter;
    private NeiBuAnalysisAdapter adapter_data;
    private View  view01,view02,view03;
    private RequestQueue mQueue;

    private SpinerPopWindow<String> mSpinerPopWindow;
    private List<String> list=new ArrayList<String>() ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cuidandataanalysis;
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
        setStatus(-1);
        mQueue = Volley.newRequestQueue(this);
        init();

        mSpinerPopWindow = new SpinerPopWindow<String>(CuiDanAnalysisTwoActivity.this, list,itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);

        GetCustomer();//获取客户清单

//        //2.3.27	内部紧急催单->统计分析->催单客户统计
        // getWebDate();
//
//        //2.3.28	内部紧急催单->统计分析->催单类型统计
//        GetTypeAnalysisWebDate();
//
//        //2.3.29	内部紧急催单->统计分析->获取平均分
        GetEvaluateAnalysisWebDate("week");
//
//        //客户类型接口方法
//        getWebDate();

    }


    private void init() {

        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回
        tetle_back.setOnClickListener(this);
        tetle_text = (TextView) findViewById(R.id.tetle_text);//title

        tetle_text.setText("内部紧急催单分析");

        analysis_close = (TextView) findViewById(R.id.analysis_close);//任务关闭率
        analysis_statistics = (TextView) findViewById(R.id.analysis_statistics);//任务分类统计
        analysis_personage = (TextView) findViewById(R.id.analysis_personage);//个人任务评价
        view01 = (View) findViewById(R.id.view01);//下划线
        view02 = (View) findViewById(R.id.view02);//下划线
        view03 = (View) findViewById(R.id.view03);//下划线

        title_day = (TextView) findViewById(R.id.title_day);//周
        title_month = (TextView) findViewById(R.id.title_month);//月
        title_year = (TextView) findViewById(R.id.title_year);//年


        neibu_sp2 = (TextView) findViewById(R.id.neibu_sp2);//spiner选择按钮

        llayout1 = (LinearLayout) findViewById(R.id.llayout1);//spiner选择按钮
        llayout2 = (LinearLayout) findViewById(R.id.llayout2);//spiner选择按钮
        llayout3 = (LinearLayout) findViewById(R.id.llayout3);//spiner选择按钮

         /*任务类型*/
        four_tv1 = (TextView) findViewById(R.id.four_tv1);//色块人员相关任务
        four_tv2 = (TextView) findViewById(R.id.four_tv2);//色块机械设备相关任务
        four_tv3 = (TextView) findViewById(R.id.four_tv3);//色块物料相关任务
        four_tv4 = (TextView) findViewById(R.id.four_tv4);//色块作业方法相关任务
        four_tv5 = (TextView) findViewById(R.id.four_tv5);//色块环境相关任务


//
       gognsi_fen = (TextView) findViewById(R.id.cui_tx_avg01);//公司平均分
       me_fen = (TextView) findViewById(R.id.cui_tx_avg02);//人平均分



        //催单客户统计
//        gognsi_data_list = (ListView) findViewById(R.id.gognsi_data_list);//spiner选择按钮
//        adapter = new AnalysisAdapter(CuiDanAnalysisTwoActivity.this,data_list);
//        gognsi_data_list.setAdapter(adapter);


        setOnclick();
    }
//    private List<AnalysisDate> getDate() {
//        //假数据来源
//        List<AnalysisDate> datas = new ArrayList<>();
//
//        for (int i =0;i<6;i++){
//            AnalysisDate nd = new AnalysisDate();
//            nd.setRanking("排行"+1);
//            nd.setName("田巧");
//            nd.setSection("技术部");
//            nd.setNumber("4.2");
//            datas.add(nd);
//        }
//        return datas;
//    }


    /**
     * spiner初始化假数据    private String []source01={"全部来源","高效会议","任务交办","内部催单","内部催单"};
     */
   /* private void initData() {
        list = new ArrayList<String>();
        list.add("全部来源");
        list.add("高效会议");
        list.add("任务交办");
        list.add("内部催单");
        list.add("采购催单");
    }*/

    private void setOnclick() {
        analysis_close.setOnClickListener(this);
        analysis_statistics.setOnClickListener(this);
        analysis_personage.setOnClickListener(this);
        title_day.setOnClickListener(this);
        title_month.setOnClickListener(this);
        title_year.setOnClickListener(this);
        analysis_close.setOnClickListener(this);
        neibu_sp2.setOnClickListener(this);
    }
    private void GetCustomer(){
        {

            //催单客户统计接口
            String Url = UrlUtil.GetCustomer(UrlUtil.IP, UrlUtil.GetCustomer);
            Log.e("获取客户清单", Url);
            StringRequest stringRequest = new StringRequest(Url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            response = XmlUtil.getDataByXml(response, "string", TAG);
                            Log.e("TAG", response);
                            list.clear();
                            try {
                                JSONObject jsonObject;
                                JSONObject jsonData=new JSONObject(response);

                                JSONArray array = jsonData.getJSONArray("message");
                                for (int i = 0; i < array.length(); i++) {
                                    jsonObject = array.getJSONObject(i);
                                    String CustomerName = jsonObject.getString("CustomerName");//任务ID
                                    list.add(CustomerName);
                                    //获取部门后添加到列表中
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

    private void getWebDate() {

        //催单客户统计接口
        String Url = UrlUtil.GetCustomerAnalysis(UrlUtil.IP, UrlUtil.GetCustomerAnalysis, SharedPreferencesTool.getMStool(this).getUserId(), SharedPreferencesTool.getMStool(this).getTenantId(), "","");
        Log.e("获取新通知列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        data_list = new ArrayList<>();
                        response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);
                        data_list.clear();
                        try {
                            JSONObject jsonData = new JSONObject(response);
                            JSONObject jsonObject;
                            JSONArray array = jsonData.getJSONArray("rows");
                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);
                                String CustomerName = jsonObject.getString("CustomerName");//任务ID
                                String Analysis = jsonObject.getString("Analysis");//任务内容
                                String Demao = jsonObject.getString("Demao");//任务编号
                                n_data = new GetCustomerAnalysis_Data(CustomerName, Analysis, Demao);
                                data_list.add(n_data);


                                //获得数据后把数据适配到表格中
                                bumen_data_list = (ListView) findViewById(R.id.bumen_data_list);//客户统计排行榜
                                adapter_data = new NeiBuAnalysisAdapter(CuiDanAnalysisTwoActivity.this,data_list);
                                bumen_data_list.setAdapter(adapter_data);



                            }
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


    //2.3.28	内部紧急催单->统计分析->催单类型统计
    private void GetTypeAnalysisWebDate() {
        String Url = UrlUtil.GetTypeAnalysis(UrlUtil.IP, UrlUtil.GetTypeAnalysis, "", "", "","");
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
                            JSONArray array = jsonData.getJSONArray("rows");
                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);
                                String type = jsonObject.getString("type");//任务ID
                                String Total = jsonObject.getString("Total");//任务内容
                                String Value = jsonObject.getString("Value");//任务编号
                                String Seq = jsonObject.getString("Seq");//任务编号

                                //获取数据后，直接赋值到界面上即可。

                                four_tv1.setText("");//色块人员相关任务
                                four_tv2.setText("");//色块机械设备相关任务
                                four_tv3.setText("");//色块物料相关任务
                                four_tv4.setText("");//色块作业方法相关任务
                                four_tv5.setText("");//色块环境相关任务
                            }
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
    }


    //2.3.29	内部紧急催单->统计分析->获取平均分
    private void GetEvaluateAnalysisWebDate(String SearchType) {
        String Url = UrlUtil.GetCustomerAnalysis(UrlUtil.IP, UrlUtil.GetEvaluateAnalysis, SharedPreferencesTool.getMStool(this).getUserId(), SharedPreferencesTool.getMStool(this).getTenantId(),SearchType);
        Log.e("获取新通知列表", Url);
        StringRequest stringRequest = new StringRequest(Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //response = XmlUtil.getDataByXml(response, "string", TAG);
                        Log.e("TAG", response);
                        try {
                            JSONObject jsonData = new JSONObject(response);
                           /* JSONObject jsonObject;
                            JSONArray array = jsonData.getJSONArray("rows");
                            for (int i = 0; i < array.length(); i++) {
                                jsonObject = array.getJSONObject(i);*/

                            Log.e("TAG", jsonData.toString());
                                String UserScore = jsonData.getString("UserScore");//用户平均分
                                String CompanyScore = jsonData.getString("CompanyScore");//任务内容
                                gognsi_fen.setText(CompanyScore);
                                me_fen.setText(UserScore);
                                //获取数据后，直接赋值到界面上即可。

                           // }
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
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;


            case R.id.title_day:
                //年
                title_day.setBackgroundResource(R.mipmap.data_leftclick);
                title_month.setBackgroundResource(R.mipmap.data_mid);
                title_year.setBackgroundResource(R.mipmap.data_right);
                GetEvaluateAnalysisWebDate("week");
                break;

            case R.id.title_month:
                //月
                title_day.setBackgroundResource(R.mipmap.data_left);
                title_month.setBackgroundResource(R.mipmap.data_midclick);
                title_year.setBackgroundResource(R.mipmap.data_right);
                GetEvaluateAnalysisWebDate("month");

                break;

            case R.id.title_year:
                //日
                title_day.setBackgroundResource(R.mipmap.data_left);
                title_month.setBackgroundResource(R.mipmap.data_mid);
                title_year.setBackgroundResource(R.mipmap.data_rightclick);
                GetEvaluateAnalysisWebDate("year");
                break;
            case R.id.neibu_sp2:
                //进行调用接口
                mSpinerPopWindow.setWidth(neibu_sp2.getWidth());
                mSpinerPopWindow.showAsDropDown(neibu_sp2);
                setTextImage(R.mipmap.icon_up);
                //进行调用接口
                break;
            case R.id.analysis_close:
                //任务关闭率
                //再次下面添加接口方法，获取分析接口
                view01.setVisibility(View.VISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.INVISIBLE);
                analysis_close.setTextColor(android.graphics.Color.parseColor("#0099db"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_personage.setTextColor(android.graphics.Color.parseColor("#808080"));

                llayout1.setVisibility(View.VISIBLE);
                llayout2.setVisibility(View.GONE);
                llayout3.setVisibility(View.GONE);


                break;

            case R.id.analysis_statistics:
                //任务分类统计
                //再次下面添加接口方法，获取分析接口
                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.VISIBLE);
                view03.setVisibility(View.INVISIBLE);


                analysis_close.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#0099db"));
                analysis_personage.setTextColor(android.graphics.Color.parseColor("#808080"));
                llayout1.setVisibility(View.GONE);
                llayout2.setVisibility(View.VISIBLE);
                llayout3.setVisibility(View.GONE);
                break;


            case R.id.analysis_personage:
                //个人任务评价
                //再次下面添加接口方法，获取分析接口
                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.INVISIBLE);
                view03.setVisibility(View.VISIBLE);

                analysis_close.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_personage.setTextColor(android.graphics.Color.parseColor("#0099db"));

                llayout1.setVisibility(View.GONE);
                llayout2.setVisibility(View.GONE);
                llayout3.setVisibility(View.VISIBLE);



                break;
        }



    }

   /* private void getWebDate(final String  Source, String SearchType,String DateRange) {
        //接口方法
        {
            {
                String  source="";
                // TODO Auto-generated method stub
                if (source02[index01]==0) {
                    source="";
                }else{
                    source=""+source02[index01];
                }
                String Url = UrlUtil.GetClosedRateChartUrl(UrlUtil.IP, UrlUtil.GetClosedRateChart,source, SharedPreferencesTool.mStool.getUserId(),ByType,SearchType,"");
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
                                    for (int i = 0; i < array.length(); i++) {
                                        jsonObject = array.getJSONObject(i);
                                        Log.e("gg", jsonObject.toString());
                                        String  ClassName=jsonObject.getString("ClassName");
                                        int TotalCount=jsonObject.getInt("TotalCount");
                                        int OnTimeCloseCount=jsonObject.getInt("OnTimeCloseCount");
                                        int DelayCloseCount=jsonObject.getInt("DelayCloseCount");
                                        int OnGoingCount=jsonObject.getInt("OnGoingCount");
                                        int DelayOnGoingCount=jsonObject.getInt("DelayOnGoingCount");
                                        if(ClassName.equals("合计")){
                                            if(ByType.equals("MyApply")){
                                                date_tv1.setText("及时关闭 "+numberFormat.format((float) OnTimeCloseCount / (float) TotalCount * 100)+"%");
                                                date_tv2.setText("延期关闭 "+numberFormat.format((float) DelayCloseCount / (float) TotalCount * 100)+"%");
                                                date_tv3.setText("正常进行 "+numberFormat.format((float) OnGoingCount / (float) TotalCount * 100)+"%");
                                                date_tv4.setText("延期进行 "+numberFormat.format((float) DelayOnGoingCount / (float) TotalCount * 100)+"%");


                                                names= new String[]{"及时关闭","延期关闭","正常进行","延期进行"};
                                                data1=new double[]{OnTimeCloseCount,DelayCloseCount,OnGoingCount,DelayOnGoingCount};
                                                PieChartView2 pieChart = new PieChartView2();
                                                pieChart.setchart(DataAnalysisTwoActivity.this, piechart01, data1, color,names);

                                            }else{
                                                examine_tv1.setText("及时关闭 "+numberFormat.format((float) OnTimeCloseCount / (float) TotalCount * 100)+"%");
                                                examine_tv1.setText("延期关闭 "+numberFormat.format((float) DelayCloseCount / (float) TotalCount * 100)+"%");
                                                examine_tv1.setText("正常进行 "+numberFormat.format((float) OnGoingCount / (float) TotalCount * 100)+"%");
                                                examine_tv1.setText("延期进行 "+numberFormat.format((float) DelayOnGoingCount / (float) TotalCount * 100)+"%");


                                                names= new String[]{"及时关闭","延期关闭","正常进行","延期进行"};
                                                data1=new double[]{OnTimeCloseCount,DelayCloseCount,OnGoingCount,DelayOnGoingCount};
                                                PieChartView2 pieChart = new PieChartView2();
                                                pieChart.setchart(DataAnalysisTwoActivity.this, piechart02, data1, color,names);

                                            }

                                        }




                                    }
                                    //piechart01


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


    }*/




    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mSpinerPopWindow.dismiss();
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
