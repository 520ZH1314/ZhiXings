package com.zhixing.employlib.api;

import android.content.Context;

import com.luck.picture.lib.entity.EventEntity;
import com.zhixing.employlib.model.IntegralEventEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;
import com.zhixing.netlib.base.BaseHttpUtil;
import com.zhixing.netlib.base.RetrofitClients;

import io.reactivex.Flowable;

public class APi {

    //获取个人班组以及权限
    public static Flowable<DBaseResponse<PersonTeamBean>> getPersonTeamInfo(PersonTeamPostBean bean, Context mContext,String url) {
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getPersonTeamInfo((new BaseHttpUtil<PersonTeamPostBean>().convertVo2Json(bean)));
    }
    //获取考核标准数据请求
    public static Flowable<DBaseResponse<PersonTestEntity>> getEventInfo(PersonTeamPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getEventInfo((new BaseHttpUtil<PersonTeamPostBean>().convertVo2Json(bean)));
    }


}
