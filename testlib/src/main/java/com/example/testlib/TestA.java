package com.example.testlib;

import android.os.Message;
import android.widget.TextView;

import butterknife.BindView;

public class TestA extends BaseA {

    @BindView(R2.id.tt0)
     TextView tvWorkTitle;
    @Override
    public int getLayoutId() {
        return R.layout.test;
    }

    @Override
    public void process(Message msg) {

    }


    @Override
    public void newIniLayout() {
        tvWorkTitle.setText("测试111");
    }
}
