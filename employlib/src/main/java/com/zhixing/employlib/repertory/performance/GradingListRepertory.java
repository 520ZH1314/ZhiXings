package com.zhixing.employlib.repertory.performance;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.GsonUtil;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.api.MyDBaseRepository;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.grading.GoGradingPostBean;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.model.grading.GradListDetailPostBean;
import com.zhixing.employlib.model.grading.GradingListDetailBean;
import com.zhixing.employlib.model.grading.GradingListPostBean;
import com.zhixing.employlib.model.performance.EventKeyBean;
import com.zhixing.employlib.model.performance.EventPostBean;
import com.zhixing.employlib.model.performance.YesterdayPerformancePostBean;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

import java.util.List;

public class GradingListRepertory {

    private Context mContext;
    private SharedUtils sharedUtils;

    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    public GradingListRepertory(Context context) {

        this.mContext = context;
        sharedUtils = new SharedUtils(PerformanceApi.FLIESNAME);
    }


    //获取评分员工列表数据

    public MutableLiveData<BaseResponse<GradListBean>> getGradingListBean(String startDate, String endDate) {
        MyBaseRepository myBaseRepository = new MyBaseRepository<GradListBean>(mContext);
        String AppCode = "EMS";
        String ApiCode = "GetWorkTeamUserEventInfo";
        String TenantId = SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip = SharedPreferencesTool.getMStool(mContext).getIp();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);
        GradingListPostBean bean = new GradingListPostBean(AppCode, ApiCode, TenantId, startDate, endDate, teamId);
        return myBaseRepository.request(APi.getGradingListData(bean, mContext, ip)).send().get();
    }


    //获取评分员工列表详情数据

    public MutableLiveData<BaseResponse<GradingListDetailBean>>getGradingListDetail(String startDate, String endDate,String useCode){
        MyBaseRepository myBaseRepository = new MyBaseRepository<GradingListDetailBean>(mContext);
        String AppCode = "EMS";
        String ApiCode = "GetUserEventInfo";
        String TenantId = SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip = SharedPreferencesTool.getMStool(mContext).getIp();
        GradListDetailPostBean bean = new GradListDetailPostBean(AppCode, ApiCode, TenantId, startDate, endDate,useCode);
        return myBaseRepository.request(APi.getGradingListDetailData(bean, mContext, ip)).send().get();
    }


    // //评分
    public MutableLiveData<BaseResponse>getGoGrading( List<GoGradingPostBean.UserInfoBean> UserInfo,List<GoGradingPostBean.EventInfoBean> EventInfo){
        MyBaseRepository myBaseRepository = new MyBaseRepository(mContext);
        String AppCode = "EMS";
        String ApiCode = "EditAPPKeyEvent";
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);

        String TenantId = SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip = SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode = SharedPreferencesTool.getMStool(mContext).getUserCode();

        GoGradingPostBean bean = new GoGradingPostBean();
          bean.setApiCode(ApiCode);
          bean.setAppCode(AppCode);
          bean.setTeamLeadeCode(userCode);
          bean.setTenantId(TenantId);
          bean.setTeamLeadeTeamId(teamId);
          bean.setEventInfo(EventInfo);
          bean.setUserInfo(UserInfo);
        String json = GsonUtil.getGson().toJson(bean);
        return myBaseRepository.request(APi.getGoGrading(json, mContext, ip)).send().get();
    }



    //获取事件
    public MutableLiveData<DBaseResponse<EventKeyBean>>getEvent(){
        MyDBaseRepository myDBaseRepository=new MyDBaseRepository(mContext);
        String AppCode = "EMS";
        String ApiCode = "GetCheckItemByTeamId";
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);

        String TenantId = SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip = SharedPreferencesTool.getMStool(mContext).getIp();
        EventPostBean bean =new EventPostBean(AppCode,ApiCode,TenantId,teamId);

        return  myDBaseRepository.request(APi.getEvent(bean,mContext,ip)).send().get();
    }

}
