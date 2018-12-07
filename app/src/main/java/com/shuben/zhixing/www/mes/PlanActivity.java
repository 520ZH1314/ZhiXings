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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.shuben.zhixing.www.mes.adapter.PlanAdapter;
import com.shuben.zhixing.www.patrol.bean.SceneInfo;
import com.shuben.zhixing.www.util.DateUtil;
import com.shuben.zhixing.www.util.ScrollListview;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.gudong.drawable.OneDrawable;

import static com.nightonke.boommenu.Util.setDrawable;

public class PlanActivity extends BaseActvity implements View.OnClickListener{
    private LinearLayout lay_back;
    private Button bnt_date;
    private TextView tx_date;
    private String myDate="";
    private NiceSpinner niceSpinner;
    private List<String> list;
    private List<String>  listId;
    private LoadingDailog dialog;//加载动画
    private int index=0;
    private ListView plan_listview;
    private List<SceneInfo> listInfo;


    @Override
    public int getLayoutId() {
        return R.layout.activity_plan;
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
        loadShift();
    }


    private void loadShift() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "EPS");
        params.put("ApiCode", "GetShiftList");
        params.put("TenantId", SharedPreferencesTool.getMStool(PlanActivity.this).getTenantId());
        params.put("page", "1");
        params.put("rows", "20");

        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "EPS");
            mJson.put("ApiCode", "GetShiftList");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(PlanActivity.this).getTenantId());
            mJson.put("page", "1");
            mJson.put("rows", "20");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****加载班次****",mJson.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(PlanActivity.this).getIp(),
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                //Log.e("KKKKKKKK", " " + jsonobj.toString());
                dialog.dismiss();
                try {
                    JSONArray jArray=jsonobj.getJSONArray("rows");
                    JSONObject jData;
                    list=new ArrayList<String>();
                    listId=new ArrayList<String>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);

                        int Seq=jData.getInt("Seq");
                        if(Seq>0){
                            list.add(jData.getString("ShiftName"));
                            listId.add(jData.getString("ShiftCode"));
                        }

                    }
                    if(list.size()>0){
                        niceSpinner.attachDataSource(list);
                        initData(myDate,listId.get(0));
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
    private void initData(String Date,String Shift) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "MiniMes");
        params.put("ApiCode", "GetCurHoursReportaAPI");
        params.put("TenantId", SharedPreferencesTool.getMStool(PlanActivity.this).getTenantId());
        params.put("Date", Date);
        params.put("Shift", Shift);

        JSONObject mJson=new JSONObject();
        try {
            mJson.put("AppCode", "MiniMes");
            mJson.put("ApiCode", "GetCurHoursReportaAPI");
            mJson.put("TenantId", SharedPreferencesTool.getMStool(PlanActivity.this).getTenantId());
            mJson.put("Date", Date);
            mJson.put("Shift", Shift);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("*****加载达成率****",mJson.toString());

        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST, SharedPreferencesTool.getMStool(PlanActivity.this).getIp()+ UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKK加载达成率KKK", " " + jsonobj.toString());
                dialog.dismiss();

                try {
                    JSONArray jArray=jsonobj.getJSONArray("DataTable");
                    Log.e("&&jArray&&",jArray.toString());
                    JSONObject jData;
                    listInfo=new ArrayList<SceneInfo>();
                    for (int i=0;i<jArray.length();i++){
                        jData=jArray.getJSONObject(i);
                        String LineNO=jData.getString("LineCode");
                        String Qty=jData.getInt("Qty")+"";
                        String PlanQty=jData.getInt("PlanQty")+"";
                        String Rate=jData.getInt("Rate")+"";
                        String Difference=jData.getInt("Difference")+"";
                        SceneInfo info=new SceneInfo(LineNO,Qty,PlanQty,Rate);
                        listInfo.add(info);

                    }
                    Log.e("&&&&&","listInfo:"+listInfo.size());
                    PlanAdapter adapter=new PlanAdapter(PlanActivity.this,listInfo);
                    plan_listview.setAdapter(adapter);
                    new ScrollListview(plan_listview);

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


    private void initView() {
        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog=loadBuilder.create();
        myDate=DateUtil.getInstance().getDateOfToDay();
        lay_back=(LinearLayout)findViewById(R.id.lay_back);
        lay_back.setOnClickListener(this);
        bnt_date=(Button)findViewById(R.id.bnt_date);
        bnt_date.setOnClickListener(this);
        setDrawable(bnt_date, OneDrawable.createBgDrawable(this, R.mipmap.ic_arrow_drop_down_black_24dp));
        tx_date=(TextView) findViewById(R.id.tx_date);
        tx_date.setText(DateUtil.getInstance().getDateOfToDay());

        niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner01);
        niceSpinner.setTextColor(Color.rgb(22,155,213));
        niceSpinner.setTextSize(18);
        niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index=position;
                initData(myDate,listId.get(position));
            }
        });
        plan_listview=(ListView)findViewById(R.id.plan_listview);
        View head01=getLayoutInflater().inflate(R.layout.list_plan_title, null);
        TextView tx01=(TextView)head01.findViewById(R.id.tx_item01);
        TextView tx02=(TextView)head01.findViewById(R.id.tx_item02);
        TextView tx03=(TextView)head01.findViewById(R.id.tx_item03);
        TextView tx04=(TextView)head01.findViewById(R.id.tx_item04);
        tx01.setText("线体");
        tx02.setText("计划产量");
        tx03.setText("实际产量");
        tx04.setText("达成率");
        plan_listview.addHeaderView(head01);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.bnt_date:
                setDate();
                break;
            default:

            break;
        }
    }


    private void setDate() {
        Calendar calendar= Calendar.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(PlanActivity.this);
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.date_dialog02, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);

        //设置日期简略显示 否则详细显示 包括:星期\周
        datePicker.setCalendarViewShown(false);
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        //设置date布局
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                StringBuffer sb = new StringBuffer();
                sb.append(String.format("%d-%02d-%02d",
                        datePicker.getYear(),
                        datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth())
                        );
                tx_date.setText(sb);
                myDate= sb.toString();
                // Toast.makeText(context, myDate ,Toast.LENGTH_SHORT).show();
                initData(myDate,listId.get(index));
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
