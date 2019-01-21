package com.zhixing.tpmlib.repertory;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.netlib.base.BaseRepository;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;
import com.zhixing.tpmlib.api.Api;
import com.zhixing.tpmlib.bean.MaintenanceRecordPostBean;
import com.zhixing.tpmlib.bean.StaticticalAnalAnalyEntity;

public class StatisticalAnalysisRepertory extends MyBaseRepository<StaticticalAnalAnalyEntity> {


    private final String AppCode;
    private final String ApiCode;
    private  String ip;
    private  String TenantId;
    private  Context mContext;

    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    public StatisticalAnalysisRepertory(Context context) {
        super(context);
        this.mContext=context;
        TenantId=SharedPreferencesTool.getMStool(context).getTenantId();
        ip=SharedPreferencesTool.getMStool(context).getIp();
        AppCode="TPM";
        ApiCode="GetTotalStatistics";
    }




    //获取统计分析数据

  public MutableLiveData<BaseResponse<StaticticalAnalAnalyEntity>>getStaticticalAnalAnalyData(String ID){

      MaintenanceRecordPostBean bean =new MaintenanceRecordPostBean(AppCode,ApiCode,TenantId,ID);
      return request(Api.getStaticticalAnalAnalyData(bean,mContext,ip)).send().get();
  }






}
