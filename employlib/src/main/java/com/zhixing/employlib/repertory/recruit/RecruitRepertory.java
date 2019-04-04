package com.zhixing.employlib.repertory.recruit;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.gardenplot.ExcellentEmployeePostBean;
import com.zhixing.employlib.model.gardenplot.TeamDemeanorBean;
import com.zhixing.employlib.model.recrui.DoJobPostBean;
import com.zhixing.employlib.model.recrui.PutRefferPostBean;
import com.zhixing.employlib.model.recrui.RecruitListBean;
import com.zhixing.employlib.model.recrui.RecruitListPostBean;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

public class RecruitRepertory  {

    private  Context mContext;
    private SharedUtils sharedUtils;

    public RecruitRepertory(Context context) {

        this.mContext=context;
        sharedUtils=new SharedUtils(PerformanceApi.FLIESNAME);
    }


    //招聘列表

    public MutableLiveData<BaseResponse<RecruitListBean>> getRecruitList(){

        MyBaseRepository myBaseRepository =new MyBaseRepository<RecruitListBean>(mContext);

        String AppCode="EMS";
        String ApiCode="GetUserRecruitList";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userId=SharedPreferencesTool.getMStool(mContext).getUserId();


        RecruitListPostBean bean = new RecruitListPostBean();

        bean.setApiCode(ApiCode);
        bean.setAppCode(AppCode);
        bean.setApplyUserId(userId);
        bean.setTenantId(TenantId);
        return myBaseRepository.request(APi.getRecruitList(bean, mContext, ip)).send().get();
    }





    //投递简历

    //ApplyUserName":"彭敏","RecruitId":"123456002",
    //"RecruitPost":"硬件工程师","HandleId":"6663fa4b-22f0-a200-a4fd-6b747e09cfdb",
    //"HandleName":"曾志亮","ApplyType":"1",
    //"State":"1","StateName":"已投递",

    public MutableLiveData<BaseResponse> SendJob(String ApplyUserName,
                                                 String RecruitId,
                                                 String RecruitPost,
                                                 String HandleId,
                                                 String HandleName,
                                                 String ApplyType, String State,String StateName ){

        MyBaseRepository myBaseRepository =new MyBaseRepository<BaseResponse>(mContext);
        String AppCode="EMS";
        String ApiCode="PutApplyData";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userId=SharedPreferencesTool.getMStool(mContext).getUserId();
        DoJobPostBean bean = new DoJobPostBean();
        bean.setApiCode(ApiCode);
        bean.setAppCode(AppCode);
        bean.setApplyUserId(userId);
        bean.setTenantId(TenantId);
        bean.setApplyType(ApplyType);
        bean.setApplyUserName(ApplyUserName);
        bean.setHandleId(HandleId);
        bean.setHandleName(HandleName);
        bean.setRecruitId(RecruitId);
        bean.setRecruitPost(RecruitPost);
        bean.setState(State);
        bean.setStateName(StateName);
        return myBaseRepository.request(APi.SendJob(bean, mContext, ip)).send().get();
    }



   //推荐人
   public MutableLiveData<BaseResponse> PutJob( String ApplyName,
            String ApplySex,
            String Phone,
            String IDCardNo,
            String NativePlace,
            String MaxDegree,
            String Major,
            String School,
            String RefPhone,
            String RefContent,
            String RefferId,
            String RefferName,String RecruitId,String RecruitPost){

       MyBaseRepository myBaseRepository =new MyBaseRepository<BaseResponse>(mContext);

       String AppCode="EMS";
       String ApiCode="PutRefferAndApply";
       String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
       String ip=SharedPreferencesTool.getMStool(mContext).getIp();
       String userId=SharedPreferencesTool.getMStool(mContext).getUserId();


       PutRefferPostBean bean = new PutRefferPostBean();

       bean.setApiCode(ApiCode);
       bean.setAppCode(AppCode);
       bean.setApplyName(ApplyName);
       bean.setApplySex(ApplySex);
       bean.setIDCardNo(IDCardNo);
       bean.setMajor(Major);
       bean.setMaxDegree(MaxDegree);
       bean.setNativePlace(NativePlace);
       bean.setPhone(Phone);
       bean.setRefContent(RefContent);
       bean.setRefferId(RefferId);
       bean.setRefferName(RefferName);
       bean.setRefPhone(RefPhone);
       bean.setSchool(School);
       bean.setTenantId(TenantId);
       bean.setRecruitId(RecruitId);
       bean.setRecruitPost(RecruitPost);
       return myBaseRepository.request(APi.PutJob(bean, mContext, ip)).send().get();
   }



}
