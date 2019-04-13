package com.zhixing.employlib.repertory.team;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.common.SharedUtils;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.employlib.api.APi;
import com.zhixing.employlib.api.PerformanceApi;
import com.zhixing.employlib.model.AppealPersonEntity;
import com.zhixing.employlib.model.gardenplot.ExcellentEmployeeBean;
import com.zhixing.employlib.model.gardenplot.ExcellentEmployeePostBean;
import com.zhixing.employlib.model.gardenplot.NewEmployeeBean;
import com.zhixing.employlib.model.gardenplot.TeamDemeanorBean;
import com.zhixing.employlib.model.gardenplot.UpLoadBean;
import com.zhixing.employlib.model.gardenplot.UpLoadFour;
import com.zhixing.employlib.model.gardenplot.UpLoadOne;
import com.zhixing.employlib.model.gardenplot.UpLoadThree;
import com.zhixing.employlib.model.gardenplot.UpLoadTwo;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

import java.io.File;
import java.util.Map;

import io.reactivex.internal.schedulers.NewThreadWorker;
import okhttp3.RequestBody;

public class TeamRepertory {

    private  Context mContext;
    private SharedUtils sharedUtils;

    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    public TeamRepertory(Context context) {
        this.mContext=context;
        sharedUtils=new SharedUtils(PerformanceApi.FLIESNAME);
    }

    //优秀员工

    public MutableLiveData<BaseResponse<ExcellentEmployeeBean>> getExcellentEmployee(){
      MyBaseRepository myBaseRepository =new MyBaseRepository<ExcellentEmployeeBean>(mContext);

        String AppCode="EMS";
        String ApiCode="GetAllExcellentEmployee";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);

