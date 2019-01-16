package com.zhixing.tpmlib.activity;

import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;
import android.animation.ArgbEvaluator;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EquipmentResumeActivity extends BaseTpmActivity {
    @BindView(R2.id.tetle_text)
    TextView tvTitle;


    @Override
    public int getLayoutId() {
        return R.layout.activity_equipment_resume;
    }

    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {


        //初始化数据
        initData();
    }

    private void initData() {
        tvTitle.setText("设备履历");


    }







}
