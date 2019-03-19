package com.zhixing.employlib.viewmodel.fragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.RecruitEntry;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *@author zjq
 *create at 2019/3/19 上午10:40
 * 招聘主页的ViewModel
 */
public class RecruitFragmentViewModel extends AndroidViewModel {


    public MutableLiveData<List<RecruitEntry>>mRecruitEntity= new MutableLiveData<>();


    public RecruitFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<RecruitEntry>> getmRecruitEntity(){
        List<RecruitEntry> datas=new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            datas.add(new RecruitEntry("机械操作工","03月08日","1-3年|中专","这个职位需要熟练工,最好职业技能在四级左右,工作年限在4年,非诚勿扰,新手误投","5-6k","0"));
        }
        for (int i = 0; i < 2; i++) {

            datas.add(new RecruitEntry("机械操作工","03月08日","1-3年|中专","这个职位需要熟练工,最好职业技能在四级左右,工作年限在4年,非诚勿扰,新手误投","5-6k","1"));
        }
        for (int i = 0; i < 2; i++) {

            datas.add(new RecruitEntry("机器学习","03月08日","1-3年|博士","这个职位需要熟练工,最好职业技能在四级左右,工作年限在4年,非诚勿扰,新手误投","35-60k","1"));
        }
        for (int i = 0; i < 2; i++) {

            datas.add(new RecruitEntry("自然语言学习","03月08日","1-3年|博士","这个职位需要熟练工,最好职业技能在四级左右,工作年限在4年,非诚勿扰,新手误投","35-60k","0"));
        }
        mRecruitEntity.setValue(datas);

        return  mRecruitEntity;

    }

}
