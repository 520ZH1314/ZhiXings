package com.zhixing.tpmlib.repertory;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.base.zhixing.www.util.SharedPreferencesTool;
import com.zhixing.netlib.base.BaseRepository;
import com.zhixing.netlib.base.BaseResponse;
import com.zhixing.netlib.base.MyBaseRepository;
import com.zhixing.tpmlib.api.Api;
import com.zhixing.tpmlib.bean.EquipmentBaseDataPostBean;
import com.zhixing.tpmlib.bean.EquipmentBaseDateEntity;
import com.zhixing.tpmlib.bean.LineStationPostBean;
import com.zhixing.tpmlib.bean.MaintenanceListDataPostBean;

public class EquipmentBaseDateRepertory extends MyBaseRepository<EquipmentBaseDateEntity> {
    private  Context mContext;
    public MutableLiveData<EquipmentBaseDateEntity> BaseDate;
    /**
     * 初始化
     * commonHttpSubscriber = new Common
     *
     * @param context
     */
    public EquipmentBaseDateRepertory(Context context) {
        super(context);
        this.mContext=context;

    }

    //获得设备列表的基础数据

    public MutableLiveData<BaseResponse<EquipmentBaseDateEntity>>getBaseData(String LineStationCode){
        String AppCode="EPS";
        String ApiCode="GetEquipmentList";
        String TenantId=SharedPreferencesTool.getMStool(mContext).getTenantId();
        String ip=SharedPreferencesTool.getMStool(mContext).getIp();
        EquipmentBaseDataPostBean bean=new EquipmentBaseDataPostBean(AppCode,ApiCode,TenantId,LineStationCode);
        return request(Api.getEquipmentBaseData(bean,mContext,ip)).send().get();
    }




}
