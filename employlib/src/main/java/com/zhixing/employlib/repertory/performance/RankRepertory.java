package com.zhixing.employlib.repertory.performance;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.performance.PerformanceRankBean;
import com.zhixing.employlib.model.performance.RankPostBean;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

public class RankRepertory {
    private  SharedUtils sharedUtils;
    private  Context mContext;

    public RankRepertory(Context context) {
        this.mContext=context;
        sharedUtils=new SharedUtils(PerformanceApi.FLIESNAME);
    }



    public MutableLiveData<BaseResponse<PerformanceRankBean>>getRank(String StartDate, String EndDate, String sort){

        MyBaseRepository<PerformanceRankBean> myBaseRepository = new MyBaseRepository<PerformanceRankBean>(mContext);
        String AppCode = "EMS";
        String ApiCode = "GetUserInfoSeqOfDate";
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);

        String TenantId = SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip = SharedPreferencesTool.getMStool(mContext).getIp();

        RankPostBean bean =new RankPostBean();
        bean.setApiCode(ApiCode);
        bean.setAppCode(AppCode);
        bean.setEndDate(EndDate);
        bean.setSeqOrderbBy(sort);
        bean.setStartDate(StartDate);
        bean.setTeamId(teamId);
        bean.setTenantId(TenantId);
        return myBaseRepository.request(APi.getRank(bean, mContext, ip)).send().get();
    }


}
