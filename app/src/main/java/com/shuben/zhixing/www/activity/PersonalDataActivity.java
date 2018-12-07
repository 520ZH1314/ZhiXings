package com.shuben.zhixing.www.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.widget.CircularImage;

/**
 * Created by Administrator on 2017/8/22.
 * 个人资料
 */

public class PersonalDataActivity extends BaseActvity implements View.OnClickListener {
    private ImageView tetle_back;
    private CircularImage user_head;
    private TextView tetle_text,user_name,user_zhiwei,user_sex,user_phone,user_email,user_lingdao,user_bumen,user_fushu_bumen;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personaldate;
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

        user_head = (CircularImage)findViewById(R.id.user_head);//头像

        tetle_text = (TextView) findViewById(R.id.tetle_text);//title
        tetle_text.setText("个人资料");

        user_name = (TextView) findViewById(R.id.user_name);//t用户姓名
        user_zhiwei = (TextView) findViewById(R.id.user_zhiwei);//t用户职位
        user_sex = (TextView) findViewById(R.id.user_sex);//性别
        user_phone = (TextView) findViewById(R.id.user_phone);//手机号码
        user_email = (TextView) findViewById(R.id.user_email);//邮箱
        user_lingdao = (TextView) findViewById(R.id.user_lingdao);//直属领导
        user_bumen = (TextView) findViewById(R.id.user_bumen);//直属部门
        user_fushu_bumen = (TextView) findViewById(R.id.user_fushu_bumen);//附属部门


        setOnclick();
    }

    private void setOnclick() {
        user_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;
        }
    }
}
