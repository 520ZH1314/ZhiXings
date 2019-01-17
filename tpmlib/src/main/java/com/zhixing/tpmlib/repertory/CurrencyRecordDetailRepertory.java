package com.zhixing.tpmlib.repertory;

import android.arch.lifecycle.MutableLiveData;

import com.zhixing.tpmlib.bean.MaintenanceBean;
import com.zhixing.tpmlib.bean.SpotCheckBean;
import com.zhixing.tpmlib.bean.WarnBean;

import java.util.ArrayList;
import java.util.List;

public class CurrencyRecordDetailRepertory {
    //加载异常记录
    public MutableLiveData<List<WarnBean>> getWarnData() {

        MutableLiveData<List<WarnBean>> data = new MutableLiveData<>();
        List<WarnBean> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            datas.add(new WarnBean("201" + i + "-" + i + "24", "", "设备异常", "李三" + i,
                    "我在睡觉打卡机读卡时间段来看撒旦教雷克萨的记录卡三等奖安联大厦", "已关闭"

            ));
        }


        for (int i = 0; i < 4; i++) {

            datas.add(new WarnBean("211" + i + "-" + i + "24", "", "设备异常", "李五" + i,
                    "沙达拉斯短时大萨达拉三等奖了撒旦教撒了", "未关闭"

            ));
        }

        data.setValue(datas);


        return data;


    }


    //刷新异常记录

    public MutableLiveData<List<WarnBean>> RefreshWarnData() {

        MutableLiveData<List<WarnBean>> data = new MutableLiveData<>();
        List<WarnBean> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            datas.add(new WarnBean("221" + i + "-" + i + "-" + "24", "", "设备异常", "李三" + i,
                    "按实际大家都来喀什的骄傲了肯德基阿拉丁", "已关闭"

            ));
        }


        for (int i = 0; i < 4; i++) {

            datas.add(new WarnBean("221" + i + "-" + i + "-" + "24", "", "设备异常", "李五" + i,
                    "按实际的撒娇的肯德基埃里克", "未关闭"

            ));
        }

        data.setValue(datas);


        return data;


    }

    //加载保养记录

    public MutableLiveData<List<MaintenanceBean>> getMaintenanceData() {

        MutableLiveData<List<MaintenanceBean>> data = new MutableLiveData<>();
        List<MaintenanceBean> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            datas.add(new MaintenanceBean("201" + i + "-" + i + "24", "", "一级保养", "李三" + i,
                    i + "天"

            ));
        }


        for (int i = 0; i < 4; i++) {

            datas.add(new MaintenanceBean("211" + i + "-" + i + "24", "", "二级保养", "李五" + i,
                    i + "天"

            ));
        }

        data.setValue(datas);


        return data;


    }

    //刷新保养记录


    public MutableLiveData<List<MaintenanceBean>> RefreshMaintenanceData() {

        MutableLiveData<List<MaintenanceBean>> data = new MutableLiveData<>();
        List<MaintenanceBean> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            datas.add(new MaintenanceBean("221" + i + "-" + i + "-" + "24", "", "三级保养", "哈哈" + i,
                    i + "天"

            ));
        }


        for (int i = 0; i < 4; i++) {

            datas.add(new MaintenanceBean("211" + i + "-" + i + "-" + "24", "", "二级保养", "嗯嗯" + i,
                    i + "天"

            ));
        }

        data.setValue(datas);


        return data;


    }


    //点检记录

    public MutableLiveData<List<SpotCheckBean>> getSpotCheckData() {

        MutableLiveData<List<SpotCheckBean>> data = new MutableLiveData<>();
        List<SpotCheckBean> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            datas.add(new SpotCheckBean("201" + i + "-" + i + "-" + "24", "", "李三" + i,
                    "15:0" + i, "已完成"

            ));
        }


        for (int i = 0; i < 4; i++) {

            datas.add(new SpotCheckBean("211" + i + "-" + i + "-" + "24", "", "李五" + i,
                    "16:0" + i, "未完成"

            ));
        }

        data.setValue(datas);


        return data;


    }


    public MutableLiveData<List<SpotCheckBean>> RefreshSpotCheckData() {

        MutableLiveData<List<SpotCheckBean>> data = new MutableLiveData<>();
        List<SpotCheckBean> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            datas.add(new SpotCheckBean("211" + i + "-" + i + "-" + "24", "", "好的" + i,
                    "17:0" + i, "已完成"

            ));
        }


        for (int i = 0; i < 4; i++) {

            datas.add(new SpotCheckBean("211" + i + "-" + i + "-" + "23", "", "李五" + i,
                    "18:0" + i, "未完成"

            ));
        }

        data.setValue(datas);


        return data;


    }


}
