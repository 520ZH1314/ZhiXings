package com.zhixing.employlib.repertory.resume;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.model.resume.EditResumeData;
import com.zhixing.employlib.model.resume.GetResumeBean;
import com.zhixing.employlib.model.resume.GetResumePostBean;
import com.zhixing.employlib.model.resume.PutResumeData;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

public class ResumeRepertory {
    private  Context mContext;

    public ResumeRepertory(Context context) {
        this.mContext=context;
    }



    //查看个人简历信息


    public MutableLiveData<BaseResponse<GetResumeBean>>GetResumeData(){

        MyBaseRepository myBaseRepository =new MyBaseRepository<GetResumeBean>(mContext);

        String AppCode="EMS";
        String ApiCode="GetResumeList";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userId=SharedPreferencesTool.getMStool(mContext).getUserId();

        GetResumePostBean bean=new GetResumePostBean();
        bean.setApiCode(ApiCode);
        bean.setAppCode(AppCode);
        bean.setTenantId(TenantId);
        bean.setUserId(userId);
        return myBaseRepository.request(APi.GetResumeData(bean, mContext, ip)).send().get();
    }


    //增加个人简历

    public MutableLiveData<BaseResponse>AddResumeData(String CompanyName,String Jobpost,String InDate,
                                                      String enddate,String jobDesc,
                                                      String School,String Major,
   String InSchoolDate,String InSchoolEndDate
    ){

        MyBaseRepository myBaseRepository =new MyBaseRepository<BaseResponse>(mContext);

        String AppCode="EMS";
        String ApiCode="PutResumeData";
        String userName = SharedPreferencesTool.getMStool(mContext).getUserName();
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userId=SharedPreferencesTool.getMStool(mContext).getUserId();

        PutResumeData bean=new PutResumeData();
        bean.setApiCode(ApiCode);
        bean.setAppCode(AppCode);
        bean.setTenantId(TenantId);
        bean.setUserId(userId);
        bean.setCompanyName(CompanyName);
        bean.setInDate(InDate);
        bean.setInSchoolDate(InSchoolDate);
        bean.setJobJd(jobDesc);
        bean.setMajor(Major);
        bean.setSchool(School);
        bean.setOffDate(enddate);
        bean.setOffSchoolDate(InSchoolEndDate);
        bean.setJobpost(Jobpost);
        return myBaseRepository.request(APi.AddResumeData(bean, mContext, ip)).send().get();
    }


    //修改个人简历

    public MutableLiveData<BaseResponse>AlterResumeData(String ResumeId,String CompanyName,String Jobpost,String InDate,
                                                      String enddate,String jobDesc,
                                                      String School,String Major,
                                                      String InSchoolDate,String InSchoolEndDate
    ){

        MyBaseRepository myBaseRepository =new MyBaseRepository<BaseResponse>(mContext);

        String AppCode="EMS";
        String ApiCode="EditResumeData";
        String userName = SharedPreferencesTool.getMStool(mContext).getUserName();
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userId=SharedPreferencesTool.getMStool(mContext).getUserId();

        EditResumeData bean=new EditResumeData();
        bean.setApiCode(ApiCode);
        bean.setAppCode(AppCode);
        bean.setTenantId(TenantId);
        bean.setUserId(userId);
        bean.setCompanyName(CompanyName);
        bean.setInDate(InDate);
        bean.setInSchoolDate(InSchoolDate);
        bean.setJobJd(jobDesc);
        bean.setMajor(Major);
        bean.setSchool(School);
        bean.setOffDate(enddate);
        bean.setOffSchoolDate(InSchoolEndDate);
        bean.setJobpost(Jobpost);
        bean.setUserName(userName);
        bean.setResumeId(ResumeId);
        return myBaseRepository.request(APi.AlterResumeData(bean, mContext, ip)).send().get();
    }







}
