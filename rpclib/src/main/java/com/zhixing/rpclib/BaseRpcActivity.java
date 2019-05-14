package com.zhixing.rpclib;

import android.os.Bundle;

import com.base.zhixing.www.BaseActvity;
import com.base.zhixing.www.common.P;

import butterknife.ButterKnife;

public abstract class BaseRpcActivity extends BaseActvity {
    @Override
    public void initLayout() {
        P.c("Ces");
        ButterKnife.bind(this);
        P.c("Ces1");
        newIniLayout();
        P.c("Ces2");
    }



    public abstract void newIniLayout();
}