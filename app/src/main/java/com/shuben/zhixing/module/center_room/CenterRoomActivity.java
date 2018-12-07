package com.shuben.zhixing.module.center_room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.base.zhixing.www.BaseActvity;
import com.shuben.zhixing.www.R;

public class CenterRoomActivity extends BaseActvity {
    @Override
    public void process(Message msg) {

    }

    @Override
    public void initLayout() {

    }
    public void kxzzts(View v){
        Intent intent =new Intent(CenterRoomActivity.this,Kxzzts.class);
        startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.center_room_layou;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
