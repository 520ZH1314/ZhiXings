package com.zhixing.tpmlib.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;

import butterknife.BindView;

/*
 * @Author smart
 * @Date 2018/12/24
 * @Des 进入保养警告界面
 */
public class MaintenanceWarmingActivity extends BaseTpmActivity {
@BindView(R2.id.tetle_text)
TextView tvTitle;
    @Override
    public int getLayoutId() {
        return R.layout.activity_maintenance_warming;
    }
    @Override
    public void process(Message msg) {

    }

    @Override
    public void newIniLayout() {
        initData();
    }

    private void initData() {
        tvTitle.setText("保养警告");
    }
}
