package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;

/**
 * Created by Administrator on 2017/8/22.
 * 应用广场
 */

public class StateActivity extends BaseActvity implements View.OnClickListener {
    private ImageView tetle_back;
    private TextView tetle_text,tetle_tv_ok;

    @Override
    public int getLayoutId() {
        return R.layout.activity_square;
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
        init();
    }

    private void init() {
        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回
        tetle_back.setOnClickListener(this);
        tetle_text = (TextView) findViewById(R.id.tetle_text);//title
        tetle_text.setText("使用说明");

        setOnclick();
    }

    private void setOnclick() {
        tetle_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;
        }
    }
}
