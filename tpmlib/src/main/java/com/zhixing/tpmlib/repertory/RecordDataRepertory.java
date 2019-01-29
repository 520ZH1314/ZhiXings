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
import com.zhixing.tpmlib.bean.MaintenanceCommitPostBean;
import com.zhixing.tpmlib.bean.MaintenanceItemCheckPostBean;
import com.zhixing.tpmlib.bean.MaintenanceItemEntity;
import com.zhixing.tpmlib.bean.MaintenanceListDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceRecordEntity;
import com.zhixing.tpmlib.bean.MaintenanceRecordPostBean;
import com.zhixing.tpmlib.bean.WarnRecordDataEntity;

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

    //获取异常记录

    public MutableLiveData<BaseResponse<WarnRecordDataEntity>> getWarnRecordData(String EquipmentId, String startTime, String endTime){
        MyBaseRepository repository=new MyBaseRepository<MaintenanceRecordEntity>(context);
        String TenantId=SharedPreferencesTool.getMStool(context).getTenantId();
        String ip=SharedPreferencesTool.getMStool(context).getIp();
        String AppCode="TPM";
        String ApiCode="GetEquipmentYRecord";
        MaintenanceRecordPostBean bean =new MaintenanceRecordPostBean(TenantId,startTime,endTime,EquipmentId,AppCode,ApiCode);
        return repository.request(Api.getWarnRecordData(bean,context,ip)).send().get();
    }


    public MutableLiveData<BaseResponse<MaintenanceItemEntity>> getMaintenanceItemData(String ClassId, String GradeId, String PlanId, String EquipmentId){

        MyBaseRepository baseRepository=new  MyBaseRepository<MaintenanceListDataEntity>(context);
        String AppCode="TPM";
        String ApiCode="GetFirstMaintanceInfo";
        String TenantId=SharedPreferencesTool.getMStool(context).getTenantId();
        String ip=SharedPreferencesTool.getMStool(context).getIp();
        MaintenanceItemCheckPostBean bean=new MaintenanceItemCheckPostBean(ApiCode,AppCode,TenantId,ClassId,GradeId,PlanId,EquipmentId);
        return baseRepository.request(Api.getMaintenanceItemDatas(bean,context,ip)).send().get();

    }


    //提交保养记录



    public MutableLiveData<BaseResponse> SendMaintenanceItemData( String equipmentId, String gradeId, String itemId, String fruit, String actuallyImage, String planId, String classId, String cell,
                                                                  String position, String description, String standardImage,
                                                                  String paramater, int seq, String operator, String maintananceId){
        MyBaseRepository baseRepository=new  MyBaseRepository(context);
        String AppCode="TPM";
        String ApiCode="EditMaintanceRecord";
        String TenantId=SharedPreferencesTool.getMStool(context).getTenantId();
        String ip=SharedPreferencesTool.getMStool(context).getIp();
        MaintenanceCommitPostBean bean=new MaintenanceCommitPostBean(TenantId,AppCode,ApiCode,equipmentId,
                gradeId,itemId,fruit,actuallyImage,planId,classId,cell,position,description,standardImage,paramater,seq,operator,maintananceId);
        return baseRepository.request(Api.SendMaintenanceItemDatas(bean,context,ip)).send().get();

    }

}
