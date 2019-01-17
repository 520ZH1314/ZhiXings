package com.zhixing.tpmlib.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.tpmlib.bean.LineStationResponEntity;
import com.zhixing.tpmlib.bean.MaintenanceListDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceWarnBean;
import com.zhixing.tpmlib.repertory.MaintenanceWarnGetStatonRepertory;
import com.zhixing.tpmlib.repertory.MaintenanceWarnListDataRepertory;

import java.util.List;

public class MaintenanceWarnViewModel extends AndroidViewModel {

    private  MaintenanceWarnListDataRepertory maintenanceWarnListDataRepertory;
    private  MaintenanceWarnGetStatonRepertory maintenanceWarnGetStatonRepertory;
    public MutableLiveData<List<MaintenanceWarnBean>> MaintenanceWarnValue =new MutableLiveData<List<MaintenanceWarnBean>>();;
    private  int Total;//总数量
    private  int page;//当前页
    private  int rows;//每页显示多少条
    //工位数据
    public LiveData<BaseResponse<LineStationResponEntity>> StationData;

    //初始化列表数据
    public LiveData<BaseResponse<MaintenanceListDataEntity>> maintenanceListData;

    public MaintenanceWarnViewModel(@NonNull Application application) {
        super(application);
         Context mContext = application.getApplicationContext();
         maintenanceWarnGetStatonRepertory =new MaintenanceWarnGetStatonRepertory(mContext);
         maintenanceWarnListDataRepertory=new MaintenanceWarnListDataRepertory(mContext);
    }

    //获取工位的数据
    public  LiveData<BaseResponse<LineStationResponEntity>> getStationData(String  LineCode){
         this.StationData=maintenanceWarnGetStatonRepertory.getLineStationData(LineCode);

         return  StationData;
    }



    // List<MaintenanceListDataEntity.RowsBean> rows = maintenanceListData.getValue().getRows();
    //        for (int i = 0; i < rows.size(); i++) {
    //            bean.add(new MaintenanceWarnBean( rows.get(i).getEquipmentName(),rows.get(i).getEquipmentCode(),rows.get(i).getGradeName(),"待审批",rows.get(i).getMaintanceDate(),"0"+i));
    //        }
//    List<MaintenanceWarnBean> bean=new ArrayList<>();
    //            MaintenanceWarnValue.setValue(bean);
    //初始化数据

    public LiveData<BaseResponse<MaintenanceListDataEntity>> initData(int page, int rows, int total, String LineCode, String StationCode, Context context) {
           this.page=page;
           this.rows=rows;
           this.Total=Total;
          this.maintenanceListData=maintenanceWarnListDataRepertory.getMaintenanceListData(LineCode, StationCode,page,rows);

         return maintenanceListData;


    }


    //lodeMoreData
    public LiveData<BaseResponse<MaintenanceListDataEntity>> lodeMoreData(String lineListCode, String lineStationCode, int page, int rows, int total) {
        this.maintenanceListData=maintenanceWarnListDataRepertory.getMaintenanceListData(lineListCode, lineStationCode,page,rows);


        return  maintenanceListData;
    }


    //RefrshData

    public LiveData<BaseResponse<MaintenanceListDataEntity>> RefrshData(String lineListCode, String lineStationCode, int page, int rows, int total) {

        this.maintenanceListData=maintenanceWarnListDataRepertory.getMaintenanceListData(lineListCode, lineStationCode,page,rows);


          return  maintenanceListData;
    }

}
