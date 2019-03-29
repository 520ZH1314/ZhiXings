package com.zhixing.employlib.repertory;


import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.common.P;
import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;
import com.zhixing.netlib.base.BaseRepository;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * 申诉方面的网络请求
 */
public class AppealRepertory extends MyBaseRepository<AppealPersonEntity> {
    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    private Context mContext;
    private SharedUtils sharedUtils;
    public AppealRepertory(Context context) {
        super(context);
        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);
       this. mContext = context;
    }
    public MutableLiveData<BaseResponse<AppealPersonEntity>> getAppealEntity(String time){

        String AppCode="EMS";
        String ApiCode="GetKeyAppeal";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);
        PersonTeamPostBean bean=new PersonTeamPostBean(AppCode,ApiCode,TenantId);
        bean.setTeamId(teamId);
        bean.setUserCode(userCode);
        Map<String,String> map = new HashMap<>();
        map.put("CreateTime",time);
        return request(APi.net(bean, mContext, ip,map,AppealPersonEntity.class)).send().get();
    }

}
