package com.shuben.zhixing.www.patrol.twoclass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;

public class CallActivity extends BaseActvity implements View.OnClickListener{
    private LinearLayout lay01,lay02,lay03,lay_back;
    private Intent intent;


    @Override
    public int getLayoutId() {
        return R.layout.activity_call;
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
        initView();
    }

    private void initView() {

        lay_back=(LinearLayout)findViewById(R.id.lay_back);
        lay01=(LinearLayout)findViewById(R.id.lay01);
        lay02=(LinearLayout)findViewById(R.id.lay02);
        lay03=(LinearLayout)findViewById(R.id.lay03);
        lay_back.setOnClickListener(this);
        lay01.setOnClickListener(this);
        lay02.setOnClickListener(this);
        lay03.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){//view点击事件使用
            case R.id.lay_back:
                finish();
                break;
            case R.id.lay01:
                intent=new Intent();
                intent.setClass(CallActivity.this,AddCallActivity.class);
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                startActivity(intent);
                break;
            case R.id.lay02:
                intent=new Intent();
                intent.setClass(CallActivity.this,SignActivity.class);
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                startActivity(intent);

                break;
            case R.id.lay03:
                intent=new Intent();
                intent.setClass(CallActivity.this,PhotoActivity.class);
                intent.putExtra("RecordId",getIntent().getStringExtra("RecordId"));
                startActivity(intent);

                break;
            default:

            break;



        }
    }
}
