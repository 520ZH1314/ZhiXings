package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.inter.SetSelect;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.shuben.zhixing.www.widget.CommonSetSelectPop;

public class SettingComActivity extends BaseActvity {
    @Override
    public void process(Message msg) {

    }
    private RelativeLayout layout0,layout1,layout2,layout3;
    private TextView tetle_text,item1,item0,item2,item3;
    @Override
    public void initLayout() {
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
                        SharedPreferencesTool.getMStool(SettingComActivity.this).setString("factory_id",id);
                        SharedPreferencesTool.getMStool(SettingComActivity.this).setString("factory_name",name);
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
                setSelectPop.getSet().put("FactoryId",SharedPreferencesTool.getMStool(SettingComActivity.this).getString("factory_id"));
                setSelectPop.setMidH(true);
                setSelectPop.setSelect(new SetSelect() {
                    @Override
                    public void select(String id, String code, String name) {
                        SharedPreferencesTool.getMStool(SettingComActivity.this).setString("workshop_id",id);
                        SharedPreferencesTool.getMStool(SettingComActivity.this).setString("workshop_code",code);
                        SharedPreferencesTool.getMStool(SettingComActivity.this).setString("workshop_name",name);
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
                setSelectPop.getSet().put("WorkShopId",SharedPreferencesTool.getMStool(SettingComActivity.this).getString("workshop_id"));
                setSelectPop.setMidH(true);
                setSelectPop.setSelect(new SetSelect() {
                    @Override
                    public void select(String id, String code, String name) {
                        SharedPreferencesTool.getMStool(SettingComActivity.this).setString("line_id",id);
                        SharedPreferencesTool.getMStool(SettingComActivity.this).setString("line_name",name);
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
                setSelectPop.getSet().put("LineId",SharedPreferencesTool.getMStool(SettingComActivity.this).getString("line_id"));
                setSelectPop.setMidH(true);
                setSelectPop.setSelect(new SetSelect() {
                    @Override
                    public void select(String id, String code, String name) {
                        SharedPreferencesTool.getMStool(SettingComActivity.this).setString("station_id",id);
                        SharedPreferencesTool.getMStool(SettingComActivity.this).setString("station_name",name);
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
        item0.setText(SharedPreferencesTool.getMStool(SettingComActivity.this).getString("factory_name"));
        item1.setText(SharedPreferencesTool.getMStool(SettingComActivity.this).getString("line_name"));
        item2.setText(SharedPreferencesTool.getMStool(SettingComActivity.this).getString("station_name"));
        item3.setText(SharedPreferencesTool.getMStool(SettingComActivity.this).getString("workshop_name"));
    }
}
