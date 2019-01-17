package com.zhixing.tpmlib.api;

import android.content.Context;

import com.zhixing.netlib.base.BaseHttpUtil;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.RetrofitClients;
import com.zhixing.tpmlib.bean.CheckRecordDataEntity;
import com.zhixing.tpmlib.bean.EquipmentBaseDataPostBean;
import com.zhixing.tpmlib.bean.EquipmentBaseDateEntity;
import com.zhixing.tpmlib.bean.LineStationPostBean;
import com.zhixing.tpmlib.bean.LineStationResponEntity;
import com.zhixing.tpmlib.bean.MaintenanceListDataEntity;
import com.zhixing.tpmlib.bean.MaintenanceListDataPostBean;
import com.zhixing.tpmlib.bean.MaintenanceRecordEntity;
import com.zhixing.tpmlib.bean.MaintenanceRecordPostBean;

import io.reactivex.Flowable;

public class Api {
    //获取工位列表
    public static Flowable<BaseResponse<LineStationResponEntity>> getLineStation(LineStationPostBean bean,Context mContext,String url) {
        return RetrofitClients.getInstance(mContext,url).create(TpmApi.class).getLineStation((new BaseHttpUtil<LineStationPostBean>().convertVo2Json(bean)));
    }

    //获取保养预警的接口数据
    public static Flowable<BaseResponse<MaintenanceListDataEntity>> getMaintenanceListData(MaintenanceListDataPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext,url).create(TpmApi.class).getMaintenanceListData((new BaseHttpUtil<MaintenanceListDataPostBean>().convertVo2Json(bean)));
    }

    //获取设备履历基本数据

    public static Flowable<BaseResponse<EquipmentBaseDateEntity>> getEquipmentBaseData(EquipmentBaseDataPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext,url).create(TpmApi.class).getEquipmentBaseData((new BaseHttpUtil<EquipmentBaseDataPostBean>().convertVo2Json(bean)));
    }


    //获取设备履历的保养记录

    public static Flowable<BaseResponse<MaintenanceRecordEntity>> getMainTenanceRecordData(MaintenanceRecordPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext,url).create(TpmApi.class).getMaintenanceRecordData((new BaseHttpUtil<MaintenanceRecordPostBean>().convertVo2Json(bean)));
    }



    //获取设备履历的点检记录

    public static Flowable<BaseResponse<CheckRecordDataEntity>> getCheckRecordData(MaintenanceRecordPostBean bean, Context mContext, String url) {
        return RetrofitClients.getInstance(mContext,url).create(TpmApi.class).getCheckRecordData((new BaseHttpUtil<MaintenanceRecordPostBean>().convertVo2Json(bean)));
    }

}
