package com.zhixing.employlib.viewmodel.activity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.GradingItemEntity;
import com.zhixing.employlib.model.PersonTestEntity;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *@author zjq
 *create at 2019/3/11 下午4:14
 * 评分列表的ViewModel
 */
public class GradingVIewModel extends AndroidViewModel {

    //评分列表的数据

    public MutableLiveData<List<GradingItemEntity>> GradingItemEntitys=new MutableLiveData<>();

    //是否编辑

    public MutableLiveData<Boolean> isSelected;


    public GradingVIewModel(@NonNull Application application) {
        super(application);
    }



   //设置可否编辑

   public  MutableLiveData<Boolean> setSelected(boolean check){
        if (isSelected==null){
            isSelected=new MutableLiveData<>();
        }
       return  isSelected;
   }


    //获取评分列表的数据


    public MutableLiveData<List<GradingItemEntity>> getGradingItemEntitysData(){
        List<GradingItemEntity> lists=new ArrayList<>();
        for (int i = 6; i >0; i--) {
            lists.add(new GradingItemEntity(i+"","张三","男","包装工","关键事件录入:0件"));
        }
        GradingItemEntitys.setValue(lists);
        return  GradingItemEntitys;

    }






}
