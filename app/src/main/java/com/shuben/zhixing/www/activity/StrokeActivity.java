package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.base.zhixing.www.BaseActvity;

/**
 * Created by Administrator on 2017/8/22.
 * 行程
 */

public class StrokeActivity extends BaseActvity implements View.OnClickListener{

    @Override
    public int getLayoutId() {
        return 0;
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

    }

    private void init() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.:
//                break;
        }
    }
}
