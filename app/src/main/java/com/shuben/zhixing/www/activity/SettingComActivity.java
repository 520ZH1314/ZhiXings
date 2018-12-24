package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.SharedUtils;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.common.T;
import com.shuben.zhixing.www.inter.SetSelect;
import com.shuben.zhixing.www.widget.CommonSetSelectPop;

public class SettingComActivity extends BaseActvity {
    @Override
    public void process(Message msg) {

    }
    private RelativeLayout layout0,layout1,layout2,layout3;
    private TextView tetle_text,item1,item0,item2,item3;
    private SharedUtils sharedUtils;
    @Override
    public void initLayout() {
        sharedUtils = new SharedUtils(T.SET_F);
        item1 = findViewById(R.id.item1);
        item0 = findViewById(R.id.item0);
        item2 = findViewById(R.id.item2);
        item3 = findViewById(R.id.item3);
        tetle_text  = findViewById(R.id.tetle_text);
        tetle_text.setText("企业基础设置");
        layout0 = findViewById(R.id.layout0);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3  = findViewById(R.id.layout3);
        layout0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonSetSelectPop setSelectPop = new CommonSetSelectPop(SettingComActivity.this,getHandler(),"工厂");
                setSelectPop.getSet().put("ApiCode", "GetFactoryList");
                setSelectPop.setMidH(true);
                setSelectPop.setSelect(new SetSelect() {
                    @Override
                    public void select(String id, String code, String name) {
                        sharedUtils.setStringValue("factory_id",id);
                        sharedUtils.setStringValue("factory_name",name);
                        setTe();
                    }
                });
                setSelectPop.showSheet();
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonSetSelectPop setSelectPop = new CommonSetSelectPop(SettingComActivity.this,getHandler(),"车间");
                setSelectPop.getSet().put("ApiCode", "GetWorkShopList");
                setSelectPop.getSet().put("FactoryId",sharedUtils.getStringValue("factory_id"));
                setSelectPop.setMidH(true);
                setSelectPop.setSelect(new SetSelect() {
                    @Override
                    public void select(String id, String code, String name) {
                        sharedUtils.setStringValue("workshop_id",id);
                        sharedUtils.setStringValue("workshop_code",code);
                        sharedUtils.setStringValue("workshop_name",name);
                        setTe();
                    }


                });
                setSelectPop.showSheet();
            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonSetSelectPop setSelectPop = new CommonSetSelectPop(SettingComActivity.this,getHandler(),"产线");
                setSelectPop.getSet().put("ApiCode", "GetLineList");
                setSelectPop.getSet().put("WorkShopId",sharedUtils.getStringValue("workshop_id"));
                setSelectPop.setMidH(true);
                setSelectPop.setSelect(new SetSelect() {
                    @Override
                    public void select(String id, String code, String name) {
                        sharedUtils.setStringValue("line_id",id);
                        sharedUtils.setStringValue("line_name",name);
                        setTe();
                    }


                });
                setSelectPop.showSheet();
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonSetSelectPop setSelectPop = new CommonSetSelectPop(SettingComActivity.this,getHandler(),"工位");
                setSelectPop.getSet().put("ApiCode", "GetLineStationList");
                setSelectPop.getSet().put("LineId",sharedUtils.getStringValue("line_id"));
                setSelectPop.setMidH(true);
                setSelectPop.setSelect(new SetSelect() {
                    @Override
                    public void select(String id, String code, String name) {
                        sharedUtils.setStringValue("station_id",id);
                        sharedUtils.setStringValue("station_name",name);
                        setTe();
                    }


                });
                setSelectPop.showSheet();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setttingcom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
      setTe();
    }
    private void setTe(){
        item0.setText(sharedUtils.getStringValue("factory_name"));
        item1.setText(sharedUtils.getStringValue("line_name"));
        item2.setText(sharedUtils.getStringValue("station_name"));
        item3.setText(sharedUtils.getStringValue("workshop_name"));
    }
}
