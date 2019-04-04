package com.zhixing.employlib.repertory;


import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.AppealList;
import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.model.MonthViewBean;
import com.zhixing.employlib.model.StandScore;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * 申诉方面的网络请求
 */
public class MonthRepertory {
    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    private Context mContext;
    private SharedUtils sharedUtils;
    public MonthRepertory(Context context) {

        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);
       this. mContext = context;
    }
    

    public MutableLiveData<BaseResponse<MonthViewBean>> getMonthViews(Map time){
        MyBaseRepository repository = new MyBaseRepository(mContext);
        String AppCode="EMS";
        String ApiCode="GetUserEventInfoByDate";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);
        PersonTeamPostBean bean=new PersonTeamPostBean(AppCode,ApiCode,TenantId);
        bean.setTeamId(teamId);
        bean.setUserCode(userCode);

        return repository.request(APi.getMonthViews(bean, mContext, ip,time)).send().get();
    }

    public MutableLiveData<BaseResponse<StandScore>> getScoreColor(Map time){
        MyBaseRepository repository = new MyBaseRepository(mContext);
        String AppCode="EMS";
        String ApiCode="GetAllGrapeChartColor";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);
        PersonTeamPostBean bean=new PersonTeamPostBean(AppCode,ApiCode,TenantId);
        bean.setTeamId(teamId);
        bean.setUserCode(userCode);

        return repository.request(APi.getScoreColor(bean, mContext, ip,time)).send().get();
    }

}
