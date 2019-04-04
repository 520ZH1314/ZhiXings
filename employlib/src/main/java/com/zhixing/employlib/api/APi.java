package com.zhixing.employlib.api;

import android.content.Context;

import com.base.zhixing.www.common.FileUtils;
import com.base.zhixing.www.common.T;
import com.zhixing.employlib.model.AppealList;
import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.model.MonthViewBean;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.StandScore;
import com.zhixing.employlib.model.grading.GoGradingPostBean;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.model.grading.GradListDetailPostBean;
import com.zhixing.employlib.model.grading.GradingListDetailBean;
import com.zhixing.employlib.model.grading.GradingListPostBean;
import com.zhixing.employlib.model.performance.EventKeyBean;
import com.zhixing.employlib.model.performance.EventPostBean;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;
import com.zhixing.employlib.model.performance.TotalMonthPerformanceBean;
import com.zhixing.employlib.model.performance.YesterdayPerformancePostBean;
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

    //获取昨日绩效

    public static  Flowable<DBaseResponse<TotalMonthPerformanceBean>> getYesterDayInfo(YesterdayPerformancePostBean bean, Context mContext, String url){
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getYesterDayInfo((new BaseHttpUtil<YesterdayPerformancePostBean>().convertVo2Json(bean)));


    }

    //获取月绩效

    public static  Flowable<DBaseResponse<MonthPerformanceBean>> getMonthDayInfo(YesterdayPerformancePostBean bean, Context mContext, String url){
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getMonthDayInfo((new BaseHttpUtil<YesterdayPerformancePostBean>().convertVo2Json(bean)));


    }

    //获取评分员工列表数据

    public static Flowable<BaseResponse<GradListBean>> getGradingListData(GradingListPostBean bean, Context mContext, String url){
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getGradingListData((new BaseHttpUtil<GradingListPostBean>().convertVo2Json(bean)));


    }

    //获取评分详情员工列表数据
    public static Flowable<BaseResponse<GradingListDetailBean>> getGradingListDetailData(GradListDetailPostBean bean, Context mContext, String url){
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getGradingListDetailData((new BaseHttpUtil<GradListDetailPostBean>().convertVo2Json(bean)));

    }

    //去评分
    public static Flowable<BaseResponse> getGoGrading(String bean, Context mContext, String url){
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getGoGrading((new BaseHttpUtil<GoGradingPostBean>().toJson(bean)));

    }

    //获取事件
    public static Flowable<DBaseResponse<EventKeyBean>> getEvent(EventPostBean bean, Context mContext, String url){
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getEvent((new BaseHttpUtil<EventPostBean>().convertVo2Json(bean)));

    }
    //获取考核标准数据请求
    public static Flowable<DBaseResponse<PersonTestEntity>> getEventInfo(PersonTeamPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext,url).create(PerformanceApi.class).getEventInfo((new BaseHttpUtil<PersonTeamPostBean>().convertVo2Json(bean)));
    }
    private static Map put(PersonTeamPostBean bean,Map map, BaseHttpUtil  httpUtil){
        Map tempMap = httpUtil.convertVo2Map(bean);
        if(map!=null){
            tempMap.putAll(map);
        }
        FileUtils.parms(tempMap);
        return tempMap;
    }
    //统一数据处理
    public static  Flowable<BaseResponse<AppealPersonEntity>> net(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil  httpUtil = new BaseHttpUtil<PersonTeamPostBean>();

        return RetrofitClients.getInstance(mContext,url) .create(PerformanceApi.class) .net(httpUtil.convertVo2Json(put(bean,map,httpUtil)));
    }
    //申诉请求
    public static  Flowable<BaseResponse> sendAppealPerson(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil  httpUtil = new BaseHttpUtil();


        return RetrofitClients.getInstance(mContext,url) .create(PerformanceApi.class) .sendAppealPerson(httpUtil.convertVo2Json( put(bean,map,httpUtil)));
    }
    //申诉列表
    public static  Flowable<BaseResponse<AppealList>> getAppealList(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil  httpUtil = new BaseHttpUtil();

        return RetrofitClients.getInstance(mContext,url) .create(PerformanceApi.class) .getAppealList(httpUtil.convertVo2Json(put(bean,map,httpUtil)));
    }

    //申诉请求
    public static  Flowable<BaseResponse> sendAppealRes(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil  httpUtil = new BaseHttpUtil();


        return RetrofitClients.getInstance(mContext,url) .create(PerformanceApi.class) .sendAppealRes(httpUtil.convertVo2Json( put(bean,map,httpUtil)));
    }
    //月视图
    public static  Flowable<BaseResponse<MonthViewBean>> getMonthViews(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil  httpUtil = new BaseHttpUtil();

        return RetrofitClients.getInstance(mContext,url) .create(PerformanceApi.class) .getMonthViews(httpUtil.convertVo2Json(put(bean,map,httpUtil)));
    }
    //月视图
    public static  Flowable<BaseResponse<StandScore>> getScoreColor(PersonTeamPostBean bean, Context mContext, String url, Map map) {
        BaseHttpUtil  httpUtil = new BaseHttpUtil();

        return RetrofitClients.getInstance(mContext,url) .create(PerformanceApi.class) .getScoreColor(httpUtil.convertVo2Json(put(bean,map,httpUtil)));
    }
}
