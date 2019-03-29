package com.zhixing.employlib.repertory.performance;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.api.MyDBaseRepository;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;
import com.zhixing.employlib.model.performance.TotalMonthPerformanceBean;
import com.zhixing.employlib.model.performance.YesterdayPerformancePostBean;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

import io.reactivex.Flowable;

public class PerformanceMainRepertory {
    private Context mContext;
    private SharedUtils sharedUtils;

    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    public PerformanceMainRepertory(Context context) {

        this.mContext = context;
        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);
    }


    //获取个人所在班组信息以及权限
    public MutableLiveData<DBaseResponse<PersonTeamBean>> getPersonTeamInfo() {

        MyDBaseRepository<PersonTeamBean> myDBaseRepository = new MyDBaseRepository<PersonTeamBean>(mContext);

        String AppCode = "EMS";
        String ApiCode = "GetTeamIDAndLeader";
        String TenantId = SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip = SharedPreferencesTool.getMStool(mContext).getIp();
        String userId = SharedPreferencesTool.getMStool(mContext).getUserId();
        PersonTeamPostBean bean = new PersonTeamPostBean(AppCode, ApiCode, TenantId, userId);
        return myDBaseRepository.request(APi.getPersonTeamInfo(bean, mContext, ip)).send().get();

    }


    //获取昨日绩效
    public MutableLiveData<DBaseResponse<TotalMonthPerformanceBean>>getYesterdayInfo(String date) {
        MyDBaseRepository<TotalMonthPerformanceBean> myDBaseRepository = new MyDBaseRepository<TotalMonthPerformanceBean>(mContext);

        String AppCode = "EMS";
        String ApiCode = "GetUserInfoOfDay";
        String TenantId = SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip = SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode = SharedPreferencesTool.getMStool(mContext).getUserCode();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);
        YesterdayPerformancePostBean bean = new YesterdayPerformancePostBean(AppCode, ApiCode, userCode, date, TenantId, teamId);
        return myDBaseRepository.request(APi.getYesterDayInfo(bean, mContext, ip)).send().get();


    }

    //获取昨日绩效
    public MutableLiveData<DBaseResponse<MonthPerformanceBean>> getMonthInfo(String date) {
        MyDBaseRepository<MonthPerformanceBean> myDBaseRepository = new MyDBaseRepository<MonthPerformanceBean>(mContext);
        String AppCode = "EMS";
        String ApiCode = "GetUserInfoOfMonth";
        String TenantId = SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip = SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode = SharedPreferencesTool.getMStool(mContext).getUserCode();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);
        YesterdayPerformancePostBean bean = new YesterdayPerformancePostBean(AppCode, ApiCode, userCode, date, TenantId, teamId);
        return myDBaseRepository.request(APi.getMonthDayInfo(bean, mContext, ip)).send().get();


    }









}
