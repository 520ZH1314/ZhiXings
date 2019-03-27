package com.zhixing.employlib.api;

import android.content.Context;

import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;
import com.zhixing.netlib.base.BaseHttpUtil;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.RetrofitClients;

import io.reactivex.Flowable;

public class APi {

    //获取个人班组以及权限
    public static Flowable<DBaseResponse<PersonTeamBean>> getPersonTeamInfo(PersonTeamPostBean bean, Context mContext,String url) {
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getPersonTeamInfo((new BaseHttpUtil<PersonTeamPostBean>().convertVo2Json(bean)));
    }



}
