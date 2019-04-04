package com.zhixing.employlib.viewmodel.fragment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.employlib.model.RecruitEntry;
import com.zhixing.employlib.model.recrui.RecruitListBean;
import com.zhixing.employlib.repertory.recruit.RecruitRepertory;
import com.zhixing.netlib.base.BaseResponse;

import java.util.List;


/**
 *
 *@author zjq
 *create at 2019/3/19 上午10:40
 * 招聘主页的ViewModel
 */
public class RecruitFragmentViewModel extends AndroidViewModel {


    private  RecruitRepertory recruitRepertory;
    public MutableLiveData<List<RecruitEntry>>mRecruitEntity= new MutableLiveData<>();


    public RecruitFragmentViewModel(@NonNull Application application) {
        super(application);
         recruitRepertory =new RecruitRepertory(application);
    }

    public MutableLiveData<BaseResponse<RecruitListBean>> getmRecruitEntity(){
       return recruitRepertory.getRecruitList();

    }

}
