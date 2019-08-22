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
import com.shuben.zhixing.www.adapter.CaigouAnalysisAdapter;
import com.shuben.zhixing.www.adapter.GetVendorAnalysisAdapter;
import com.shuben.zhixing.www.data.AnalysisDate;
import com.shuben.zhixing.www.data.GetVendorAnalysis_Data;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.util.SpinerPopWindow;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.util.XmlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/22.
 * 个人资料
 */

public class CaiGouAnalysisTwoActivity extends BaseActvity implements View.OnClickListener {
    private ImageView tetle_back;
    private TextView tetle_text,analysis_close,analysis_statistics,title_day,title_month,title_year;
    private TextView neibu_sp2;
    private TextView examine_tv1,examine_tv2,examine_tv3,examine_tv4;
    private TextView three_tv1,three_tv2,three_tv3;
    private TextView four_tv1,four_tv2,four_tv3,four_tv4,four_tv5;

    private LinearLayout llayout1,llayout2;
    private List<AnalysisDate> data;
    private ListView caigou_data_list,caigou_Twodata_list;
    private GetVendorAnalysisAdapter adapter;
    private CaigouAnalysisAdapter adapter_data;


    private SpinerPopWindow<String> mSpinerPopWindow;
    private List<String> list;
    private View  view01,view02;

    //2.3.31	采购紧急催单->统计分析->催单供应商统计
    private List<GetVendorAnalysis_Data> data_list;
    private GetVendorAnalysis_Data n_data;

    private RequestQueue mQueue;

    @Override
    public int getLayoutId() {
        return R.layout.activity_caigou_dataanalysis;
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
        initData();//spiner假数据
        mSpinerPopWindow = new SpinerPopWindow<String>(CaiGouAnalysisTwoActivity.this, list,itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);
        //2.3.31	采购紧急催单->统计分析->催单供应商统计
        getWebDate();

        // 2.3.32	采购紧急催单->统计分析->催单分类统计(多余的接口，采购催单没有统计这一页)--和--2.3.28	内部紧急催单->统计分析->催单类型统计一样
        //GetTypeAnalysisWebDate();

        // 2.3.33	采购紧急催单->统计分析->获取平均分( 多余的接口，采购催单没有统计这一页 )--和--2.3.29	内部紧急催单->统计分析->获取平均分一样
        GetEvaluateAnalysisWebDate();
    }

    private void init() {
//        data = getDate();

//        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回
//        tetle_back.setOnClickListener(this);
        tetle_text = (TextView) findViewById(R.id.tetle_text);//title

        tetle_text.setText("采购紧急催单分析");

        neibu_sp2 = (TextView) findViewById(R.id.neibu_sp2);//spiner选择条件

        analysis_close = (TextView) findViewById(R.id.analysis_close);//任务关闭率
        analysis_statistics = (TextView) findViewById(R.id.analysis_statistics);//任务分类统计

        view01 = (View) findViewById(R.id.view01);//下划线
        view02 = (View) findViewById(R.id.view02);//下划线
        title_day = (TextView) findViewById(R.id.title_day);//日
        title_month = (TextView) findViewById(R.id.title_month);//月
        title_year = (TextView) findViewById(R.id.title_year);//年

        llayout1 = (LinearLayout) findViewById(R.id.llayout1);//
        llayout2 = (LinearLayout) findViewById(R.id.llayout2);//

        setOnclick();
    }


    /**
     * spiner初始化假数据
     */
    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            list.add("你好:" + i);
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


    private void setOnclick() {
        analysis_close.setOnClickListener(this);
        analysis_statistics.setOnClickListener(this);
        title_day.setOnClickListener(this);
        title_month.setOnClickListener(this);
        title_year.setOnClickListener(this);
        analysis_close.setOnClickListener(this);
        neibu_sp2.setOnClickListener(this);
    }


    private void getWebDate() {

        //催单客户统计接口
        String Url = UrlUtil.GetVendorAnalysis(UrlUtil.IP, UrlUtil.GetVendorAnalysis, SharedPreferencesTool.getMStool(this).getUserId(), SharedPreferencesTool.getMStool(this).getTenantId(), "","");
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
                                String VendorName = jsonObject.getString("VendorName");//任务ID
                                String Analysis = jsonObject.getString("Analysis");//任务内容
                                String Demao = jsonObject.getString("Demao");//任务编号
                                n_data = new GetVendorAnalysis_Data(VendorName, Analysis, Demao);
                                data_list.add(n_data);

                                //获得数据后把数据适配到表格中

                                caigou_data_list = (ListView) findViewById(R.id.caigou_data_list);//公司龙虎榜
                                adapter = new GetVendorAnalysisAdapter(CaiGouAnalysisTwoActivity.this,data_list);
                                caigou_data_list.setAdapter(adapter);




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



    //2.3.29	内部紧急催单->统计分析->获取平均分--多余的接口
    private void GetEvaluateAnalysisWebDate() {
        String Url = UrlUtil.GetCustomerAnalysis(UrlUtil.IP, UrlUtil.GetEvaluateAnalysis, "", "", "");
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

                                String UserScore = jsonObject.getString("UserScore");//用户平均分
                                String CompanyScore = jsonObject.getString("CompanyScore");//任务内容

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

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;
            case R.id.neibu_sp2:
                mSpinerPopWindow.setWidth(neibu_sp2.getWidth());
                mSpinerPopWindow.showAsDropDown(neibu_sp2);
                setTextImage(R.mipmap.icon_up);
                break;
            case R.id.title_day:
                //年
                title_day.setBackgroundResource(R.mipmap.data_leftclick);
                title_month.setBackgroundResource(R.mipmap.data_mid);
                title_year.setBackgroundResource(R.mipmap.data_right);

//                title_month.setTextColor(android.graphics.Color.parseColor("#808080"));
                break;

            case R.id.title_month:
                //月
                title_day.setBackgroundResource(R.mipmap.data_left);
                title_month.setBackgroundResource(R.mipmap.data_midclick);
                title_year.setBackgroundResource(R.mipmap.data_right);

                break;

            case R.id.title_year:
                //日
                title_day.setBackgroundResource(R.mipmap.data_left);
                title_month.setBackgroundResource(R.mipmap.data_mid);
                title_year.setBackgroundResource(R.mipmap.data_rightclick);
                break;



            case R.id.analysis_close:
                //任务关闭率
                //再次下面添加接口方法，获取分析接口
                view01.setVisibility(View.VISIBLE);
                view02.setVisibility(View.INVISIBLE);


                analysis_close.setTextColor(android.graphics.Color.parseColor("#0099db"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#808080"));

                llayout1.setVisibility(View.VISIBLE);
                llayout2.setVisibility(View.GONE);



                break;

            case R.id.analysis_statistics:
                //任务分类统计
                //再次下面添加接口方法，获取分析接口
                view01.setVisibility(View.INVISIBLE);
                view02.setVisibility(View.VISIBLE);

                analysis_close.setTextColor(android.graphics.Color.parseColor("#808080"));
                analysis_statistics.setTextColor(android.graphics.Color.parseColor("#0099db"));
                llayout1.setVisibility(View.GONE);
                llayout2.setVisibility(View.VISIBLE);

//                caigou_Twodata_list = (ListView) findViewById(R.id.caigou_Twodata_list);//公司龙虎榜
//                adapter_data = new CaigouAnalysisAdapter(CaiGouAnalysisTwoActivity.this,data);
//                caigou_Twodata_list.setAdapter(adapter_data);
                break;
        }
    }



    /**
     * popupwindow显示的ListView的item点击事件
     */
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
