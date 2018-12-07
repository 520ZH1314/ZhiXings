package com.shuben.zhixing.www.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.zhixing.www.BaseActvity;

import com.shuben.zhixing.www.R;
import com.shuben.zhixing.www.util.CustomToast;
import com.base.zhixing.www.util.SharedPreferencesTool;

import java.util.HashMap;

/**
 * Created by XianBo.Geng on 2016/12/9.
 */
public class ChangePwdActivity extends BaseActvity implements View.OnClickListener {
    private ImageView tetle_back;
    private TextView tetle_text,change_pwd_tv;
    private CustomToast customToast,mSuccessToast;
    private SharedPreferencesTool sharedPreferencesTool;
    private String oidpwd,new_pwd,next_newpwd;
    private EditText user_oidpwd_et,new_pwd_et,user_next_newpwd_et;
    private HashMap<String,String> map ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change;
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
        sharedPreferencesTool = new SharedPreferencesTool(this);
        initview();
    }


    private void initview() {
        tetle_back = (ImageView)findViewById(R.id.tetle_back);
        tetle_back.setOnClickListener(this);
        tetle_text.setText("修改密码");
        change_pwd_tv = (TextView) findViewById(R.id.change_pwd_tv);
        change_pwd_tv.setOnClickListener(this);
        user_oidpwd_et = (EditText) findViewById(R.id.user_oidpwd_et);
        new_pwd_et = (EditText) findViewById(R.id.new_pwd_et);
        user_next_newpwd_et = (EditText) findViewById(R.id.user_next_newpwd_et);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tetle_back:

                finish();
                break;
            case R.id.change_pwd_tv:
                oidpwd = user_oidpwd_et.getText().toString().trim();
                new_pwd = new_pwd_et.getText().toString().trim();
                next_newpwd = user_next_newpwd_et.getText().toString().trim();
                if (oidpwd.isEmpty()){
                    customToast.showToast("请输入原密码");
                }else if(new_pwd.isEmpty()){
                    customToast.showToast("请输入新密码");
                }else if(next_newpwd.isEmpty()){
                    customToast.showToast("请再次输入新密码");
                }
                getWebServer();
                break;
        }
    }

    private void getWebServer() {
       //调用修改密码接口

    }
}
