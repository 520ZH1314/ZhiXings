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
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;

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






}
