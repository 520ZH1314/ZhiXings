package com.zhixing.employlib.repertory;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.luck.picture.lib.entity.EventEntity;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.api.MyDBaseRepository;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.IntegralEventEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;

public class EventRepertory extends MyDBaseRepository<PersonTestEntity> {
    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    private SharedUtils sharedUtils;
    public EventRepertory(Context context) {
        super(context);
        this.mContext=context;
        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);
    }
    //获取考核标准
    public MutableLiveData<DBaseResponse<PersonTestEntity>> getEventInfo(){
        String AppCode="EMS";
        String ApiCode="GetCheckItemByTeamId";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userId=SharedPreferencesTool.getMStool(mContext).getUserId();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);
        P.c("teamId"+teamId);
        PersonTeamPostBean bean=new PersonTeamPostBean(teamId,AppCode,ApiCode,TenantId,userId);
        return request(APi.getEventInfo(bean, mContext, ip)).send().get();

    }
}
