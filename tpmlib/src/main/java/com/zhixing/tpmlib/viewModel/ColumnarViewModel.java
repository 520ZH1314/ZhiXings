package com.zhixing.tpmlib.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.bean.ColumnarBean;
import com.zhixing.tpmlib.bean.EquipmentBaseDateEntity;
import com.zhixing.tpmlib.bean.LineStationResponEntity;
import com.zhixing.tpmlib.bean.StaticticalAnalAnalyEntity;
import com.zhixing.tpmlib.repertory.EquipmentBaseDateRepertory;
import com.zhixing.tpmlib.repertory.MaintenanceWarnGetStatonRepertory;
import com.zhixing.tpmlib.repertory.StatisticalAnalysisRepertory;

import java.util.List;
import java.util.Map;

public class ColumnarViewModel extends AndroidViewModel {

    private  EquipmentBaseDateRepertory equipmentBaseDateRepertory;
    private  StatisticalAnalysisRepertory repertory;
    public MutableLiveData<List<ColumnarBean>> ColumnarValue = new MutableLiveData<>();

    //默认的Key

    public MutableLiveData<String>nomalKey=new MutableLiveData<>();

    //存分类后的数据

    public MutableLiveData<Map<String, List<StaticticalAnalAnalyEntity>>> mapMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<Boolean> isReplace = new MutableLiveData<>();
    private MutableLiveData<BaseResponse<LineStationResponEntity>> StationData;
    private MutableLiveData<BaseResponse<EquipmentBaseDateEntity>> BaseDateEntity;

    //所有key
    public MutableLiveData<List<String>> key=new MutableLiveData<>();


    public ColumnarViewModel(@NonNull Application application) {
        super(application);
         repertory=new StatisticalAnalysisRepertory(application.getApplicationContext());
        equipmentBaseDateRepertory = new EquipmentBaseDateRepertory(application.getApplicationContext());

    }


    //获取设备基础数据

    public LiveData<BaseResponse<EquipmentBaseDateEntity>>getEquipmentBaseData(String Code){

        this.BaseDateEntity=equipmentBaseDateRepertory.getBaseDatas(Code);
        return BaseDateEntity;
    }





    //获取是否切换布局
    public  MutableLiveData<Boolean> getIsReplace(){

        return isReplace;
    }




    //获取统计分析的数据


    public MutableLiveData<BaseResponse<StaticticalAnalAnalyEntity>>getStaticticalAnalAnalyData(String id){

      return   repertory.getStaticticalAnalAnalyData(id);

    }


    //获取分类后的数据


    public MutableLiveData<Map<String, List<StaticticalAnalAnalyEntity>>>getMapData(){


        return  mapMutableLiveData;

    }


    //获取所有key

    public MutableLiveData<List<String>>getAllKey(){

        return key;
    }

    //获取Key

    public MutableLiveData<String>getNomalKey(){

        return nomalKey;
    }


}
