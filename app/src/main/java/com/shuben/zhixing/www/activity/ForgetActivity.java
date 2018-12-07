package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.util.CustomToast;

import java.util.HashMap;

/**
 * Created by XianBo.Geng on 2016/12/9.
 */
public class ForgetActivity extends BaseActvity implements View.OnClickListener {
    private ImageView tetle_back;
    private TextView tetle_text;
    private EditText forget_phone;
    private String paramer;
    private RelativeLayout forget_pwd_layout;
    private HashMap<String,String> map ;
    private CustomToast customToast;
    private String PHONE;

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget;
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
        customToast = new CustomToast(this);
        initview();
    }

    private void initview() {
        PHONE = getIntent().getStringExtra("PHONE");//登录界面传递过来的手机号码
        tetle_back = (ImageView)findViewById(R.id.tetle_back);//返回
        tetle_back.setOnClickListener(this);
        tetle_text = (TextView) findViewById(R.id.tetle_text);//title
        tetle_text.setText("忘记密码");

        forget_phone = (EditText)findViewById(R.id.forget_phone);
        if (PHONE!=null){
            forget_phone.setText(PHONE);
        }
        forget_pwd_layout = (RelativeLayout)findViewById(R.id.forget_pwd_layout);
        forget_pwd_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tetle_back:
                finish();
                break;
            case R.id.forget_pwd_layout:
                paramer = forget_phone.getText().toString().trim();
                if(paramer.isEmpty()){
                    customToast.showToast("输入内容不能为空");
                }else {
                    getWebServer();
                }
                break;
        }
    }

    private void getWebServer() {
       //调用wangji忘记密码接口
    }
}
