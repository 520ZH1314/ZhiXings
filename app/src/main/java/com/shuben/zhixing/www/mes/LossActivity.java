package com.shuben.zhixing.www.mes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.mes.adapter.LossAdapter;
import com.shuben.zhixing.www.mes.bean.LossInfo;
import com.zhixing.patrol.util.CalendarUtil;
import com.zhixing.patrol.util.DateUtil;
import com.zhixing.patrol.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LossActivity extends BaseActvity implements View.OnClickListener{
    private LinearLayout lay_back;
    private RelativeLayout rlay01;
    private LoadingDailog dialog;//加载动画
    private ListView mList;
    private List<LossInfo> listInfo;

    private List<String>  list01;
    private List<String>  list02;
    private List<String>  list03;
    private List<String>  listId01;
    private List<String>  listId02;
    private List<String>  listId03;
    private int index01=0,index02=0,index03=0;
    private NiceSpinner niceSpinner01;
    private NiceSpinner niceSpinner02;
    private NiceSpinner niceSpinner03;
    private TextView tx_date01;
    private Button bnt_date01;
    private  TextView tx_set;

    @Override
    public int getLayoutId() {
        return R.layout.activity_loss;
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
        initView();
        showPopWindow();
    }

    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        lay_back=(LinearLayout)findViewById(R.id.lay_back);
        rlay01=(RelativeLayout)findViewById(R.id.rlay01);
        lay_back.setOnClickListener(this);
        mList=(ListView)findViewById(R.id.loss_list);
        tx_set= (TextView) findViewById(R.id.tx_set);
        tx_set.setOnClickListener(this);

    }
    //获取线体列表
    private void initLine() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetLineList");
        params.put("TenantId", SharedPreferencesTool.getMStool(LossActivity.this).getTenantId());

        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "EPS");
            mJson.put("ApiCode", "GetLineList");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(LossActivity.this).getTenantId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****加载线体****",mJson.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(LossActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list01=new ArrayList<String>();
                    listId01=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        int Seq=jData.getInt("Seq");
                        if(Seq>0){
                            list01.add(jData.getString("LineName"));
                            listId01.add(jData.getString("LineCode"));
                        }


                    }
                    if(list01.size()>0){
                        niceSpinner01.attachDataSource(list01);
                        initstation(listId01.get(0));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        dialog.show();
        requestQueue.add(newMissRequest);

    }
    //获取工位列表
    private void initstation(String LineCode) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetLineStationList");
        params.put("TenantId", SharedPreferencesTool.getMStool(LossActivity.this).getTenantId());
        params.put("LineCode", LineCode);
        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "EPS");
            mJson.put("ApiCode", "GetLineStationList");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(LossActivity.this).getTenantId());
            mJson.put("LineCode", LineCode);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****加载工位****",mJson.toString());
        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(LossActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                //Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list02=new ArrayList<String>();
                    listId02=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        list02.add(jData.getString("StationName"));
                        listId02.add(jData.getString("StationCode"));

                    }
                    if(list02.size()>0){
                        niceSpinner02.attachDataSource(list02);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        dialog.show();
        requestQueue.add(newMissRequest);

    }
    private void loadShift() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetShiftList");
        params.put("TenantId", SharedPreferencesTool.getMStool(LossActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");

        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "EPS");
            mJson.put("ApiCode", "GetShiftList");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(LossActivity.this).getTenantId());
            mJson.put("page", "1");
            mJson.put("rows", "20");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****加载班次****",mJson.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(LossActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                //Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list03=new ArrayList<String>();
                    listId03=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);

                        int Seq=jData.getInt("Seq");
                        if(Seq>0){
                            list03.add(jData.getString("ShiftName"));
                            listId03.add(jData.getString("ShiftCode"));
                        }

                    }
                    if(list03.size()>0){
                        niceSpinner03.attachDataSource(list03);
                    }


                    //loadPatrolTask(listId.get(0));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        dialog.show();
        requestQueue.add(newMissRequest);



    }
    private void initData(String Date,String LineNO,String Station,String Shift) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "MiniMes");
        params.put("ApiCode", "GetLostTimeAPI");
        params.put("TenantId", SharedPreferencesTool.getMStool(LossActivity.this).getTenantId());
        params.put("Date", Date);
        params.put("LineCode", LineNO);
        params.put("Station", Station);
        params.put("Shift", Shift);

        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "MiniMes");
            mJson.put("ApiCode", "GetLostTimeAPI");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(LossActivity.this).getTenantId());
            mJson.put("Date", Date);
            mJson.put("LineCode", LineNO);
            mJson.put("Station", Station);
            mJson.put("Shift", Shift);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****损失工时****",mJson.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(LossActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKK损失工时KKK", " " + jsonobj.toString());
                dialog.dismiss();

                try {
                    JSONArray jArray=jsonobj.getJSONArray("DataTable");
                    Log.e("&&jArray&&",jArray.toString());
                    JSONObject jData;
                    listInfo=new ArrayList<LossInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String StartTime=jData.getString("StartTime");
                        String LostTime=jData.getInt("LostTime")+"";
                        String AllLostTime=jData.getInt("AllLostTime")+"";
                        String EndTime=jData.getString("EndTime");
                        String Person=jData.getInt("Person")+"";
                        // String Difference=jData.getInt("Difference")+"";
                        LossInfo info=new LossInfo(StartTime,LostTime,AllLostTime,EndTime,Person);
                        listInfo.add(info);

                    }
                    if(listInfo.size()>0){
                        LossAdapter adapter=new LossAdapter(LossActivity.this,listInfo);
                        mList.setAdapter(adapter);
                        new ScrollListview(mList);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

            }
        });
        newMissRequest.setRetryPolicy( new DefaultRetryPolicy( 50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        dialog.show();
        requestQueue.add(newMissRequest);



    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.tx_set:
              showPopWindow();
                break;
            default:

                break;
        }
    }
    private void setDate(TextView tx_date) {
        CalendarUtil.getInstance().setDate03(LossActivity.this,tx_date);

    }

    private void showPopWindow()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(LossActivity.this);
        View view = (LinearLayout)LossActivity.this. getLayoutInflater().inflate(R.layout.mes_search, null);
        builder.setView(view);
        builder.setTitle("设置");
        niceSpinner01=(NiceSpinner) view.findViewById(R.id.nice_spinner01);
        niceSpinner02=(NiceSpinner) view.findViewById(R.id.nice_spinner02);
        niceSpinner03=(NiceSpinner) view.findViewById(R.id.nice_spinner03);
        niceSpinner01.setTextColor(Color.rgb(22,155,213));
        niceSpinner02.setTextColor(Color.rgb(22,155,213));
        niceSpinner03.setTextColor(Color.rgb(22,155,213));
        niceSpinner01.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index01=position;
                initstation(listId01.get(index01));
            }
        });
        niceSpinner02.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index02=position;

            }
        });
        niceSpinner02.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index03=position;

            }
        });
        //niceSpinner01.setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        //niceSpinner02.setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        //niceSpinner03.setTextSize(TypedValue.COMPLEX_UNIT_PX,36);
        tx_date01=(TextView) view.findViewById(R.id.tx_date01);
        tx_date01.setText(DateUtil.getInstance().getDateOfToDay());
        bnt_date01=(Button) view.findViewById(R.id.bnt_calendar01);
        bnt_date01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(tx_date01);
            }
        });
        initLine();
        loadShift();


        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                initData(tx_date01.getText().toString(),listId01.get(index01),listId02.get(index02),listId03.get(index03));
                dialog.cancel();
            }
        });
        builder.setNegativeButton("取  消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();

    }


}
