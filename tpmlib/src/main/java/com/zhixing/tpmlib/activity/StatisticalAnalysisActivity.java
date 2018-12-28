package com.zhixing.tpmlib.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.R2;

import butterknife.BindView;

public class StatisticalAnalysisActivity extends BaseTpmActivity {
    @BindView(R2.id.tetle_text)
    TextView tvTitle;//标题文本标签
    @Override
    public int getLayoutId() {
        return R.layout.activity_statistical_analysis;
    }

    @Override
    public void process(Message msg) {

    }
    @Override
    public void newIniLayout() {
        //        初始化数据
        initData();
    }

    private void initData() {
        tvTitle.setText("统计分析");
    }
}
