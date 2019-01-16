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
}
