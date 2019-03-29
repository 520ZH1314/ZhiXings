package com.zhixing.tpmlib.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;
import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.CommitMaintenanceBean;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;
import com.zhixing.tpmlib.bean.MaintenanceItemEntity;
import com.zhixing.tpmlib.bean.MaintenanceServerBean;
import com.zhixing.tpmlib.repertory.RecordDataRepertory;

import java.util.ArrayList;
import java.util.List;

public class MyTextActivityViewModel extends AndroidViewModel {
    private  RecordDataRepertory recordDataRepertory;
    //位置
    public MutableLiveData<Integer> Position=new MutableLiveData<>();

    //是否刷新数据

    public MutableLiveData<Boolean> isRetrfsh=new MutableLiveData<>();

   //重新设置的数据
   public MutableLiveData<List<DailyCheckItemBean>> DailyCheckItemValues=new MutableLiveData<>();

   //保养点检数据
    public MutableLiveData<BaseResponse<MaintenanceItemEntity>> maintenanceItemEntityDatas=new MutableLiveData<>();


    public MutableLiveData<List<DailyCheckItemBean>> DailyCheckItemValue=new MutableLiveData<>();
    private DailyCheckItemBean bean;

    //保养点检数据



    public MutableLiveData<MaintenanceServerBean> MaintenanceServer= new MutableLiveData<>();

    //上传结果
    public MutableLiveData<CommitMaintenanceBean> baseResponseMutableLiveData;


    public MyTextActivityViewModel(@NonNull Application application) {
        super(application);
         recordDataRepertory =new RecordDataRepertory(application.getApplicationContext());
    }


    public MutableLiveData<List<DailyCheckItemBean>>  getData(){
        List<DailyCheckItemBean> beans =new ArrayList<>();
       /* for (int i = 0; i < 3; i++) {
             bean=new DailyCheckItemBean(R.drawable.check_test,"按实际的拉升的卢卡斯的卢卡斯的合理卡仕达安静的撒娇的撒娇大速冻机"+i,"1","卧式注塑机"+i,"注塑间","注塑机台");
         beans.add(bean);
        }
        for (int i = 3; i < 6; i++) {
             bean=new DailyCheckItemBean(R.drawable.check_test,"按实际的拉升的卢卡斯的卢卡斯的合理卡仕达安静的撒娇的撒娇大速冻机"+i,"2","卧式注塑机"+i,"注塑间","注塑机台");
            beans.add(bean);
        }
        for (int i = 6; i < 9; i++) {
             bean=new DailyCheckItemBean(R.drawable.check_test,"按实际的拉升的卢卡斯的卢卡斯的合理卡仕达安静的撒娇的撒娇大速冻机"+i,"3","卧式注塑机"+i,"注塑间","注塑机台");
            beans.add(bean);
        }*/
        DailyCheckItemValue.setValue(beans);
        return  DailyCheckItemValue;

    }





     public void UpdataPosition(int i){
         Position.setValue(i);
     }


     public void RefrshBean(List<DailyCheckItemBean> data){
         DailyCheckItemValues.setValue(data);
     }


    public MutableLiveData<List<DailyCheckItemBean>> getSelected() {
        return DailyCheckItemValues;
    }




    //获取保养点检数据
    public  MutableLiveData<BaseResponse<MaintenanceItemEntity>> getMaintenanceItemEntity(String ClassId, String GradeId, String PlanId, String EquipmentId){
       maintenanceItemEntityDatas= recordDataRepertory.getMaintenanceItemData(ClassId, GradeId, PlanId, EquipmentId);
       return  maintenanceItemEntityDatas;
    }



   //提交保养记录

    public  MutableLiveData<CommitMaintenanceBean> SendMaintenanceItemEntity(String equipmentId, String gradeId, String itemId, String fruit, String actuallyImage, String planId, String classId, String cell,
                                                                             String position, String description, String standardImage,
                                                                             String paramater, int seq, String operator, String maintananceId){

        baseResponseMutableLiveData = recordDataRepertory.SendMaintenanceItemData(equipmentId, gradeId, itemId, fruit, actuallyImage, planId, classId, cell, position, description, standardImage, paramater, seq, operator, maintananceId);
        return  baseResponseMutableLiveData;
    }



}
