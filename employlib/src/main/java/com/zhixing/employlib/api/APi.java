package com.zhixing.employlib.api;

import android.content.Context;

import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.T;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;
import com.zhixing.netlib.base.BaseHttpUtil;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.RetrofitClients;

import java.util.Map;

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
    //统一数据处理
    public static<T> Flowable<BaseResponse<T>> net(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil  httpUtil = new BaseHttpUtil<PersonTeamPostBean>();
        Map tempMap = httpUtil.convertVo2Map(bean);
        if(map!=null){
            tempMap.putAll(map);
        }
        FileUtils.parms(tempMap);

        return RetrofitClients.getInstance(mContext,url) .create(PerformanceApi.class) .net(httpUtil.convertVo2Json(tempMap));
    }

}
