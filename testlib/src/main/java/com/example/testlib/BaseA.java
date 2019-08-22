package com.example.testlib;


import com.base.zhixing.www.BaseActvity;
import butterknife.ButterKnife;

public abstract class BaseA extends BaseActvity {

    @Override
    public void initLayout() {
        ButterKnife.bind(this);
        newIniLayout();
    }
    public abstract void newIniLayout();
}
