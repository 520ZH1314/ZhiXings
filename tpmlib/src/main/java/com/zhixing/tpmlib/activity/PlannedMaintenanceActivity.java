package com.zhixing.tpmlib.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;

import butterknife.BindView;

public class PlannedMaintenanceActivity extends BaseTpmActivity {
@BindView(R2.id.tetle_text)
TextView tvTitle;
    @Override
    public int getLayoutId() {
        return R.layout.activity_planned_maintenance;
    }
    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {
        //        初始化控件
        initData();
    }
//初始化数据\
    private void initData() {
        tvTitle.setText("计划保养");
    }
}
