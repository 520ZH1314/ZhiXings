package com.zhixing.tpmlib.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zhixing.tpmlib.bean.ColumnarBean;
import com.zhixing.tpmlib.bean.MaintenanceWarnBean;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceWarnViewModel extends ViewModel {

    public MutableLiveData<List<MaintenanceWarnBean>> MaintenanceWarnValue =new MutableLiveData<List<MaintenanceWarnBean>>();;



    //初始化数据

    public void initData() {
        List<MaintenanceWarnBean> bean=new ArrayList<>();


        for (int i = 0; i < 8; i++) {
            bean.add(new MaintenanceWarnBean("卧式注塑机"+i+"#","ZSJ00"+i,"周保养","待审批",i+"天","0"+i));
        }

        MaintenanceWarnValue.setValue(bean);
    }


    //lodeMoreData
    public MutableLiveData<List<MaintenanceWarnBean>> lodeMoreData() {
        List<MaintenanceWarnBean> bean=new ArrayList<>();

        for (int i = 20; i < 28; i++) {
            bean.add(new MaintenanceWarnBean("卧式注塑机"+i+"#","ZSJ00"+i,"周保养","待审批",i+"天",+i+""));

        }

        MaintenanceWarnValue.setValue(bean);

        return  MaintenanceWarnValue;
    }


    //RefrshData

    public MutableLiveData<List<MaintenanceWarnBean>> RefrshData() {
        List<MaintenanceWarnBean> bean=new ArrayList<>();


        for (int i = 33; i < 38; i++) {
            bean.add(new MaintenanceWarnBean("卧式注塑机"+i+"#","ZSJ00"+i,"周保养","待审批",i+"天",i+""));
        }

        MaintenanceWarnValue.setValue(bean);

          return  MaintenanceWarnValue;
    }

}
