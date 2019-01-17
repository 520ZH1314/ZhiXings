package com.zhixing.tpmlib.repertory;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.common.T;
import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.netlib.base.BaseRepository;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;
import com.zhixing.tpmlib.api.Api;
import com.zhixing.tpmlib.api.TpmApi;
import com.zhixing.tpmlib.bean.CheckRecordDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceRecordEntity;
import com.zhixing.tpmlib.bean.MaintenanceRecordPostBean;

/**
 *
 *@author zjq
 *create at 2019/1/16 下午1:54
 * 设备履历记录的数据仓库
 */
public class RecordDataRepertory {
    private  Context context;

    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    public RecordDataRepertory(Context context) {
        this.context=context;
    }
    //获取设备保养记录
    public MutableLiveData<BaseResponse<MaintenanceRecordEntity>> getMaintenanceRecordData(String EquipmentId,String startTime,String endTime){
        MyBaseRepository repository=new MyBaseRepository<MaintenanceRecordEntity>(context);
        String TenantId=SharedPreferencesTool.getMStool(context).getTenantId();
        String ip=SharedPreferencesTool.getMStool(context).getIp();
        String AppCode="TPM";
        String ApiCode="GetEquipmentBRecord";
        MaintenanceRecordPostBean bean =new MaintenanceRecordPostBean(TenantId,startTime,endTime,EquipmentId,AppCode,ApiCode);
        return repository.request(Api.getMainTenanceRecordData(bean,context,ip)).send().get();
    }


    //获取点检记录

    public MutableLiveData<BaseResponse<CheckRecordDataEntity>> getCheckRecordData(String EquipmentId, String startTime, String endTime){
        MyBaseRepository repository=new MyBaseRepository<MaintenanceRecordEntity>(context);
        String TenantId=SharedPreferencesTool.getMStool(context).getTenantId();
        String ip=SharedPreferencesTool.getMStool(context).getIp();
        String AppCode="TPM";
        String ApiCode="GetEquipmentDRecord";
        MaintenanceRecordPostBean bean =new MaintenanceRecordPostBean(TenantId,startTime,endTime,EquipmentId,AppCode,ApiCode);
        return repository.request(Api.getCheckRecordData(bean,context,ip)).send().get();
    }



}
