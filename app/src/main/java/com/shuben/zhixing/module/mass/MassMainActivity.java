package com.shuben.zhixing.module.mass;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.base.zhixing.www.view.Toasty;
import com.shuben.zhixing.module.mass.bean.QC_Reason;
import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.dataBase.DB;
import com.base.zhixing.www.inter.VolleyResult;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.base.zhixing.www.util.UrlUtil;
import com.shuben.zhixing.www.dataBase.MassDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MassMainActivity extends BaseActvity {
    @Override
    public int getLayoutId() {
        return R.layout.mass_main_layout;
    }

    @Override
    public void process(Message msg) {
        switch (msg.what){
            case 0:
                break;
            case 2:
                MassDB.getInstance().addQC_Reason(qc_reasons);
                break;
        }

    }
    private TextView tetle_text;
    private RelativeLayout item0_layout,item1_layout,item2_layout,item3_layout;
    @Override
    public void initLayout() {
        setStatus(-1);
        tetle_text  = findViewById(R.id.tetle_text);
        tetle_text.setText("Mini-质量执行");
        item0_layout = findViewById(R.id.item0_layout);
        item1_layout = findViewById(R.id.item1_layout);
        item2_layout = findViewById(R.id.item2_layout);
        item3_layout = findViewById(R.id.item3_layout);



        item0_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.INSTANCE.showToast(MassMainActivity.this,"功能完善中");

              /*  Intent intent = new Intent(MassMainActivity.this,EnterMassActivity.class);
                intent.putExtra("title","首件检验");
                intent.putExtra("left_t","首件检验");
                intent.putExtra("right_t","首件报告记录");
                intent.putExtra("btn_t","添加首件检验");
                intent.putExtra("type",0);
                startActivity(intent);*/
            }
        });
        item1_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MassMainActivity.this,EnterMassActivity.class);
                intent.putExtra("title","全检");
                intent.putExtra("left_t","开始全检");
                intent.putExtra("right_t","全检报告记录");
                intent.putExtra("btn_t","添加全检检验");
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        item2_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MassMainActivity.this,EnterMassActivity.class);
                intent.putExtra("title","抽检");
                intent.putExtra("left_t","开始抽检");
                intent.putExtra("right_t","抽检报告记录");
                intent.putExtra("btn_t","添加抽检检验");
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
        item3_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MassMainActivity.this,EnterMassActivity.class);
                intent.putExtra("title","维修");
                intent.putExtra("left_t","维修单填写");
                intent.putExtra("right_t","维修报告记录");
                intent.putExtra("btn_t","添加维修项");
                intent.putExtra("type",3);
                startActivity(intent);
            }
        });
        initData();
    }
    private ArrayList<QC_Reason> qc_reasons = new ArrayList<>();
    //初始化加载数据
    private void initData(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("AppCode", "QC");
        params.put("ApiCode", "GetAbnormalList");
        params.put("TenantId",SharedPreferencesTool.getMStool(MassMainActivity.this).getTenantId());
        httpPostVolley(SharedPreferencesTool.getMStool(MassMainActivity.this).getIp() + UrlUtil.Url, params, new VolleyResult() {
            @Override
            public void success(JSONObject jsonObject) {
                try {
                    JSONArray array = jsonObject.getJSONArray("rows");
                    int len  = array.length();
                    qc_reasons.clear();
                    for(int i=0;i<len;i++){
                        JSONObject object  = array.getJSONObject(i);
                        QC_Reason reason = new QC_Reason();
                        reason.setId(object.getString("ID"));
                        reason.setName(object.getString("ExceptionName"));
                        reason.setType(object.getInt("Type"));
                        qc_reasons.add(reason);
                        getHandler().sendEmptyMessage(2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(VolleyError error) {

            }
        },false);
    }
}
