package com.shuben.zhixing.www.patrol;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.patrol.threeclass.DefinitionActivity;
import com.shuben.zhixing.www.patrol.threeclass.TaskPerformActivity;
import com.shuben.zhixing.www.patrol.twoclass.CallActivity;
import com.shuben.zhixing.www.patrol.twoclass.NotificationActivity;
import com.shuben.zhixing.www.util.CalendarUtil;
import com.shuben.zhixing.www.util.DateUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.view.NiceSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThreeClassActivity extends BaseActvity implements View.OnClickListener {
    private TextView tx_back, tx_patrolDate, tx_workShop, tx_custown, tx_date;
    private Intent intent;
    private EditText ed_place;
    private Button bnt_set, bnt_date01;
    private RelativeLayout m_set_lay01;
    private RelativeLayout m_set_lay02;
    private RelativeLayout m_set_lay03;
    private RelativeLayout m_set_lay04;
    private LinearLayout lay_back;
    private ImageView title_back;

    @Override
    public int getLayoutId() {
        return R.layout.activity_three_class;
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
        initListner();
    }

    private void initListner() {
        title_back.setOnClickListener(this);
        tx_back.setOnClickListener(this);
        lay_back.setOnClickListener(this);
        bnt_set.setOnClickListener(this);
        m_set_lay01.setOnClickListener(this);
        m_set_lay02.setOnClickListener(this);
        m_set_lay03.setOnClickListener(this);
        m_set_lay04.setOnClickListener(this);

    }

    private void initView() {
        tx_back= (TextView) findViewById(R.id.tx_back);
        lay_back= (LinearLayout) findViewById(R.id.lay_back);
        title_back= (ImageView) findViewById(R.id.title_back);
        bnt_set=(Button) findViewById(R.id.bnt_set);
        tx_patrolDate = (TextView) findViewById(R.id.tx_patrolDate);
        tx_custown = (TextView) findViewById(R.id.tx_custown);
        tx_patrolDate.setText(getIntent().getStringExtra("PatrolDate"));
        tx_custown.setText(getIntent().getStringExtra("Countdown"));
        tx_workShop=(TextView) findViewById(R.id.tx_workShop);
        tx_workShop.setText(getIntent().getStringExtra("MeetingPlace"));

        m_set_lay01 = (RelativeLayout) findViewById(R.id.m_set_lay01);
        m_set_lay02 = (RelativeLayout) findViewById(R.id.m_set_lay02);
        m_set_lay03 = (RelativeLayout) findViewById(R.id.m_set_lay03);
        m_set_lay04 = (RelativeLayout) findViewById(R.id.m_set_lay04);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_back:
                setResult(UrlUtil.ThreeClassActivity_ResultCode,intent);
                finish();
                break;
            case R.id.tx_back:
                setResult(UrlUtil.ThreeClassActivity_ResultCode,intent);
                finish();
                break;
            case R.id.title_back:
                setResult(UrlUtil.ThreeClassActivity_ResultCode,intent);
                finish();
                break;
            case R.id.m_set_lay01:
                intent = new Intent();
                intent.setClass(ThreeClassActivity.this, CallActivity.class);
                intent.putExtra("RecordId", getIntent().getStringExtra("RecordId"));
                startActivity(intent);
                break;
            case R.id.m_set_lay02:
                intent = new Intent();
                intent.setClass(ThreeClassActivity.this, DefinitionActivity.class);
                intent.putExtra("RecordId", getIntent().getStringExtra("RecordId"));
                startActivity(intent);
                break;
            case R.id.m_set_lay03:
                intent = new Intent();
                intent.setClass(ThreeClassActivity.this, TaskPerformActivity.class);
                intent.putExtra("RecordId", getIntent().getStringExtra("RecordId"));
                startActivity(intent);
                break;
            case R.id.m_set_lay04:
                intent = new Intent();
                intent.setClass(ThreeClassActivity.this, NotificationActivity.class);
                intent.putExtra("RecordId", getIntent().getStringExtra("RecordId"));
                intent.putExtra("class", "three");
                startActivity(intent);
                break;
            case R.id.bnt_set:
                showPopWindow();
                break;

            default:
                break;
        }
    }

    private void showPopWindow() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ThreeClassActivity.this);
        View view = (LinearLayout) ThreeClassActivity.this.getLayoutInflater().inflate(R.layout.patrol_class_set, null);
        builder.setView(view);
        builder.setTitle("设置");
        tx_date = (TextView) view.findViewById(R.id.tx_date01);
        bnt_date01 = (Button) view.findViewById(R.id.bnt_calendar01);
        ed_place = (EditText) view.findViewById(R.id.ed_place);
        ed_place.setText(tx_workShop.getText().toString());
        tx_date.setText(DateUtil.getInstance().getDateOfToDay02());
        bnt_date01 = (Button) view.findViewById(R.id.bnt_calendar01);
        NiceSpinner nice_spinner01= (NiceSpinner) view.findViewById(R.id.nice_spinner01);
        nice_spinner01.setVisibility(View.GONE);
        bnt_date01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });


        builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //日期格式
                dialog.cancel();
                set(tx_date.getText().toString(), ed_place.getText().toString());


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

    private void set(final String date, final String palce) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "LinePatrol");
        params.put("ApiCode", "EditPatrolSetting");
        params.put("TenantId", SharedPreferencesTool.getMStool(ThreeClassActivity.this).getTenantId());
        params.put("RecordId", getIntent().getStringExtra("RecordId"));
        params.put("PatrolDate", date);//开始时间
        params.put("MeetingPlace", palce);//集合地点
        params.put("SubjectId", "");//主题



        JSONObject data = new JSONObject();
        try {
            data.put("AppCode", "LinePatrol");
            data.put("ApiCode", "EditPatrolSetting");
            data.put("TenantId", SharedPreferencesTool.getMStool(ThreeClassActivity.this).getTenantId());
            data.put("RecordId", getIntent().getStringExtra("RecordId"));
            data.put("PatrolDate", date);//开始时间
            data.put("MeetingPlace", palce);//集合地点
            data.put("SubjectId", "");//主题
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("***********", data.toString());


        JsonObjectRequest newMissRequest = new JsonObjectRequest(
                Request.Method.POST,SharedPreferencesTool.getMStool(ThreeClassActivity.this).getIp()+UrlUtil.Url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonobj) {
                Log.e("KKKKKKKK", " " + jsonobj.toString());
                try {
                    String status = jsonobj.getString("status");
                    final String message = jsonobj.getString("message");
                    if (status.equals("true")) {
                        tx_patrolDate.setText(date);
                        tx_custown.setText(DateUtil.getCountdown(date));
                        tx_workShop.setText(palce);
                        Toast.makeText(ThreeClassActivity.this, "提交问题成功", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ThreeClassActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        newMissRequest.setRetryPolicy(new DefaultRetryPolicy(50000,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(newMissRequest);
    }

    private void setDate() {
        CalendarUtil.getInstance().setDate(ThreeClassActivity.this, tx_date);
    }
}