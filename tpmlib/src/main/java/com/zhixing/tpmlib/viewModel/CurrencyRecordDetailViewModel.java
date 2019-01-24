package com.zhixing.tpmlib.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.bean.CheckRecordDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceBean;
import com.zhixing.tpmlib.bean.MaintenanceRecordEntity;
import com.zhixing.tpmlib.bean.SpotCheckBean;
import com.zhixing.tpmlib.bean.WarnBean;
import com.zhixing.tpmlib.bean.WarnRecordDataEntity;
import com.zhixing.tpmlib.repertory.RecordDataRepertory;

import java.util.List;

public class CurrencyRecordDetailViewModel extends AndroidViewModel {
    private RecordDataRepertory maintenanceRecordRepertory;
    public MutableLiveData<List<MaintenanceBean>> beans =new MutableLiveData<>();
    public CurrencyRecordDetailViewModel(@NonNull Application application) {
        super(application);
         maintenanceRecordRepertory =new RecordDataRepertory(application.getApplicationContext());

    }


    //获取异常记录

    public MutableLiveData<BaseResponse<WarnRecordDataEntity>> getWarnData(String id, String time1, String time2){

          return  maintenanceRecordRepertory.getWarnRecordData(id,time1,time2);
    }

//    //获取刷新异常记录
//    public MutableLiveData<List<WarnBean>> RefreshWarnData(){
//        return  recordDetailRepertory.RefreshWarnData();
//    }
//    //获取上啦加载异常记录
//    //获取刷新异常记录
//    public MutableLiveData<List<WarnBean>> LoadWarnData(){
//        return recordDetailRepertory.RefreshWarnData();
//    }


    //获取保养记录
    public  MutableLiveData<BaseResponse<MaintenanceRecordEntity>> getMaintenanceData(String id, String time1, String time2){

       return  maintenanceRecordRepertory.getMaintenanceRecordData(id, time1, time2);




    }
//    //获取刷新保养记录
//    public MutableLiveData<List<MaintenanceBean>> RefreshMaintenanceData(){
//               return  recordDetailRepertory.RefreshMaintenanceData();
//    }
//    //获取上啦加载保养记录
//    public MutableLiveData<List<MaintenanceBean>> LoadMaintenanceData(){
//        return  recordDetailRepertory.RefreshMaintenanceData();
//
//    }
    //获取点检记录
    public MutableLiveData<BaseResponse<CheckRecordDataEntity>> getSpotCheckData(String id, String time1, String time2){
        return maintenanceRecordRepertory.getCheckRecordData(id,time1,time2);
    }
//    //获取刷新点检记录
//    public MutableLiveData<List<SpotCheckBean>> RefreshSpotCheckData(){
//        return recordDetailRepertory.RefreshSpotCheckData();
//    }
//    //获取上啦加载点检记录
//    public MutableLiveData<List<SpotCheckBean>>LoadSpotCheckData(){
//
//        return recordDetailRepertory.RefreshSpotCheckData();
//    }

}
