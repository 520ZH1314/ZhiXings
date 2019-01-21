package com.zhixing.tpmlib.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.zhixing.tpmlib.R;
import com.zhixing.tpmlib.bean.DailyCheckItemBean;

import java.util.ArrayList;
import java.util.List;

public class MyTextActivityViewModel extends AndroidViewModel {
   //位置
    public MutableLiveData<Integer> Position=new MutableLiveData<>();
   //重新设置的数据
   public MutableLiveData<List<DailyCheckItemBean>> DailyCheckItemValues=new MutableLiveData<>();

    public MutableLiveData<List<DailyCheckItemBean>> DailyCheckItemValue=new MutableLiveData<>();
    private DailyCheckItemBean bean;

    public MyTextActivityViewModel(@NonNull Application application) {
        super(application);
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


}
