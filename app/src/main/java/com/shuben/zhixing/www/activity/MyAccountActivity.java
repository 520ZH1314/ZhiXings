package com.shuben.zhixing.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;

/**
 * Created by Administrator on 2017/8/22.
 * 个人资料
 */

public class MyAccountActivity extends BaseActvity implements View.OnClickListener {
    private ImageView tetle_back;
    private TextView tetle_text,updata_pwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account;
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
        tetle_text.setText("账户与安全");

        updata_pwd = (TextView) findViewById(R.id.updata_pwd);//title
        setOnclick();
    }

    private void setOnclick() {
        updata_pwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;

            case R.id.updata_pwd:
                intent = new Intent(MyAccountActivity.this, ChangePwdActivity.class);//账户安全
                startActivity(intent);
                break;
        }
    }
}
