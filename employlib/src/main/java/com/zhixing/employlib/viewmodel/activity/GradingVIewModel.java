package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.zhixing.employlib.api.DBaseResponse;
import com.zhixing.employlib.model.GradingItemEntity;
import com.zhixing.employlib.model.PersonTestEntity;
import com.zhixing.employlib.model.grading.GradListBean;
import com.zhixing.employlib.model.performance.MonthPerformanceBean;
import com.zhixing.employlib.repertory.performance.GradingListRepertory;
import com.zhixing.netlib.base.BaseRepository;
import com.zhixing.netlib.base.BaseResponse;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *@author zjq
 *create at 2019/3/11 下午4:14
 * 评分列表的ViewModel
 */
public class GradingVIewModel extends AndroidViewModel {
    private  GradingListRepertory gradingListRepertory;

      //日期筛选
      private final MutableLiveData<String> GradingDate=new MutableLiveData<>();


    public  final LiveData<BaseResponse<GradListBean>> ListData=Transformations.switchMap(GradingDate,entity->(
        gradingListRepertory.getGradingListBean(entity,entity)));

    //是否编辑
    public MutableLiveData<Boolean> isSelected;


    public GradingVIewModel(@NonNull Application application) {
        super(application);
         gradingListRepertory=new GradingListRepertory(application);
    }



   //设置可否编辑

   public  MutableLiveData<Boolean> setSelected(boolean check){
        if (isSelected==null){
            isSelected=new MutableLiveData<>();
        }
       return  isSelected;
   }


    //获取评分列表的数据




    //
    public void setDate(String Dates){

        GradingDate.setValue(Dates);

    }






}