        ExcellentEmployeePostBean bean = new ExcellentEmployeePostBean();
        bean.set_$Index212("0");
        bean.set_$PageSize52("1000");
        bean.setApiCode(ApiCode);
        bean.setAppCode(AppCode);
        bean.setTeamId(teamId);
        bean.setTenantId(TenantId);
        return myBaseRepository.request(APi.getExcellentEmployee(bean, mContext, ip)).send().get();
    }

    //新员工

    public MutableLiveData<BaseResponse<NewEmployeeBean>> getNewEmployee(){
        MyBaseRepository myBaseRepository =new MyBaseRepository<NewEmployeeBean>(mContext);

        String AppCode="EMS";
        String ApiCode="GetAllNewEmployee";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);
        ExcellentEmployeePostBean bean = new ExcellentEmployeePostBean();
        bean.set_$Index212("0");
        bean.set_$PageSize52("1000");
        bean.setApiCode(ApiCode);
        bean.setAppCode(AppCode);
        bean.setTeamId(teamId);
        bean.setTenantId(TenantId);
        return myBaseRepository.request(APi.getNewEmployee(bean, mContext, ip)).send().get();
    }
    //班组风采
    public MutableLiveData<BaseResponse<TeamDemeanorBean>> getTeamDemeanor(){
        MyBaseRepository myBaseRepository =new MyBaseRepository<TeamDemeanorBean>(mContext);

        String AppCode="EMS";
        String ApiCode="GetAllTeamDemeanor";
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);

        ExcellentEmployeePostBean bean = new ExcellentEmployeePostBean();
        bean.set_$Index212("0");
        bean.set_$PageSize52("1000");
        bean.setApiCode(ApiCode);
        bean.setAppCode(AppCode);
        bean.setTeamId(teamId);
        bean.setTenantId(TenantId);
        return myBaseRepository.request(APi.getTeamDemeanor(bean, mContext, ip)).send().get();
    }



    //上传园地图片

    
    public MutableLiveData<BaseResponse<UpLoadBean>> UpLoadImage(Map<String, String> map, File flie){
        MyBaseRepository myBaseRepository =new MyBaseRepository<UpLoadBean>(mContext);
        String  ip = "https://sale.stdlean.com:8004";
        return myBaseRepository.request(APi.UpLoadImage(map,flie, mContext, ip)).send().get();
    }



    //上传优秀员工
    public MutableLiveData<BaseResponse> UpLoadOne(
            String ExcellentId,String type,
                                                   String UserName,
                                                   String UserCode,
                                                   String EvaluationDate,
                                                   String ExcellentDeeds){
        MyBaseRepository myBaseRepository =new MyBaseRepository<BaseResponse>(mContext);
        String AppCode="EMS";
        String ApiCode="EditAPPExcellentEmployee" ;
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String userName=SharedPreferencesTool.getMStool(mContext).getUserName();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);

        UpLoadOne one = new UpLoadOne();
        one.setApiCode(ApiCode);
        one.setAppCode(AppCode);
        one.setCreateUserCode(userCode);
        one.setCreateUserName(userName);
        one.setEvaluationDate(EvaluationDate);
        one.setExcellentDeeds(ExcellentDeeds);
        one.setExcellentId(ExcellentId);
        one.setSeq("1");
        one.setExcellentType(getType(type));
        one.setUserCode(UserCode);
        one.setTeamId(teamId);
        one.setTenantId(TenantId);
        one.setUserName(UserName);

        return myBaseRepository.request(APi.UpLoadOne(one, mContext, ip)).send().get();

    }




    //上传新员工
    public MutableLiveData<BaseResponse> UpLoadTwo( String ExcellentId,String UserName,
                                                    String UserCode,String NewDeeds){
        MyBaseRepository myBaseRepository =new MyBaseRepository<BaseResponse>(mContext);
        String AppCode="EMS";
        String ApiCode="EditAPPNewEmployee" ;
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String userName=SharedPreferencesTool.getMStool(mContext).getUserName();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);

        UpLoadTwo one = new UpLoadTwo();

        one.setApiCode(ApiCode);
        one.setAppCode(AppCode);
        one.setCreateUserCode(userCode);
        one.setCreateUserName(userName);
        one.setUserCode(UserCode);
        one.setTeamId(teamId);
        one.setTenantId(TenantId);
        one.setUserName(UserName);
        one.setNewId(ExcellentId);
        one.setNewDeeds(NewDeeds);
        one.setSeq("1");
        return myBaseRepository.request(APi.UpLoadTwo(one, mContext, ip)).send().get();

    }






    //上传班组园地
    public MutableLiveData<BaseResponse> UpLoadThree( String DemeanorId,String DemeanorTitle,
                                                   String DemeanorContent){
        MyBaseRepository myBaseRepository =new MyBaseRepository<BaseResponse>(mContext);
        String AppCode="EMS";
        String ApiCode="EditAPPTeamDemeanor" ;
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String userName=SharedPreferencesTool.getMStool(mContext).getUserName();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);

        UpLoadThree one = new UpLoadThree();

        one.setApiCode(ApiCode);
        one.setAppCode(AppCode);
        one.setCreateUserCode(userCode);
        one.setCreateUserName(userName);

        one.setTeamId(teamId);
        one.setTenantId(TenantId);
        one.setDemeanorContent(DemeanorContent);
        one.setDemeanorId(DemeanorId);
        one.setDemeanorTitle(DemeanorTitle);
        one.setSeq("1");
        return myBaseRepository.request(APi.UpLoadThree(one, mContext, ip)).send().get();

    }



    //上传公告
    public MutableLiveData<BaseResponse> UpLoadFour( String NoticeId,String NoticeTitle,
                                                      String NoticeContent){
        MyBaseRepository myBaseRepository =new MyBaseRepository<BaseResponse>(mContext);
        String AppCode="EMS";
        String ApiCode="EditAPPNotice" ;
        String TenantId= SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
        String userName=SharedPreferencesTool.getMStool(mContext).getUserName();
        String teamId = sharedUtils.getStringValue(PerformanceApi.TEAMID);

        UpLoadFour one = new UpLoadFour();

        one.setApiCode(ApiCode);
        one.setAppCode(AppCode);
        one.setCreateUserCode(userCode);
        one.setCreateUserName(userName);

        one.setTeamId(teamId);
        one.setTenantId(TenantId);
        one.setNoticeContent(NoticeContent);
        one.setNoticeId(NoticeId);
        one.setNoticeTitle(NoticeTitle);
        one.setSeq("1");
        return myBaseRepository.request(APi.UpLoadFour(one, mContext, ip)).send().get();

    }




    public String getType(String  Type){
        if ("1".equals(Type)){
            return "月度";
        }else if("2".equals(Type)){
            return "季度";
        }else{
            return "年度";
        }

    }

}
