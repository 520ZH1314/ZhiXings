package com.zhixing.tpmlib.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zhixing.netlib.base.BaseHttpUtil;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.bean.EquipmentBaseDateEntity;
import com.zhixing.tpmlib.bean.LineStationResponEntity;
import com.zhixing.tpmlib.repertory.EquipmentBaseDateRepertory;
import com.zhixing.tpmlib.repertory.MaintenanceWarnGetStatonRepertory;

public class EquipmentEntityViewModel extends AndroidViewModel {
    private  EquipmentBaseDateRepertory equipmentBaseDateRepertory;
    private  MaintenanceWarnGetStatonRepertory maintenanceWarnGetStatonRepertory;
    //工位数据
    public LiveData<BaseResponse<LineStationResponEntity>> StationData;

    //基础数据

    public LiveData<BaseResponse<EquipmentBaseDateEntity>> BaseDateEntity;


    public EquipmentEntityViewModel(@NonNull Application application) {
        super(application);
        Context mContext = application.getApplicationContext();
        maintenanceWarnGetStatonRepertory =new MaintenanceWarnGetStatonRepertory(mContext);
        equipmentBaseDateRepertory = new EquipmentBaseDateRepertory(mContext);

    }
   //获取工位的数据
    public  LiveData<BaseResponse<LineStationResponEntity>> getStationData(String  LineCode){
        this.StationData=maintenanceWarnGetStatonRepertory.getLineStationData(LineCode);

        return  StationData;
    }

    //获取设备基础数据

    public LiveData<BaseResponse<EquipmentBaseDateEntity>>getEquipmentBaseData(String Code){

        this.BaseDateEntity=equipmentBaseDateRepertory.getBaseData(Code);
        return BaseDateEntity;
    }




}
