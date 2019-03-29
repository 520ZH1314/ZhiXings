package com.zhixing.tpmlib.repertory;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.netlib.base.BaseRepository;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;
import com.zhixing.tpmlib.api.Api;
import com.zhixing.tpmlib.bean.MaintenanceListDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceListDataPostBean;

public class MaintenanceWarnListDataRepertory extends MyBaseRepository<MaintenanceListDataEntity> {
    private Context mContext;
    public MaintenanceWarnListDataRepertory(Context context) {
        super(context);


        this.mContext=context;
    }
    public MutableLiveData<BaseResponse<MaintenanceListDataEntity>> getMaintenanceListData(String LineCode, String StationCode, int page, int rows){
         String AppCode="TPM";
         String ApiCode="GetPlanProtect";
         String TenantId=SharedPreferencesTool.getMStool(mContext).getTenantId();
         String ip=SharedPreferencesTool.getMStool(mContext).getIp();
         String userCode=SharedPreferencesTool.getMStool(mContext).getUserCode();
         String StringPage = String.valueOf(page);
         String Stringrows = String.valueOf(rows);
        MaintenanceListDataPostBean bean=new MaintenanceListDataPostBean(AppCode,ApiCode,TenantId,LineCode,StationCode,userCode,StringPage,Stringrows);
       return request(Api.getMaintenanceListData(bean,mContext,ip)).send().get();

   }


}
