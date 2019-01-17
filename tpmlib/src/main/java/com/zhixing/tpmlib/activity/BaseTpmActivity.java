package com.zhixing.tpmlib.activity;

import android.os.Bundle;
import android.os.Message;

import com.base.zhixing.www.BaseActvity;

import butterknife.ButterKnife;

public abstract class BaseTpmActivity extends BaseActvity {
    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        newIniLayout();
    }
    public abstract void newIniLayout();

    public String getFormartTime(String data) {
        String buyDate = data;
        String t[] = buyDate.split("T");
        String string=t[0]+" "+t[1];
        return string;
    }
}
