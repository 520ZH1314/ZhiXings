package com.zhixing.employlib.repertory;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.api.MyDBaseRepository;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.performance.PersonTeamBean;
import com.zhixing.employlib.model.performance.PersonTeamPostBean;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

public class PerformanceRepertory extends MyDBaseRepository<PersonTeamBean> {
    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    public PerformanceRepertory(Context context) {
        super(context);
        this.mContext=context;
    }



    //获取个人所在班组信息以及权限
    public MutableLiveData<DBaseResponse<PersonTeamBean>>getPersonTeamInfo(){
        String AppCode="EMS";
        String ApiCode="GetTeamIDAndLeader";
        String TenantId=SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userId=SharedPreferencesTool.getMStool(mContext).getUserId();
        PersonTeamPostBean bean=new PersonTeamPostBean(AppCode,ApiCode,TenantId,userId);
        return request(APi.getPersonTeamInfo(bean, mContext, ip)).send().get();

    }


}
